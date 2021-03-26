package com.company;

public class CalcLib {

    public static class dll {

        static class Node {
            String str;
            Double num;
            Node prev;
            Node next;

            Node(String input) {
                str = input;
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

        boolean append(String str) {
            Node newNode = new Node(str);
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

    private static void testPrint(dll list) {
        dll.Node tmp = list.head;
        while(tmp != null) {
            System.out.println(tmp.str);
            tmp = tmp.next;
        }
    }
    private static void test(String input){
        dll list = new dll();
        list.append(input);
        list.append("str1");
        list.append("str2");
        list.append("str3");

        testPrint(list);
        System.out.println("Remove str2:");
        list.remove(list.getHead().next.next);
        testPrint(list);
        System.out.println("Remove str1:");
        list.remove(list.getHead().next);
        testPrint(list);
        System.out.println("Remove str3:");
        list.remove(list.getHead().next);
        testPrint(list);
        System.out.println("Remove the input:");
        list.remove(list.getHead());
        testPrint(list);
    }

    public static String main(String input) {
        test(input);
        return input;
    }
}