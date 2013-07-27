package interview;

import practice.TreeNode;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/25/13
 * Time: 7:54 PM
 * To change this template use File | Settings | File Templates.
 */
 import practice.TreeNode;


public class FindCommonAncestor {




    public int findCommonAncestor(TreeNode root, int a, int b) {

        if (root == null) return -1;

        while (root != null) {

            if (root.left.value == b  || root.left.value == a)  return root.value;  // when a is in b's path
            if (root.right.value == b || root.right.value == a) return root.value; // when a is in b's path

            if (root.value > a && root.value < b)
                return root.value; // found the common ancestor

            else if (root.value < a)
                root = root.right; // go right
            else
                root = root.left; // go left
        }

        return -1;
    }

    public static void main(String[] args) {
        
        practice.TreeNode a = new practice.TreeNode(1, null, null);
        practice.TreeNode b = new practice.TreeNode(3, null, null);
        practice.TreeNode c = new practice.TreeNode(4, null, null);
        practice.TreeNode d = new practice.TreeNode(5, null, null);
        practice.TreeNode e = new practice.TreeNode(6, null, null);
        practice.TreeNode f = new practice.TreeNode(7, null, null);
        practice.TreeNode g = new practice.TreeNode(8, null, null);

        b.left = a;
        b.right = c;
        f.left = e;
        f.right = g;
        d.left = b;
        d.right = f;
        
        FindCommonAncestor findCommonAncestor = new FindCommonAncestor();
        
        System.out.println(findCommonAncestor.findCommonAncestor(d, 1, 6));




    }
}
