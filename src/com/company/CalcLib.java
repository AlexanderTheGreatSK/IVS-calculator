package com.company;

public class CalcLib {

    public static class DoublyLinkedList {

        static class Node {
            char op;
            Double num;
            Node prev;
            Node next;

            Node(char inputChar, double inputNum) {
                op = inputChar;
                num = inputNum;
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
            System.out.println(tmp.op);
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
     //*/

    public static DoublyLinkedList parser(String input) {
        DoublyLinkedList list = new DoublyLinkedList();

        input = "neco 5 +d 4 6d+5 pow(2.45, 5.4564) neco dalsi";
        //Removes all whitespaces and non-visible characters
        input = input.replaceAll("\\s+","");

        //Replace all "mod" with '%'


        //Convert "pow" and "root" to ^ and |
        int indexFrom = input.indexOf("pow");
        while(indexFrom != -1){
            int indexTo = input.indexOf(")", indexFrom);
            String orig = input.substring(indexFrom, indexTo + 1);
            String tmp = orig;
            tmp = tmp.replace("pow(", ""); // rozdělit na 2 až to nebude fungovat
            tmp = tmp.replace(")", "");
            tmp = tmp.replace(",", "^");
            input = input.replace(orig, tmp);
            indexFrom = input.indexOf("pow");
        }

        indexFrom = input.indexOf("root");
        while(indexFrom != -1){
            int indexTo = input.indexOf(")", indexFrom);
            String orig = input.substring(indexFrom, indexTo + 1);
            String tmp = orig;
            tmp = tmp.replace("root(", ""); // rozdělit na 2 až to nebude fungovat
            tmp = tmp.replace(")", "");
            tmp = tmp.replace(",", "|");
            input = input.replace(orig, tmp);
            indexFrom = input.indexOf("root");
        }

        input.replaceAll(",", ".");
        char[] operators = {'+', '-', '*', '/', '!', '%', '^', '|'};
        /*
            ^ |
            !
            %
            * /
            + -

        1532412+ pow(3, 2) * 8!

        1532412
        +
        3
        ^
        2
        *
        8
        !
        */
        String temp = "";
        char c = '0';
        int len = input.length();
        for(int i = 0; i < len; i++){
            c = input.charAt(i);
            if((c >= '0' && c <= '9') || c == '.'){
                temp = temp + c;
                continue;
            }else if(!temp.isEmpty()){
                list.append('\0', Double.parseDouble(temp));
                temp = "";
            }

        }

        return list;
    }

    public static String main(String input) {
        DoublyLinkedList list = parser(input);
        return input;
    }
}

