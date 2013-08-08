package interview;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 8/7/13
 * Time: 2:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class TripAdvisorPhone {


//
//    @class MyClass
//    {
//        @property IBOutlet (nonatomic, strong) UIButton *button;
//    }
//
//
//
//
//    Given the head node of a singly linked list, where each node contains a single random character, write an efficient program to remove all nodes with a numeric character.
//

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode removeNumeric(ListNode head) {   //'a' -> '8' -> 'b'         result:  'a' -> 'b'-> null

        if (head == null) return null;

        ListNode resultHead = null;
        ListNode resultEnd = null;
        ListNode currentNode = head;  //'a'

        while (currentNode != null) {
            ListNode next = currentNode.next; // keep track of the next;   // 'null'
            if (!isNumberic(currentNode)) { //'b'

                if (resultHead == null) { // handle the start of the list  //!= null
                    resultHead = resultEnd = currentNode;
                    resultEnd.next = null;

                }  else {
                    resultEnd.next = currentNode; //append to the end
                    resultEnd = currentNode; // update the end node to the new end    // resultEnd == 'b'
                    resultEnd.next = null;
                }
            }

            currentNode = next;   //null
        }

        return resultHead;   //'a'
    }


    public boolean isNumberic(ListNode node) {

        return node.val - '0' >= 0 && '9' - node.val >= 0;

    }

}
