package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/31/13
 * Time: 10:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class RemoveNthNode {

    public class ListNode {
        int val;
        ListNode next;
    }

    // sliding window, two pointers
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // Start typing your Java solution below
        // DO NOT write main() function
        if (head == null || n < 1) return null;
        ListNode start = head;
        ListNode end = head;
        ListNode prev = head;

        for (int i = 1; i < n; i++) {
            if (end.next == null) return null;
            end = end.next;
        }

        if (end.next == null) {
            head = head.next;
            return head; // if currently is the head
        }

        // now the prev should have been tracked
        start = start.next;
        end = end.next;


        while (end.next != null) {
            end = end.next;
            start = start.next;
            prev = prev.next;
        }

        prev.next = start.next;
        return head;

    }
}
