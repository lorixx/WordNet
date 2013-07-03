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
        if (root == null) return;

        ArrayList<TreeNode> stack = new ArrayList<TreeNode>();

        TreeNode currentNode = root;

        while (currentNode != null || stack.size() > 0) {

            if (currentNode != null) {
                stack.add(currentNode);
                currentNode = currentNode.left;
            } else {
                TreeNode node = stack.remove(stack.size() - 1);
                System.out.println(node.value);
                currentNode = node.right;
            }
        }
    }

    public static void preOrderPrint(TreeNode root) {
        if (root == null) return;

        ArrayList<TreeNode> stack = new ArrayList<TreeNode>();

        TreeNode currentNode = root;

        while (currentNode != null || stack.size() > 0) {
            if (currentNode != null) {
                System.out.println(currentNode.value);

                if (currentNode.right != null) stack.add(currentNode.right);
                currentNode = currentNode.left;
            } else {
                TreeNode node = stack.remove(stack.size() - 1);
                System.out.println(node.value);

                if (node.left != null) currentNode = node.left;

            }

        }
    }

    public static void postOrderPrint(TreeNode root) {
        if (root == null) return;

        ArrayList<TreeNode> stack = new ArrayList<TreeNode>();

        TreeNode currentNode = root;

        while (currentNode != null || stack.size() > 0) {
            if (currentNode != null) {
                stack.add(currentNode);
                if (currentNode.right != null) stack.add(currentNode.right);
                // if (currentNode.left != null) stack.add(currentNode.left);
                currentNode = currentNode.left;

            } else {
                TreeNode node = stack.remove(stack.size() - 1);

                //if(node.left != null || node.right!= null) currentNode = node;
                System.out.println(node.value);
            }
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

        InorderPrintBST.inOrderPrint(d); // order list
        System.out.println("=========================");

        InorderPrintBST.inOrderPrint(InorderPrintBST.mirror(d)); // order list


    }
}
