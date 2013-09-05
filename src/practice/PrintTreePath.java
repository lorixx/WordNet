package practice;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 8/28/13
 * Time: 12:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class PrintTreePath {


    public void printTreePath(TreeNode root) {
        LinkedList<LinkedList<TreeNode>> all = findAllPath(root);
        for (LinkedList<TreeNode> list : all) {
            for (TreeNode node : list) {
                System.out.print(node.value + " ");
            }
            System.out.println();
        }
    }

    private LinkedList<LinkedList<TreeNode>> findAllPath(TreeNode root) {
        LinkedList<LinkedList<TreeNode>> result = new LinkedList<LinkedList<TreeNode>>();

        if (root.left == null && root.right == null) {  /* Base case, print the leaf node out  */
            LinkedList<TreeNode> list = new LinkedList<TreeNode>();
            list.add(root);
            result.add(list);
            return result;
        }

        if (root.left != null) {  /* If there is left tree, then we add all path for the left tree */
            LinkedList<LinkedList<TreeNode>> lefts = findAllPath(root.left);
            for (LinkedList<TreeNode> currentList : lefts) {
                currentList.addFirst(root);
            }
            result.addAll(lefts);
        }

        if (root.right != null) { /* If there is right tree, then we add all path for the right tree */
            LinkedList<LinkedList<TreeNode>> rights = findAllPath(root.right);
            for (LinkedList<TreeNode> currentList : rights) {
                currentList.addFirst(root);
            }
            result.addAll(rights);
        }
        return result;
    }

    /**
     * This is a better version of DFS for printing the tree path
     *
     * @param root
     */
    public void printPathBetter(TreeNode root) {
         printPathBetter(root, new LinkedList<TreeNode>());
    }

    private void printPathBetter(TreeNode root, LinkedList<TreeNode> path) {
        if (root != null) {
            path.addLast(root);
            if (root.left != null) printPathBetter(root.left, path);
            if (root.right != null) printPathBetter(root.right, path);

            if (root.left == null && root.right == null) {
                for (TreeNode node : path)
                    System.out.print(node.value + " ");
                System.out.println();
            }

            path.removeLast(); // very important
        }
    }

    public static void main(String[] args) {

        PrintTreePath printTreePath = new PrintTreePath();
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

        printTreePath.printTreePath(d);
        //printTreePath.printPathBetter(d);



    }

}
