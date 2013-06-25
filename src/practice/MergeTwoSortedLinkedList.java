package practice;

/**
 * Created with IntelliJ IDEA.
 * User: zhuang
 * Date: 6/24/13
 * Time: 12:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class MergeTwoSortedLinkedList {


    public Node mergeTwoLists(Node a, Node b) {
        if (a == null && b == null) {
            return null;
        }

        if (a == null) {
            return b;
        } else if (b == null) {
            return a;
        }
        Node head, tail;
        if (a.value < b.value) {
            head = tail = a;
            a = a.next;
        } else {
            head = tail = b;
            b = b.next;
        }

        while (a != null || b != null) {
            if (a == null) {
                tail = tail.next = b;
                b = b.next;
                continue;
            } else if (b == null) {
                tail = tail.next = a;
                a = a.next;
                continue;
            }
            if (a.value < b.value) {
                tail = tail.next = a;
                a = a.next;

            } else {
                tail = tail.next = b;
                b = b.next;
            }
        }

        return head;
    }

    public void test() {
        Node a = new Node();
        a.value = 0;

        Node b = new Node();
        b.value = 3;
        a.next = b;

        Node c = new Node();
        c.value = 10;
        b.next = c;

        Node d = new Node();
        d.value = 90;
        c.next = d;

        Node e = new Node();
        e.value = 1;

        Node f = new Node();
        f.value = 5;
        e.next = f;

        Node g = new Node();
        g.value = 13;
        f.next = g;

        Node h = new Node();
        h.value = 94;
        g.next = h;

        Node result = this.mergeTwoLists(a, e);

        while(result != null) {
            System.out.println(result.value);
            result = result.next;
        }



    }

    private class Node {
        int value;
        Node next;
    }

    public static void main(String[] args) {
        MergeTwoSortedLinkedList tool = new MergeTwoSortedLinkedList();
        tool.test();
    }

}
