package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 8/7/13
 * Time: 2:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class PartitionList {

    public ListNode partition(ListNode head, int x) {
        // Start typing your Java solution below
        // DO NOT write main() function

        ListNode newHead = null;
        ListNode less = null;
        ListNode lessHead = null;
        ListNode greater = null;
        ListNode greaterHead = null;

        ListNode currentNode = head;
        while (currentNode != null) {
            ListNode temp = currentNode.next;
            if (currentNode.val < x) {
                if (lessHead == null) {
                    lessHead = less = currentNode;
                    less.next = null;
                } else {
                    less.next = currentNode;
                    less = currentNode;
                    less.next = null;
                }
            } else {
                if (greaterHead == null) {
                    greaterHead = greater = currentNode;
                    greaterHead.next = null;
                } else {
                    greater.next = currentNode;
                    greater = currentNode;
                    greater.next = null;
                }
            }

            currentNode = temp;
        }

        if (lessHead != null) less.next = greater;
        return lessHead == null ? greaterHead : lessHead;

    }


    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}
