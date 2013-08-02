package leetcode;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 8/2/13
 * Time: 5:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class MinDepthOfTree {
    private class TreeNode {
        TreeNode left;
        TreeNode right;
        int val;
        TreeNode(int x) { val = x; }
    }
    public int minDepth(TreeNode root) {
        // Start typing your Java solution below
        // DO NOT write main() function
        if (root == null) return 0;

        int currentDepth = 0;
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();

        queue.add(root);

        while (!queue.isEmpty()) {

            int qSize = queue.size();
            for (int i = 0; i < qSize; i++) {
                TreeNode node = queue.remove();
                if (node.left == null && node.right == null)
                    return currentDepth + 1;
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);

            }
            currentDepth++;

        }

        return currentDepth + 1;

    }
}
