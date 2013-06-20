package practice;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 6/19/13
 * Time: 11:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReverseLinkedList {

    public static Node reverseLinkedList(Node head) {
        if (head == null) return null;
        if (head.next == null) return head;

        Node currentNode, originalHead, resultHead;
        currentNode = originalHead = resultHead = head;

        while (currentNode != null) {
            originalHead = currentNode.next;
            if (currentNode == head)
                currentNode.next = null;
            else
                currentNode.next = resultHead;

            resultHead = currentNode;
            currentNode = originalHead;
        }
        return resultHead;
    }

    public void test() {
        Node a = new Node(5, null);
        Node b = new Node(4, a);
        Node c = new Node(3, b);
        Node d = new Node(2, c);
        Node e = new Node(1, d);
        Node f = new Node(0, e);

        Node head = f;
        Node currentNode = head;
        while (currentNode != null) {
            System.out.println(currentNode.value);
            currentNode = currentNode.next;
        }
        System.out.println("===============");
        Node newHead = ReverseLinkedList.reverseLinkedList(head);
        currentNode = newHead;
        while (currentNode != null) {
            System.out.println(currentNode.value);
            currentNode = currentNode.next;
        }
        System.out.println("===============");
        newHead = ReverseLinkedList.reverseLinkedList(newHead);
        currentNode = newHead;
        while (currentNode != null) {
            System.out.println(currentNode.value);
            currentNode = currentNode.next;
        }






    }

    private class Node {
        public int value;
        public Node next;

        public Node(int v, Node next) {
            this.value = v;
            this.next = next;
        }
    }

    public static void main(String[] args) {
         ReverseLinkedList engine = new ReverseLinkedList();
        engine.test();
    }
}


