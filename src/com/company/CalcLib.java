/*
 * cool hlavicka
 */

package com.company;

import java.text.DecimalFormat;


public class CalcLib {

    private static final String operatorsList = "+-*/!%^|";

    /**
     * neco chytryho k listu
     */
    public static class DoublyLinkedList {

        /**
         * The Node class represents either an operator or a number.
         * If a Node is an operator, number = 0.0,
         * if a Node is a number, operator = '\0'.
         */
        private static class Node {
            char operator;
            Double number;
            Node prev;
            Node next;

            Node(char inputChar, double inputNum) {
                operator = inputChar;
                number = inputNum;
            }
        }

        Node head;
        Node currentOperatorNode;

        /**
         * Finds and the tail
         *
         * @return tail node
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
         * Sets currentOperatorNode to the head of the list
         */
        public void resetCurrentNode() {
            currentOperatorNode = head;
        }

        /**
         * Finds the first node (from head to tail) representing the given
         * operator and saves it into currentOperatorNode.
         *
         * @param op  a character representing the operation we are searching
         *            for
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
         * Finds a node previous of currentOperatorNode
         *
         * @return  number of the node previous to currentOperatorNode
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
         * Finds a node next of currentOperatorNode
         *
         * @return  number of the node next to currentOperatorNode
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
         * If isTwoOperandOperation is true, also deletes the node next of
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
         * Changes the first operand (number of the node previous of currentOperatorNode) of the current node
         * to the given number.
         *
         * @param newOperand  new first operand
         */
        public void changeFirstOperand(double newOperand) {
            currentOperatorNode.prev.number = newOperand;
        }

        /**
         * Changes the operator of the current node
         * to the given character representing an operation.
         *
         * @param newOp  new operator
         */
        public void changeOperator(char newOp) {
            currentOperatorNode.operator = newOp;
        }

        /**
         * Changes the second operand (number of the node next of currentOperatorNode) of the current node
         * to the given number.
         *
         * @param newOperand  new second operand
         */
        public void changeSecondOperand(double newOperand) {
            currentOperatorNode.next.number = newOperand;
        }

        /**
         * @return the the first number of the linked list.
         */
        public double getHeadNum() {
            return head.number;
        }

        /**
         * @return true if the list has only one node, false otherwise.
         */
        public boolean isRootOnly() {
            if(head == null){
                return false;
            }
            return head.next == null;
        }

        /**
         * @return true if the next node is an operator, false if it's a number
         */
        public boolean isNextNodeAnOperator() {
            if(currentOperatorNode.next == null) {
                return false;
            }
            return currentOperatorNode.next.operator != '\0';
        }

        /**
         * Removes the given node.
         *
         * @param toRemove  node that is supposed to be removed
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
         * Appends a node to the list with given operation or number.
         *
         * @param op  a character representing the operation supposed to be
         *            appended
         * @param num  a number supposed to be appended
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
     * Parses the input string into a doubly linked list - each operand and
     * operator is a single node.
     *
     * Before appending operands and operators to the list, removes all white
     * characters, converts mod to '%, pow to '^', root to '|'
     * and replaces ',' with '.'.
     *
     * @param input  a string containing exactly what the user entered
     * @return list  The doubly linked list containing all operations and
     *               operands
     * @throws ArithmeticException if it fails to parse the input
     */
    public static DoublyLinkedList parser(String input)
            throws ArithmeticException{
        DoublyLinkedList list = new DoublyLinkedList();

        // Removes all whitespaces and non-visible characters
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
            if(tmp.charAt(tmp.indexOf('^') + 1) == '-'){
                tmp = tmp.replace('-', '~');
            }
            input = input.replace(orig, tmp);
        }

        // Replace all commas with dots
        input = input.replaceAll(",", ".");

        // Fill list with operators and numbers
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
                throw new ArithmeticException("Invalid character inserted");
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
     * @param num  A double precision number
     * @return true if the number is natural, false if it is not
     */
    public static boolean isNatural(double num){
        return Math.round(num) == num;
    }

    /**
     * Checks the parsed input for invalid operations, forbidden characters
     * and so on.
     *
     * @param list  The doubly linked list containing all operations and
     *              operands
     * @throws ArithmeticException if the input is invalid
     */
    public static void validate(DoublyLinkedList list)
            throws ArithmeticException{
        // invalid factorial input
        list.resetCurrentNode();
        while(list.findOperator('!', true)) {
            if(!isNatural(list.getFirstOperand())){
                throw new ArithmeticException(
                        "The factorial argument is not a natural number");
            }
        }

        // modulo natural number
        list.resetCurrentNode();
        while(list.findOperator('%', true)) {
            if(!isNatural(list.getSecondOperand())){
                throw new ArithmeticException("The divisor in the modulo " +
                        "operation is not a natural number.");
            }
        }

        // division by zero
        list.resetCurrentNode();
        while(list.findOperator('/', true)) {
            if(list.getSecondOperand() == 0){
                throw new ArithmeticException("Division by zero.");
            }
        }

        // root problems
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
                        "The root index is not a whole number");
            }
        }

        // power problems
        list.resetCurrentNode();
        while(list.findOperator('^', true)) {
            if(Math.round(list.getSecondOperand()) != list.getSecondOperand()){
                throw new ArithmeticException(
                        "The exponent of power is not a whole number");
            }
        }

        // two operators without a number separating them
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
                            "between which there is no operand");
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
     *
     * Precedence: factorial > power > root > modulo >
     * division and multiplication > subtraction and addition
     *
     * For easier implementation, every division is converted to multiplication
     * (2/2 -> 2 * 1/2) and subtraction to addition (2-2 -> 2 + -2) before
     * the calculation.
     *
     * @param list  The doubly linked list containing all operations and
     *              operands
     */
    public static void calculate(DoublyLinkedList list){
        double result;

        // calculate fact
        while(list.findOperator('!', false)) {
            //
            result = list.getFirstOperand();
            for(int i = (int)result - 1; i > 0; i--) {
                result *= i;
            }
            list.writeInResult(result, false);
        }

        // calculate power
        while(list.findOperator('^', false)) {
            result = Math.pow(list.getFirstOperand(),
                    list.getSecondOperand());
            list.writeInResult(result, true);
        }

        // calculate root
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

        // Calculate modulo
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

        // Calculate multiplication
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

        // Calculate plus
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
     * @return result  a string containing the result of the calculation -
     *                 a single number in decimal form with a maximum of 8
     *                 floating point digits (or exponential form if the
     *                 number is bigger than 2^52)
     * @throws ArithmeticException if the input is not computable
     */
    public static String main(String input)
            throws ArithmeticException{

        // If the string is empty
        if(input.equals("")){
            return "";
        }

        // Parse the input string into a doubly linked list
        DoublyLinkedList list = parser(input);

        // Check if the input string is valid
        validate(list);

        // Calculate the result using the doubly linked list
        calculate(list);

        if(!list.isRootOnly()) {
            throw new ArithmeticException(
                    "Could not compute the entered expression.");
        }

        double result = list.getHeadNum();

        if(result > Math.pow(2, 52)) {
            return Double.toString(result);
            // Return the number in an exponent format
        }

        // If the result is a natural number, return it as an integer
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
