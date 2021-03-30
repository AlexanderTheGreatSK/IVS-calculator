/*
 * cool hlavicka TODO
 */

package com.company;

import java.text.DecimalFormat;


/**
 * Library for the calculator - main method takes a string representing
 * the expression to calculate, parses it into a doubly linked list
 * (one node represents one operand or one operator), calculates the result
 * (one by one finds an operator, takes it's operands, writes the result
 * of the operation to one of the nodes and removes the rest. This is repeated
 * until there is only one node left: the final result) and returns the result
 * as a string.
 * <p>
 * This class involves implementation of a doubly linked list and many methods
 * allowing us to work with it.
 */
public class CalcLib {

    // Operators the library is able to work with:
    // Plus, minus, multiply, divide, factorial, modulo, power, root
    private static final String operatorsList = "+-*/!%^|";

    /**
     * Implementation of a doubly linked list.
     * <p>
     * The list consists of interconnected nodes (the first one is the 'head'
     * and the last one is the 'tail'). Each node points to a previous one as
     * well as the next one. Each node also contains a character variable
     * (this is where to character representing an operation is stored) and
     * a number (double data type). If the node represents a number, the
     * character is '\0' (null byte).
     * <p>
     * The class contains methods for appending a new node, removing an
     * existing one, finding a node, changing its contents and few more.
     */
    public static class DoublyLinkedList {

        /**
         * A single node represents either an operator or a number.
         * <p>
         * If a node is an operator, number variable (double data type) equals
         * 0.0, and the operator variable (char data type) contains a character
         * representing the operation.
         * If a node is a number, operator variable contains '\0' and,
         * naturally, the number variable contains the number itself.
         * <p>
         * Involves a constructor that requires two parameters:
         * character and a number - both of these are then written to the node.
         */
        private static class Node {
            char operator;
            Double number;
            Node prev;
            Node next;

            /**
             * Constructor of a single node.
             *
             * @param inputChar  If the node is to be an operator, inputChar
             *                   should contain a character representing the
             *                   mathematical operation. It should contain
             *                   null byte ('\0') otherwise.
             * @param inputNum  If the node is to be a number, inputNum should
             *                  be equal to the number the node is to
             *                  represent. It should be equal to 0.0 otherwise.
             */
            Node(char inputChar, double inputNum) {
                operator = inputChar;
                number = inputNum;
            }
        }

        // Always the first node in the list
        Node head;

        // To optimize working with the list, this node is always the one
        // that was last worked with
        Node currentOperatorNode;

        /**
         * Finds and returns the tail of the doubly linked list.
         *
         * @return the tail node.
         */
        private Node getTail() {
            Node node = head;
            if (node == null) {
                return null;
            }
            while (node.next != null) {
                node = node.next;
            }
            return node;
        }

        /**
         * Sets currentOperatorNode to the head of the list.
         */
        public void resetCurrentNode() {
            currentOperatorNode = head;
        }

        /**
         * Finds the first node (from head to tail) representing the given
         * operator and saves it into currentOperatorNode.
         *
         * @param op  a character representing the operation we are searching
         *            for.
         * @param nextNotFirst  If false, it will save the position of the
         *                      first matching operator it finds.
         *                      If true, it will save the position of the
         *                      next matching operator instead.
         * @return true if given operator was found, or false if given
         *         operator was not present in the list.
         */
        public boolean findOperator(char op, boolean nextNotFirst) {
            Node node;
            if(nextNotFirst) {
                node = currentOperatorNode.next;
            }
            else {
                node = head;
            }
            while (node != null) {
                if (node.operator == op) {
                    currentOperatorNode = node;
                    return true;
                }
                node = node.next;
            }
            return false;
        }

        /**
         * Finds a node previous of currentOperatorNode.
         *
         * @return a number that the node previous to currentOperatorNode
         *         contains.
         * @throws ArithmeticException if the node is not found.
         */
        public double getFirstOperand()
                throws ArithmeticException {
            if (currentOperatorNode == null) {
                throw new ArithmeticException("Malformed expression.");
            }
            if (currentOperatorNode.prev == null) {
                throw new ArithmeticException("Malformed expression.");
            }
            return currentOperatorNode.prev.number;
        }

        /**
         * Finds a node next of currentOperatorNode.
         *
         * @return a number that the node after the currentOperatorNode
         *         contains.
         * @throws ArithmeticException if the node is not found.
         */
        public double getSecondOperand()
                throws ArithmeticException {
            if (currentOperatorNode == null) {
                throw new ArithmeticException("Malformed expression.");
            }
            if (currentOperatorNode.next == null) {
                throw new ArithmeticException("Malformed expression.");
            }
            return currentOperatorNode.next.number;
        }

        /**
         * Writes the given number into the node previous to the
         * currentOperatorNode while deleting currentOperatorNode.
         * If isTwoOperandOperation is true, also deletes the node after the
         * currentOperatorNode.
         *
         * @param num  number to be written to the previous node.
         * @param isTwoOperandOperation  if true, removes two nodes (current
         *                               and next). If false, removes only
         *                               the current node.
         */
        public void writeInResult(double num, boolean isTwoOperandOperation) {
            currentOperatorNode.prev.number = num;
            if (isTwoOperandOperation) {
                remove(currentOperatorNode.next);
            }
            remove(currentOperatorNode);
        }

        /**
         * Changes the first (left) operand
         * (number of the node previous to the currentOperatorNode)
         * of the current node to a given number.
         *
         * @param newOperand  new first (left) operand.
         */
        public void changeFirstOperand(double newOperand) {
            currentOperatorNode.prev.number = newOperand;
        }

        /**
         * Changes the operator of the current node
         * to the given character representing a new operation.
         *
         * @param newOp  new operator.
         */
        public void changeOperator(char newOp) {
            currentOperatorNode.operator = newOp;
        }

        /**
         * Changes the second (right) operand
         * (number of the node after the currentOperatorNode)
         * of the current node to a given number.
         *
         * @param newOperand  new second (right) operand.
         */
        public void changeSecondOperand(double newOperand) {
            currentOperatorNode.next.number = newOperand;
        }

        /**
         * @return the number of the first node of the doubly linked list.
         */
        public double getHeadNum() {
            return head.number;
        }

        /**
         * @return true if the list only contains one node, false otherwise.
         */
        public boolean isRootOnly() {
            if(head == null){
                return false;
            }
            return head.next == null;
        }

        /**
         * @return true if the next node is an operator, false if it's a number.
         */
        public boolean isNextNodeAnOperator() {
            if(currentOperatorNode.next == null) {
                return false;
            }
            return currentOperatorNode.next.operator != '\0';
        }

        /**
         * Removes a given node from the list.
         *
         * @param toRemove  node that is supposed to be removed.
         */
        private void remove(Node toRemove) {
            if (toRemove == null || head == null) {
                return;
            }
            if (head == toRemove) {
                head = head.next;
            }
            if (toRemove.next != null) {
                toRemove.next.prev = toRemove.prev;
            }
            if (toRemove.prev != null) {
                toRemove.prev.next = toRemove.next;
            }
        }

        /**
         * Appends a node to the list with a given operation and a given number.
         *
         * @param op  a character representing a operation the node to append
         *            should contain.
         * @param num  a number the node to append should contain.
         */
        public void append(char op, double num) {
            Node newNode = new Node(op, num);
            Node tail = getTail();
            if (tail == null) {
                head = newNode;
                head.next = null;
                head.prev = null;
            } else {
                tail.next = newNode;
                newNode.next = null;
                newNode.prev = tail;
            }
        }
    }

    /**
     * Parses the input string into a doubly linked list - each operand or
     * operator is a single node.
     * <p>
     * Before appending operands and operators to the list, removes all white
     * characters, converts mod to '%, pow to '^', root to '|'
     * and replaces ',' with '.'.
     *
     * @param input  a string containing exactly what the user entered.
     * @return the doubly linked list containing all operations and
     *               operands.
     * @throws ArithmeticException if it fails to parse the input.
     */
    public static DoublyLinkedList parser(String input)
            throws ArithmeticException{
        DoublyLinkedList list = new DoublyLinkedList();

        // Remove all white characters
        input = input.replaceAll("\\s+","");

        // Replace all "mod" with '%'
        input = input.replace("mod", "%");

        // Convert "pow()" to '^', for example pow(5,2) => 5^2
        String orig;
        String tmp;
        int indexFrom;
        while(true){
            indexFrom = input.indexOf("pow");
            if(indexFrom == -1){
                break;
            }
            int indexTo = input.indexOf(")", indexFrom);
            orig = input.substring(indexFrom, indexTo + 1);
            tmp = orig;
            tmp = tmp.replace("pow(", "");
            tmp = tmp.replace(")", "");

            if(tmp.indexOf(',') == -1) {
                throw new ArithmeticException("Malformed power expression.");
            }
            tmp = tmp.replace(",", "^");
            if(tmp.charAt(0) == '-'){
                tmp = tmp.replace('-', '~');
            }
            if(tmp.charAt(tmp.indexOf('^') + 1) == '-'){
                tmp = tmp.replace('-', '~');
            }
            input = input.replace(orig, tmp);
        }
        
        // Converts "root" to '|'
        // For example root(5,2) => 5|2 (represents sqrt(5))
        while(true){
            indexFrom = input.indexOf("root");
            if(indexFrom == -1){
                break;
            }
            int indexTo = input.indexOf(")", indexFrom);
            orig = input.substring(indexFrom, indexTo + 1);
            tmp = orig;
            tmp = tmp.replace("root(", "");
            tmp = tmp.replace(")", "");
            if(tmp.indexOf(',') == -1) {
                throw new ArithmeticException("Malformed root expression.");
            }
            tmp = tmp.replace(",", "|");
            if(tmp.charAt(0) == '-'){
                tmp = tmp.replace('-', '~');
            }
            if(tmp.charAt(tmp.indexOf('|') + 1) == '-'){
                tmp = tmp.replace('-', '~');
            }
            input = input.replace(orig, tmp);
        }

        // Replace all commas with dots
        input = input.replaceAll(",", ".");

        // Fill list with given operators and numbers
        String temp = "";
        int len = input.length();
        char ch = '0';
        for(int i = 0; i < len; i++) {
            ch = input.charAt(i);
            if((ch >= '0' && ch <= '9') || ch == '.' || ch == '~') {
                if(temp.equals("") && ch == '~') {
                    temp = "-";
                }
                else {
                    temp = temp + ch;
                }
                continue;
            }else if(!temp.isEmpty()){
                list.append('\0', Double.parseDouble(temp));
                temp = "";
            }
            if(operatorsList.indexOf(ch) == -1) {
                throw new ArithmeticException("Invalid character inserted.");
            }
            list.append(ch, 0);
        }
        if(!temp.isEmpty()) {
            list.append('\0', Double.parseDouble(temp));
        }
        return list;
    }

    /**
     * Checks if a number is natural (whole) or not.
     *
     * @param num  A double precision number.
     * @return true if the number is natural, false if it is not.
     */
    public static boolean isNatural(double num){
        return Math.round(num) == num;
    }

    /**
     * Checks the parsed input for invalid operations, forbidden characters,
     * malformed expressions and so on.
     *
     * @param list  The doubly linked list containing all operations and
     *              operands.
     * @throws ArithmeticException if the input is invalid.
     */
    public static void validate(DoublyLinkedList list)
            throws ArithmeticException{

        // Factorial - the operand is not a natural number
        list.resetCurrentNode();
        while(list.findOperator('!', true)) {
            if(!isNatural(list.getFirstOperand())){
                throw new ArithmeticException(
                        "The factorial argument is not a natural number.");
            }
        }

        // Modulo - the divisor is not a natural number
        list.resetCurrentNode();
        while(list.findOperator('%', true)) {
            if(!isNatural(list.getSecondOperand())){
                throw new ArithmeticException("The divisor in the modulo " +
                        "operation is not a natural number.");
            }
        }

        // Division - division by zero is undefined
        list.resetCurrentNode();
        while(list.findOperator('/', true)) {
            if(list.getSecondOperand() == 0){
                throw new ArithmeticException("Division by zero.");
            }
        }

        // Root exception
        list.resetCurrentNode();
        while(list.findOperator('|', true)) {
            if(list.getSecondOperand()%2 == 0 && list.getFirstOperand() < 0) {
                throw new ArithmeticException("Even root of a negative number.");
            }
            if(list.getSecondOperand() == 0){
                throw new ArithmeticException("The root index is zero.");
            }
            if(Math.round(list.getSecondOperand()) != list.getSecondOperand()){
                throw new ArithmeticException(
                        "The root index is not a whole number.");
            }
        }

        // Power exceptions
        list.resetCurrentNode();
        while(list.findOperator('^', true)) {
            if(Math.round(list.getSecondOperand()) != list.getSecondOperand()){
                throw new ArithmeticException(
                        "The exponent of power is not a whole number.");
            }
        }

        // Other - Two operators without a number separating them is only
        // allowed after the factorial operator
        list.resetCurrentNode();
        char op;
        for(int i = 0; i < operatorsList.length(); i++) {
            op = operatorsList.charAt(i);
            if(op == '!'){
                continue;
            }
            while(list.findOperator(op, true)) {
                if(list.isNextNodeAnOperator()){
                    throw new ArithmeticException("There are two operators " +
                            "between which there is no operand.");
                }
            }
        }
    }

    /**
     * Searches the doubly linked list for operations with the highest
     * precedence, calculates the result of these operations and replaces it's
     * nodes (operand(s) and the operation) with the result, then continues with
     * operations with lower precedence and ends if there is only one item
     * left in the linked list - the result of the calculation.
     * <p>
     * Precedence: factorial > power > root > modulo >
     * division and multiplication > subtraction and addition
     * <p>
     * For easier implementation, every division is converted to multiplication
     * (2/2 -> 2 * 1/2) and subtraction to addition (2-2 -> 2 + -2) before
     * the calculation.
     *
     * @param list  The doubly linked list containing all operations and
     *              operands.
     */
    public static void calculate(DoublyLinkedList list){
        double result;

        // Factorial
        while(list.findOperator('!', false)) {
            result = list.getFirstOperand();
            for(int i = (int)result - 1; i > 0; i--) {
                result *= i;
            }
            list.writeInResult(result, false);
        }

        // Power
        while(list.findOperator('^', false)) {
            result = Math.pow(list.getFirstOperand(),
                    list.getSecondOperand());
            list.writeInResult(result, true);
        }

        // Root
        while(list.findOperator('|', false)) {
            boolean negativeIndex = false;
            if(list.getFirstOperand() < 0) {
                list.changeFirstOperand(-list.getFirstOperand());
                negativeIndex = true;
            }
            result = Math.pow(list.getFirstOperand(),
                    1.0f/list.getSecondOperand());
            if(negativeIndex){
                result = -result;
            }
            list.writeInResult(result, true);
        }

        // Modulo
        while(list.findOperator('%', false)) {
            result = list.getFirstOperand() %
                    list.getSecondOperand();
            list.writeInResult(result, true);
        }

        // Convert division to multiplication
        while(list.findOperator('/', false)) {
            list.changeOperator('*');
            list.changeSecondOperand(1 / list.getSecondOperand());
        }

        // Multiplication
        while(list.findOperator('*', false)) {
            result = list.getFirstOperand() *
                    list.getSecondOperand();
            list.writeInResult(result, true);
        }

        // Convert minus sings to plus signs (negating the next operand)
        while(list.findOperator('-', false)) {
            list.changeOperator('+');
            list.changeSecondOperand(- list.getSecondOperand());
        }

        // Addition
        while(list.findOperator('+', false)) {
            result = list.getFirstOperand() +
                    list.getSecondOperand();
            list.writeInResult(result, true);
        }
    }


    /**
     * Parses the input string into a doubly linked list, checks if the input
     * is valid, calculates the result, converts it to a string in desired
     * format and finally, returns it as a string.
     *
     * @param input  a string containing exactly what the user entered
     * @return result - a string containing the result of the calculation -
     *         a single number in decimal form with a maximum of 8 floating
     *         point digits (or exponential form if the number is bigger
     *         than 2^52).
     * @throws ArithmeticException if the input is not computable.
     */
    public static String main(String input)
            throws ArithmeticException{

        // If the string is empty, simply return an empty string
        if(input.equals("")){
            return "";
        }

        // Parse the input string into a doubly linked list
        DoublyLinkedList list = parser(input);

        // Check if the input string is valid
        validate(list);

        // Calculate the result of the input
        calculate(list);

        // If after the calculation there were more multiple nodes or not
        // a single one, something definitely went wrong
        if(!list.isRootOnly()) {
            throw new ArithmeticException(
                    "Could not compute the entered expression.");
        }

        // The result equals the number of the only node remaining
        double result = list.getHeadNum();

        // If the number is bigger than range of a double data type, the
        // result should be in an exponential format
        // 'x*10^n', or, better: 'xEn'
        if(result > Math.pow(2, 52)) {
            return Double.toString(result);
        }

        // If the result is a natural number, return it as an integer
        // (the floating point digits are unnecessary)
        if(isNatural(result)) {
            long newRes = Math.round(result);
            return Long.toString(newRes);
        }

        // Truncate the result to 8 decimal places and return
        String pattern = "#.########";
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(result);
    }
}
