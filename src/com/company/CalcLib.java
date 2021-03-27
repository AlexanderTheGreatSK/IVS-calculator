package com.company;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CalcLib {

    private static final int floatingPointDigits = 8;

    public static class DoublyLinkedList {

        /*
         * The Node class represents either an operator or a number
         * if a Node is an operator, number = 0.0
         * if a Node is a number, operator = '\0'
         */
        static class Node {
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

        Node getHead() {
            return head;
        }

        Node getTail() {
            Node node = getHead();
            if(node == null) {
                return null;
            }
            while(node.next != null) {
                node = node.next;
            }
            return node;
        }

        Node getNodeByOperator(char op) {
            Node node = head;
            while(node != null) {
                if(node.operator == op) {
                    return node;
                }
                node = node.next;
            }
            return null;
        }

        Node getNodeByNumber(double num){
            Node node = head;
            while(node != null) {
                if(node.number == num) {
                    return node;
                }
                node = node.next;
            }
            return null;
        }

        boolean append(char op, double num) {
            Node newNode = new Node(op, num);
            Node tail = getTail();
            if(tail == null) {
                head = newNode;
                head.next = null;
                head.prev = null;
            }
            else {
                tail.next = newNode;
                newNode.next = null;
                newNode.prev = tail;
            }
            return true;
        }

        boolean remove(Node toRemove) {
            if(toRemove == null || getHead() == null) {
                return false;
            }
            if(head == toRemove) {
                head = head.next;
            }
            if(toRemove.next != null) {
                toRemove.next.prev = toRemove.prev;
            }
            if(toRemove.prev != null) {
                toRemove.prev.next = toRemove.next;
            }
            return true;
        }

    }


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

    public static DoublyLinkedList parser(String input) {
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
                return null;
            }
            list.append(ch, 0);
        }
        if(!temp.isEmpty()) {
            list.append('\0', Double.parseDouble(temp));
        }
        return list;
    }

    public static String validate(DoublyLinkedList list){
        // invalid factorial input
        // modulo natural number
        // division by zero
        // no operator

        return "valid";
    }

    public static void writeTwoOperandOperation(char operator,
            double num, DoublyLinkedList list) {
        list.getNodeByOperator(operator).prev.number = num;
        list.remove(list.getNodeByOperator(operator).next);
        list.remove(list.getNodeByOperator(operator));
    }

    public static void calculate(DoublyLinkedList list){
        double result;

        // calculate fact
        while(list.getNodeByOperator('!') != null) {
            result = list.getNodeByOperator('!').prev.number;
            for(int i = (int)result - 1; i > 0; i--) {
                result *= i;
            }
            list.getNodeByOperator('!').prev.number = result;
            list.remove(list.getNodeByOperator('!'));
        }

        // calculate power
        while(list.getNodeByOperator('^') != null) {
            result = Math.pow(list.getNodeByOperator('^').prev.number,
                    list.getNodeByOperator('^').next.number);
            writeTwoOperandOperation('^', result, list);
        }

        // calculate root
        while(list.getNodeByOperator('|') != null) {
            result = Math.pow(list.getNodeByOperator('|').prev.number,
                    1.0f/list.getNodeByOperator('|').next.number);
            writeTwoOperandOperation('|', result, list);
        }

        // Calculate modulo
        while(list.getNodeByOperator('%') != null) {
            result = list.getNodeByOperator('%').prev.number %
                    list.getNodeByOperator('%').next.number;
            writeTwoOperandOperation('%', result, list);
        }

        // Convert division to multiplication
        while(list.getNodeByOperator('/') != null) {
            list.getNodeByOperator('/').next.number =
                    1 / list.getNodeByOperator('/').next.number;
            list.getNodeByOperator('/').operator = '*';
        }

        // Calculate multiplication
        while(list.getNodeByOperator('*') != null) {
            result = list.getNodeByOperator('*').prev.number *
                    list.getNodeByOperator('*').next.number;
            writeTwoOperandOperation('*', result, list);
        }

        // Convert minus sings to plus signs (negating the next operand)
        while(list.getNodeByOperator('-') != null) {
            list.getNodeByOperator('-').next.number =
                    - list.getNodeByOperator('-').next.number;
            list.getNodeByOperator('-').operator = '+';
        }

        // Calculate plus
        while(list.getNodeByOperator('+') != null) {
            result = list.getNodeByOperator('+').prev.number +
                    list.getNodeByOperator('+').next.number;
            writeTwoOperandOperation('+', result, list);
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
        if(list == null){
            return "BAD";
        }

        // Check if the input string is valid
        String validation = validate(list);
        if(!validation.equals("valid")) {
            return validation;
        }

        // Calculate the result using the doubly linked list
        calculate(list);
        if(list.getHead().next != null) {
            return "ERROR - could not compute";
        }
        double result = list.getHead().number;

        // If the result is a natural number, return it as an integer
        if(result == Math.floor(result)) {
            long newRes = Math.round(result);
            return Long.toString(newRes);
        }

        // Truncate the result to 8 decimal places and return
        String pattern = "#.########";
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(result);
    }
}
