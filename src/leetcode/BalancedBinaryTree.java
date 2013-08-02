package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 8/2/13
 * Time: 4:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class BalancedBinaryTree {
    private class TreeNode {
        TreeNode left;
        TreeNode right;
        int val;
        TreeNode(int x) { val = x; }
    }

    public boolean isBalanced(TreeNode root) {

        return checkHeight(root) != -1;
    }

    private int checkHeight(TreeNode root) {
        if (root == null)
            return 0;

        int leftHeight = checkHeight(root.left);
        if (leftHeight == -1)
            return -1; // not balance for the left tree

        int rightHeight = checkHeight(root.right);
        if (rightHeight == -1)
            return -1; // not balanced for the right tree

        int heightDiff = leftHeight - rightHeight;

        if (Math.abs(heightDiff) > 1) {
            return -1;
        } else
            return Math.max(leftHeight, rightHeight) + 1;

    }
}