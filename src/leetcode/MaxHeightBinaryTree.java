package leetcode;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 6/6/13
 * Time: 10:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class MaxHeightBinaryTree {

    public int maxDepth(TreeNode root) {
        // Start typing your Java solution below
        // DO NOT write main() function
        if (root == null) return 0;

        if ( ! (root instanceof TreeNode) )return 0;

        LinkedList<TreeNode> q = new LinkedList<TreeNode>();

        q.add(root);
        int level = 0, qSize;
        while (q.size() != 0) {
            qSize = q.size();
            for (int i = 0; i < qSize; i++) {
                TreeNode currentNode = q.remove();
                if (currentNode.left != null) q.add(currentNode.left);
                if (currentNode.right != null) q.add(currentNode.right);
            }
            level++;
        }
        return level;

    }

    private class TreeNode {
        TreeNode left;
        TreeNode right;
        int val;
    }
}
