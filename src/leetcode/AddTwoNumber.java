package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: zhuang
 * Date: 5/2/13
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddTwoNumber {

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        if (l1 == null && l2 == null) return null;
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        int addOne = 0;
        ListNode result = null;
        ListNode start = null;
        int digit;

        ListNode r1 = l1, r2 = l2;
        int first, second;
        while (r1 != null || r2 != null) {

            first = r1 == null ? 0 : r1.val;
            second = r2 == null ? 0 : r2.val;

            int sum = first + second + addOne;
            if (sum > 9) {
                digit = sum - 10;
                addOne = 1;
            } else {
                digit = sum;
                addOne = 0;
            }

            if (result == null) {
                start = result = new ListNode(digit);
            } else {
                result.next = new ListNode(digit);
                result = result.next;
            }

            if (r1 != null) r1 = r1.next;
            if (r2 != null) r2 = r2.next;
        }

        if (addOne == 1)
            result.next = new ListNode(addOne);

        return start;
    }
}
