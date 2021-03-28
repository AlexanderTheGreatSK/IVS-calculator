package com.company;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CalcLib {

    public static class DoublyLinkedList {

        /*
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

        /*
         * Finds and returns the tail (last node).
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

        public void resetCurrentNode() {
            currentOperatorNode = head;
        }

        /*
         * Finds the first node (from head to tail) representing the given operator
         * and saves it into currentOperatorNode.
         * Returns true if given operator was found, or false if given operator was not present in the list.
         * If after the last call of this method the node was not removed, TODO
         */
        public boolean findOperator(char op, boolean nextNotFirst) {
            Node node = head;
            if(nextNotFirst) {
                node = currentOperatorNode.next;
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

        /*
         * Finds a node previous of currentOperatorNode
         * and returns its number.
         * Returns 0 if the node is not found.
         */
        public double getFirstOperand() {
            if (currentOperatorNode == null) {
                return 0;
            }
            if (currentOperatorNode.prev == null) {
                return 0;
            }
            return currentOperatorNode.prev.number;
        }



        /*
         * Finds a node next of currentOperatorNode
         * and returns its number.
         * Returns 0 if the node is not found.
         */
        public double getSecondOperand() {
            if (currentOperatorNode == null) {
                return 0;
            }
            if (currentOperatorNode.next == null) {
                return 0;
            }
            return currentOperatorNode.next.number;
        }

        /*
         * Writes the given number into the node previous of currentOperatorNode.
         * Deletes currentOperatorNode.
         * If isTwoOperandOperation is true, deletes the node next of currentOperatorNode as well.
         * Returns true if finished successfully, or false if nodes weren't found.
         */
        public boolean writeInResult(double num, boolean isTwoOperandOperation) {
            if (currentOperatorNode == null) {
                return false;
            }
            if (currentOperatorNode.prev == null) {
                return false;
            }
            currentOperatorNode.prev.number = num;
            if (isTwoOperandOperation) {
                remove(currentOperatorNode.next);
            }
            return remove(currentOperatorNode);
        }

        /*
         * Changes the operator and number to given values.
         * Returns true if finished successfully, or false if nodes weren't found.
         */
        public boolean changeOperation(char newOp, double newSecond) {
            if (currentOperatorNode == null) {
                return false;
            }
            if (currentOperatorNode.next == null) {
                return false;
            }
            currentOperatorNode.operator = newOp;
            currentOperatorNode.next.number = newSecond;
            return true;
        }

        /*
         * Returns the number in the head node.
         * Returns 0 if the list is empty.
         */
        public double getHeadNum() {
            if(head == null){
                return 0;
            }
            return head.number;
        }

        /*
         * Returns true if the list has only one node.
         * Returns false if the list is empty or has more than one node.
         */
        public boolean isRootOnly() {
            if(head == null){
                return false;
            }
            return head.next == null;
        }

        /*
         * Removes the given node.
         * Returns true if finished successfully, or false if the node wasn't found.
         */
        private boolean remove(Node toRemove) {
            if (toRemove == null || head == null) {
                return false;
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
            return true;
        }

        /*
         * Adds a node to the end of the list with given parameters.
         *
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

    /*
     * Prints the list
     */
    private static void testPrint(DoublyLinkedList list) {
        DoublyLinkedList.Node tmp = list.head;
        while(tmp != null) {
            if(tmp.operator != '\0') {
                System.out.println(tmp.operator);
            } else {
                System.out.println(tmp.number);
            }
            tmp = tmp.next;
        }
    }
    
    /*
    private static void test(String input){
        DoublyLinkedList list = new DoublyLinkedList();
        list.append('x', 1);
        list.append('a', 0);
        list.append('b', 0);
        list.append('c', 0);

        testPrint(list);
        System.out.println("Remove b:");
        list.remove(list.getHead().next.next);
        testPrint(list);
        System.out.println("Remove a:");
        list.remove(list.getHead().next);
        testPrint(list);
        System.out.println("Remove c:");
        list.remove(list.getHead().next);
        testPrint(list);
        System.out.println("Remove the input:");
        list.remove(list.getHead());
        testPrint(list);
    }
     */

    public static DoublyLinkedList parser(String input)
            throws ArithmeticException{
        DoublyLinkedList list = new DoublyLinkedList();
        String operatorsList = "+-*/!%^|";

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
            tmp = tmp.replace(",", "^");
            input = input.replace(orig, tmp);
        }
        
        // Converts "root" to '|', for example root(5,2) => 5|2 (represents sqrt(5))
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
            tmp = tmp.replace(",", "|");
            input = input.replace(orig, tmp);
            indexFrom = input.indexOf("root");
        }

        // Replace all commas with dots
        input = input.replaceAll(",", ".");

        // Fill list with operators and numbers
        String temp = "";
        int len = input.length();
        char ch = '0';
        for(int i = 0; i < len; i++){
            ch = input.charAt(i);
            if((ch >= '0' && ch <= '9') || ch == '.'){
                temp = temp + ch;
                continue;
            }else if(!temp.isEmpty()){
                list.append('\0', Double.parseDouble(temp));
                temp = "";
            }
            if(operatorsList.indexOf(ch) == -1){
                throw new ArithmeticException("Invalid character inserted");
            }
            list.append(ch, 0);
        }
        if(!temp.isEmpty()) {
            list.append('\0', Double.parseDouble(temp));
        }
        return list;
    }

    public static boolean isNatural(double num){
        return Math.round(num) == num;
    }

    public static String validate(DoublyLinkedList list)
            throws ArithmeticException{
        // invalid factorial input
        list.resetCurrentNode();
        while(list.findOperator('!', true)) {
            if(!isNatural(list.getFirstOperand())){
                throw new ArithmeticException("The factorial argument is not a natural number");
            }
        }

        // modulo natural number
        list.resetCurrentNode();
        while(list.findOperator('%', true)) {
            if(!isNatural(list.getSecondOperand())){
                throw new ArithmeticException("The divisor in the modulo " +
                        "operation is not a natural number");
            }
        }

        // division by zero
        list.resetCurrentNode();
        while(list.findOperator('/', true)){
            if(list.getSecondOperand() == 0){
                throw new ArithmeticException("Division by zero");
            }
        }

        list.resetCurrentNode();
        //while(list.findOperator('/', true)) {
        //}


        // even root of a negative number


        // anything else than two numbers separated by a comma in parentheses
        // two operators without a number separating them
        // odd number of parentheses
        return "valid";
    }

    /*
    public static void writeTwoOperandOperation(char operator,
            double num, DoublyLinkedList list) {
        list.findOperator(operator).prev.number = num;
        list.remove(list.findOperator(operator).next);
        list.remove(list.findOperator(operator));
    }
    */

    public static void calculate(DoublyLinkedList list){
        double result;

        // calculate fact
        while(list.findOperator('!', false)) {
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
            result = Math.pow(list.getFirstOperand(),
                    1.0f/list.getSecondOperand());
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
            list.changeOperation('*', 1 / list.getSecondOperand());
        }

        // Calculate multiplication
        while(list.findOperator('*', false)) {
            result = list.getFirstOperand() *
                    list.getSecondOperand();
            list.writeInResult(result, true);
        }

        // Convert minus sings to plus signs (negating the next operand)
        while(list.findOperator('-', false)) {
            list.changeOperation('+', - list.getSecondOperand());
        }

        // Calculate plus
        while(list.findOperator('+', false)) {
            result = list.getFirstOperand() +
                    list.getSecondOperand();
            list.writeInResult(result, true);
        }
    }

    public static String rmFloatingPointIfInt(String input){
        double doubleNum = Double.parseDouble(input);
        long longNum = (long)doubleNum;
        if(doubleNum - longNum != 0) {
            return input;
        }
        return Long.toString(longNum);
    }


    public static String main(String input) {

        // If the string is empty
        if(input.equals("")){
            return "";
        }

        // Parse the input string into a doubly linked list
        DoublyLinkedList list = parser(input);

        // Check if the input string was successfully parsed
        /*
        if(list == null){
            return "BAD";
        }
        */

        // Check if the input string is valid
        String validation = validate(list);
        if(!validation.equals("valid")) {
            return validation;
        }

        // Calculate the result using the doubly linked list
        calculate(list);

        if(!list.isRootOnly()) {
            return "ERROR - could not compute";
        }
        double result = list.getHeadNum();

        if(result > Math.pow(2, 52)) {
            return Double.toString(result); // Return the number in an exponent format
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
