package com.company;

public class CalcLib {

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
            Node tmp = getHead();
            if(tmp == null) {
                return null;
            }
            while(tmp.next != null){
                tmp = tmp.next;
            }
            return tmp;
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
            if(head == toRemove){
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
        char[] operators = {'+', '-', '*', '/', '!', '%', '^', '|'};

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

        // Fill list with operators and values
        boolean isOperator;
        String temp = "";
        int len = input.length();
        char c = '0';
        for(int i = 0; i < len; i++){
            c = input.charAt(i);
            isOperator = false;
            if((c >= '0' && c <= '9') || c == '.'){
                temp = temp + c;
                continue;
            }else if(!temp.isEmpty()){
                list.append('\0', Double.parseDouble(temp));
                temp = "";
            }
            for(int j = 0; j < 8; j++){
                if(c == operators[j]){
                    list.append(c, 0);
                    isOperator = true;
                    break;
                }
            }
            if(!isOperator){
                return null;
            }
        }
        if(!temp.isEmpty()) {
            list.append('\0', Double.parseDouble(temp));
        }
        return list;
    }

    public static String main(String input) {
        DoublyLinkedList list = parser(input);
        if(list == null){
            return "BAD";
        }
        testPrint(list);
        return input;
    }
}

