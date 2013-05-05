package pie;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 5/5/13
 * Time: 1:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class MaintainTailPointer<Item> {

    public Node head = null;

    public Node last = null;

    private class Node {
        Item item;
        Node next;
    }

    public MaintainTailPointer() {

    }

    public void add(Item i) {
        Node node = new Node();
        node.next = null;
        node.item = i;
        if (head == null) {


            head = last = node;
        } else {
            last.next = node;
            last = node;
        }
    }

    private boolean isExist(Node i) {
        Node current = head;
        while (current != null) {
            if (current == i) {
                return true;
            }
        }
        return false;
    }

    public boolean delete(Node i) {

        if (head == null) return false; // linked list is empty

        if (!isExist(i)) return false;


        if (i == head) { //if we are to delete the head
            head = head.next;

            if (last == i) { // if it is the only node
                last = null;
            }
        } else {
            // start deleting the regular case
            Node prevNode = head;
            while (prevNode.next != i) {
                prevNode = prevNode.next;
            }

            prevNode.next = i.next;

            if (prevNode.next == null) // handle the case that deleting the last
                last = prevNode;
        }

        return true;

    }

    public boolean insertAfter(Node i, Item item) {

        if (!isExist(i)) return false;

        Node newNode = new Node();
        newNode.item = item;
        newNode.next = null;

        i.next = newNode;
        if (i == last)
            last = newNode;

        return true;
    }
}
