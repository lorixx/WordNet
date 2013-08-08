package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 8/6/13
 * Time: 11:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class DuplicateNodes {

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode deleteDuplicates(ListNode head) {
        // Start typing your Java solution below
        // DO NOT write main() function
        if (head == null || head.next == null) return head;

        ListNode newHead = null;
        ListNode newEnd = null;

        ListNode prev = head;   // 1
        ListNode currentNode = head.next; // 2
        boolean needsToAdd = true;
        while (currentNode != null) {
            ListNode temp = currentNode.next;
            if (currentNode.val != prev.val) { // 2 != 3
                // add prev
                if (needsToAdd) {

                    if (newHead == null) {
                        newHead = newEnd = prev; // 1
                        newEnd.next = null;
                    } else {
                        newEnd.next = prev; // update
                        newEnd = prev;
                        newEnd.next = null;
                    }
                }

                prev = currentNode; // 2
                needsToAdd = true; // for the rest


            } else {
                needsToAdd = false;
            }

            currentNode = temp; // 3
        }

        if (needsToAdd) {
            if (newHead == null) {
                newHead = newEnd = prev;
            } else {
                newEnd.next = prev; // update

            }
        }
        return newHead;
    }

    public void test() {
        ListNode a = new ListNode(1);
        ListNode b = new ListNode(2);
        ListNode c = new ListNode(2);
        a.next = b; b.next = c;

        ListNode result = this.deleteDuplicates(a);
        while(result != null) {
            System.out.println(result.val);
            result = result.next;
        }
    }

    public static void main(String[] args) {
        DuplicateNodes duplicateNodes = new DuplicateNodes();
        duplicateNodes.test();

    }

}
