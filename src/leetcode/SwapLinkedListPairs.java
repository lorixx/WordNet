package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/22/13
 * Time: 10:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class SwapLinkedListPairs {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode swapPairs(ListNode head) {
        // Start typing your Java solution below
        // DO NOT write main() function

        ListNode old = null, first = head, second = null, third = null;

        while (first != null && first.next != null) {

            second = first.next;
            third = second.next;

            second.next = first;
            first.next = third;
            if (old != null) old.next = second;

            old = first;
            first = third;

            if (old == head) head = second;
        }
        return head;
    }
}
