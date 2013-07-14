package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/14/13
 * Time: 3:16 PM
 * To change this template use File | Settings | File Templates.
 */


public class ValidateBST {

    private class TreeNode {
        TreeNode left;
        TreeNode right;
        int val;
    }

    public boolean isValidBST(TreeNode root) {
        // Start typing your Java solution below
        // DO NOT write main() function
        return isValidBST(root, Integer.MIN_VALUE , Integer.MAX_VALUE);

    }

    private boolean isValidBST(TreeNode root, int min, int max) {
        if (root == null) return true;

        if (root.val > min && root.val < max &&
                isValidBST(root.left, min, root.val) &&
                isValidBST(root.right, root.val, max))
            return true;
        else
            return false;

    }
}
