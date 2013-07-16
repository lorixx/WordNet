package interview;

public class FindNthNodeFromEnd {

    public class ListNode {
        int item;
        ListNode next;

        public ListNode(int v, ListNode next) {
            this.next = next;
            item =v;
        }
    }

    public ListNode findNthFromTheEnd(ListNode head, int k) {

        if (head == null) return null;

        if (k < 1) return null;

        ListNode currentNode = head;

        for (int i = 0; i < k; i++) {
            if (currentNode.next == null) return null;
            currentNode = currentNode.next;
        }

        ListNode start = head;
        ListNode end = currentNode;

        while (end != null) {

            if (end.next == null) { // if it is the end
                return start;
            }
            end = end.next;
            start = start.next;
        }

        return null;

    }

    public void test() {
        ListNode a = new ListNode(5, null);
        ListNode b = new ListNode(4, a);
        ListNode c = new ListNode(3, b);
        ListNode d = new ListNode(2, c);
        ListNode e = new ListNode(1, d);
        ListNode f = new ListNode(0, e);

        System.out.println(this.findNthFromTheEnd(f, 6).item);
    }

    public static void main(String[] args) {
        FindNthNodeFromEnd findNthNodeFromEnd = new FindNthNodeFromEnd();
        findNthNodeFromEnd.test();
    }

}
