package basic;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/6/13
 * Time: 12:31 AM
 * To change this template use File | Settings | File Templates.
 */

import practice.TreeNode;

public class BinarySearchTree {

    private TreeNode root;

    private int size;

    public BinarySearchTree() {
        root = null;
        size = 0;
    }

    public void add(int value) {
        // 1. empty case
        if (root == null)
            root = new TreeNode(value, null, null);

        else { // 2. DFS the tree to search until the end

            TreeNode newNode = new TreeNode(value, null, null);
            TreeNode currentNode = root;
            while (currentNode != null) {
                if (currentNode.value == value)  // duplicate, do nothing
                    return;

                if (currentNode.value < value) {  // go right and add to the right tree
                    if (currentNode.right == null) {
                        currentNode.right = newNode;
                        break;
                    }
                    currentNode = currentNode.right;
                } else {                          // go left and add it to the left tree if it is null
                    if (currentNode.left == null) {
                        currentNode.left = newNode;
                        break;
                    }
                    currentNode = currentNode.left;
                }
            }
        }

        size++;
    }

    public TreeNode get(int value) {

        TreeNode currentNode = root;
        while (currentNode != null) {
            if (currentNode.value == value)
                return currentNode;

            if (currentNode.value <  value)
                currentNode = currentNode.right;
            else
                currentNode = currentNode.left;
        }
        return currentNode;
    }

    /**
     * Totally cover all the cases
     * 0. not exist
     * 1. root case (just a special handle)
     * 2. leaf, no children, easy, just delete
     * 3. One children, move its child into the tree
     * 4. Two children, find the smallest element that is larger than the target node, replace its place
     *
     *
     * @param value
     */
    public void deleteNode(int value) {

        root = deleteNode(root, value);


    }

    private TreeNode deleteNode(TreeNode root, int value) {

        if (root == null) return null;

        if (root.value > value) root.left = deleteNode(root.left, value);   // important concept
        else if (root.value < value) root.right = deleteNode(root.right, value); // important concept
        else {   // Now the root is our target to delete
            size--;
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;

            TreeNode t = root;
            root = smallestBiggerNode(t.right); // pick the smallest from its right tree
            root.right = deleteMin(t.right);    // delete the smallest from its right tree
            root.left = t.left;
        }
        return root; // important, since we use recursive way to modify the tree structure, it is so beautiful
    }

    private TreeNode deleteMin(TreeNode root) {
        if (root.left == null) return root.right;
        root.left = deleteMin(root.left);
        return root;
    }


    private TreeNode smallestBiggerNode(TreeNode root) {

        if (root.left == null) return root;
        return smallestBiggerNode(root.left); // keep finding for the smallest element in the tree

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void inOrderPrint() {
         inOrderPrint(root);
        System.out.println("\n=====");
    }

    private void inOrderPrint(TreeNode root) {
        if (root == null) return;

        inOrderPrint(root.left);
        System.out.print(root.value + " ");
        inOrderPrint(root.right);
    }

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        bst.add(5);
        bst.add(1);
        bst.add(12);
        bst.add(8);
        bst.add(19);
        bst.inOrderPrint();

        bst.deleteNode(8);
        bst.inOrderPrint();

        bst.deleteNode(5);
        bst.inOrderPrint();
        System.out.println("Size of tree is " + bst.size());
    }

}
