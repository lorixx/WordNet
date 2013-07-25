package practice;

import java.util.ArrayList;

public class InorderPrintBST {

    /**
     * The idea is to use a stack for in order print
     * First set current node to root
     * start checking whether the current node is null or the stack is empty
     * 1. if currentNode is not null, we need to get its left node and set it as the current node and continue
     * 2. else we pop a node from the stack, then print this value, set the current node as its right node if there is any.
     * <p/>
     * Eventually this will end
     *
     * @param root
     */
    public static void inOrderPrint(TreeNode root) {
        ArrayList<TreeNode> stack = new ArrayList<TreeNode>();
        TreeNode currentNode = root;
        while (currentNode != null || !stack.isEmpty()) {
            if (currentNode != null) {
                stack.add(currentNode);   // keep adding current node
                currentNode = currentNode.left;
            } else {
                currentNode = stack.remove(stack.size() - 1);
                System.out.println(currentNode.value);
                currentNode = currentNode.right;   // now update to its right node
            }
        }

    }

    public static void preOrderPrint(TreeNode root) {
        ArrayList<TreeNode> stack = new ArrayList<TreeNode>();
        TreeNode currentNode = root;
        while (currentNode != null || !stack.isEmpty()) {
            if (currentNode != null) {
                System.out.println(currentNode.value);
                stack.add(currentNode);   // keep adding current node
                currentNode = currentNode.left;
            } else {
                currentNode = stack.remove(stack.size() - 1);
                currentNode = currentNode.right;   // now update to its right node
            }
        }
    }


    /**
     * Algorithm:
     * We use a prev variable to keep track of the previously-traversed node.
     * Let’s assume curr is the current node that’s on top of the stack.

     (1)
     When prev is curr‘s parent, we are traversing down the tree.
     In this case, we try to traverse to curr‘s left child if available (ie, push left child to the stack).
     If it is not available, we look at curr‘s right child.
     If both left and right child do not exist (ie, curr is a leaf node),
     we print curr‘s value and pop it off the stack.

     (2)
     If prev is curr‘s left child, we are traversing up the tree from the left.
     We look at curr‘s right child. If it is available, then traverse down the right child
     (ie, push right child to the stack), otherwise print curr‘s value and pop it off the stack.

     (3)
     If prev is curr‘s right child, we are traversing up the tree from the right.
     In this case, we print curr‘s value and pop it off the stack.

     * @param root
     */
    public static void postOrderPrint(TreeNode root) {
        if (root == null) return;

        ArrayList<TreeNode> stack = new ArrayList<TreeNode>();
        TreeNode currentNode = root;
        stack.add(currentNode);

        TreeNode prevNode = null;

        while (stack.size() > 0) {
            currentNode = stack.get(stack.size() - 1); // peek the last element
            if (prevNode == null || prevNode.left == currentNode || prevNode.right == currentNode) { //going down of the tree

                if (currentNode.left != null)
                    stack.add(currentNode.left);
                else if (currentNode.right != null)
                    stack.add(currentNode.right);

            } else if (currentNode.left == prevNode) {  // going up of the tree
                if (currentNode.right != null)
                    stack.add(currentNode.right);
            } else {  // should print in all these cases
                System.out.println(currentNode.value);
                stack.remove(stack.size() - 1);
            }
            prevNode = currentNode;
        }
    }

    public static TreeNode mirror(TreeNode root) {

        if (root == null) return root;
        TreeNode currentNode = root;

        TreeNode temp = currentNode.left;
        currentNode.left = mirror(currentNode.right);
        currentNode.right = mirror(temp);
        return currentNode;
    }

    public static void main(String[] args) {


             /*
                       5
                     /   \
                    3     7
                   /\    / \
                  1  4  6   8
              */
        TreeNode a = new TreeNode(1, null, null);
        TreeNode b = new TreeNode(3, null, null);
        TreeNode c = new TreeNode(4, null, null);
        TreeNode d = new TreeNode(5, null, null);
        TreeNode e = new TreeNode(6, null, null);
        TreeNode f = new TreeNode(7, null, null);
        TreeNode g = new TreeNode(8, null, null);

        b.left = a;
        b.right = c;
        f.left = e;
        f.right = g;
        d.left = b;
        d.right = f;

        InorderPrintBST.postOrderPrint(d); // order list
        System.out.println("=========================");

        //InorderPrintBST.inOrderPrint(InorderPrintBST.mirror(d)); // order list


    }
}
