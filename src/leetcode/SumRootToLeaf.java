package leetcode;

import practice.TreeNode;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/7/13
 * Time: 6:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class SumRootToLeaf {

    public int sumNumbers(TreeNode root) {

        return sum(root, 0);

    }

    private int sum(TreeNode currentNode, int resultSoFar) {
        int totalResult = 0;
        if (currentNode != null) {
            resultSoFar = resultSoFar * 10 + currentNode.value;
            if (currentNode.left == null && currentNode.right == null)
                totalResult += resultSoFar;
            return totalResult + sum(currentNode.left, resultSoFar) + sum(currentNode.right, resultSoFar);
        }
        return totalResult;
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

        SumRootToLeaf sumRootToLeaf = new SumRootToLeaf();
        System.out.println(sumRootToLeaf.sumNumbers(d));
    }

}
