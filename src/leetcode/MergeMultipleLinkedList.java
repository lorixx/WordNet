package leetcode;

import java.util.ArrayList;

/**
 * This is an interesting problem.
 * First, I made mistake for not considering lists empty list situation
 * Second, I did not update the linked list next for the result reference
 * Third, I did not create a new node for the result list
 *
 * The time complexity would be O(nlog(m)), n is the size for linked list, m is the size for the array
 *
 */
public class MergeMultipleLinkedList {

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode mergeKLists(ArrayList<ListNode> lists) {
        // Start typing your Java solution below
        // DO NOT write main() function
        if (lists.size() < 1) return null;
        return mergeKLists(lists, 0, lists.size() - 1);

    }

    private ListNode mergeKLists(ArrayList<ListNode> lists, int start, int end) {

        if (start == end) return lists.get(start);

        int mid = start + (end - start) / 2;
        ListNode first = mergeKLists(lists, start, mid);
        ListNode second = mergeKLists(lists, mid + 1, end);

        return mergeTwoList(first, second);
    }


    public ListNode mergeTwoList(ListNode first, ListNode second) {
        if (first == null && second == null) return null;
        if (first == null) return second;
        if (second == null) return first;

        ListNode result = new ListNode(0); // create a dummy node
        ListNode head = result; // keep track of the head

        while (first != null || second != null) {
            if (first == null) {
                result.next = second;
                break;
            } else if (second == null) {
                result.next = first;
                break;
            }

            if (first.val < second.val) {
                result.next = new ListNode(first.val);
                first = first.next;
                result = result.next;
            } else if (first.val > second.val) {
                result.next = new ListNode(second.val);
                second = second.next;
                result = result.next;
            } else {
                result.next = new ListNode(first.val);
                result = result.next;
                result.next = new ListNode(second.val);
                result = result.next;
                first = first.next;
                second = second.next;
            }
        }
        return head.next;
    }

    public void test() {
        ListNode a = new ListNode(1);
        ListNode b = new ListNode(1);
        ListNode c = new ListNode(2);
        ListNode d = new ListNode(2);
        ListNode e = new ListNode(2);
        ListNode f = new ListNode(3);
        ListNode g = new ListNode(1);

        a.next = c; c.next = d;
        b.next = e; e.next = f;

        ListNode result = mergeTwoList(a, b);

        while (result != null) {
            System.out.println(result.val);
            result = result.next;

        }
    }

    public static void main(String[] args) {
        MergeMultipleLinkedList mergeMultipleLinkedList = new MergeMultipleLinkedList();
        mergeMultipleLinkedList.test();
    }




}
