package pie;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 5/5/13
 * Time: 12:19 AM
 * To change this template use File | Settings | File Templates.
 */

/*
 * Some note about implementing my own Stack using linked list:
 * 1. implements Iterable and use <Item> for generic programming technique.
 * 2. Iterator is a good design pattern for separating the codes.
 */
public class Stack<Item> implements Iterable<Item> {

    private int l;

    private Node head;

    public Stack() {
        l = 0;
        head = null;
    }

    public void push(Item v) {
        if (head == null) {
            head = new Node(v);
            l++;
        } else {
            Node newNode = new Node(v);
            newNode.next = head;
            head = newNode;
            l++;
        }
    }

    public Item pop() {
        if (l == 0) throw new IllegalAccessError("The stack is empty");
        else {
            Item v = head.v;
            head = head.next;
            l--;
            return v;
        }

    }

    public boolean isEmpty() {
         return l == 0;
    }

    public int length() {
        return l;
    }

    private class Node {
        public Item v;
        public Node next;
        public Node(Item data) {
            v = data;
        }
    }
    public Iterator iterator() {
         return new StackIterator();
    }

    private class StackIterator implements Iterator {

        private Node current = head;

        public boolean hasNext() {
             return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.v;
            current = current.next;
            return item;
        }

    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(1);stack.push(2);stack.push(3);

//        while(!stack.isEmpty()) {
//            System.out.print("printing");
//            System.out.println(stack.pop());
//        }
        for (int i : stack) {
             System.out.println(i);
        }
    }
}
