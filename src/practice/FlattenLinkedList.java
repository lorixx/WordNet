package practice;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 9/1/13
 * Time: 9:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class FlattenLinkedList {

    public class ListNode {

        ListNode down;
        ListNode next;
        int value;

        public ListNode(int v) {
            this.value = v;
        }
    }

    public ListNode flattenLinkedList(ListNode head) {
        ListNode result = flattenDownLinkedList(head);
        while (result != null) {
            result.next = result.down;
            result.down = null;
            result = result.next;
        }
        return head;
    }

    private ListNode flattenDownLinkedList(ListNode head) {
        if (head == null) return null;  // edge case
        if (head.next != null) {
            ListNode rest = flattenDownLinkedList(head.next);
            return mergeTwoList(head, rest);

        } else
            return head;
    }

    // merge two sorted linked list
    private ListNode mergeTwoList(ListNode a, ListNode b) {
        if (a == null) return b;
        if (b == null) return a;

        ListNode result;
        ListNode aDown = a.down;
        ListNode bDown = b.down;

        if (a.value < b.value) {
            result = a;
            a.down = mergeTwoList(aDown, b);
        } else {
            result = b;
            b.down = mergeTwoList(a, bDown);
        }
        return result;
    }

    public void test() {

        ListNode a = new ListNode(5);
        ListNode b = new ListNode(7);
        ListNode c = new ListNode(8);
        ListNode d = new ListNode(30);

        ListNode e = new ListNode(10);
        ListNode f = new ListNode(20);

        ListNode g = new ListNode(19);
        ListNode h = new ListNode(22);
        ListNode i = new ListNode(50);

        ListNode j = new ListNode(28);
        ListNode k = new ListNode(35);
        ListNode m = new ListNode(40);
        ListNode n = new ListNode(45);

        a.down = b; b.down = c; c.down = d;  a.next = e;

        e.down = f; e.next = g;

        g.down = h; h.down = i; g.next = j;

        j.down = k; k.down = m; m.down = n;


        ListNode result = flattenLinkedList(a);

        while (result != null) {
            System.out.println(result.value);
            result = result.next;
        }




    }

    public static void main(String[] args) {
        FlattenLinkedList flattenLinkedList = new FlattenLinkedList();
        flattenLinkedList.test();




    }
}
