package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/10/13
 * Time: 11:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchMatrix {

    public boolean searchMatrix(int[][] matrix, int target) {
        // Start typing your Java solution below
        // DO NOT write main() function

        if (matrix.length < 1) return false;
        int indexToSearch = findIndex(matrix, 0, matrix.length - 1, target);
        if (matrix[indexToSearch].length < 1) return false;
        return findTarget(matrix[indexToSearch], 0, matrix[indexToSearch].length - 1, target);
    }

    private int findIndex(int[][] matrix, int start, int end, int target) {
        if (start == end) return start;
        int mid = start + (end - start) / 2;

        if (target >= matrix[mid][0] && target <= matrix[mid][  matrix[mid].length - 1  ]) return mid;

        if (matrix[mid][0] > target)
            return findIndex(matrix, start, mid, target);
        else
            return findIndex(matrix, mid + 1, end, target);

    }

    private boolean findTarget(int[] array, int start, int end, int target) {
        if (start == end) {
            if (array[start] != target)
                return false;
        }
        int mid = start + (end - start) / 2;
        if (array[mid] == target) return true;
        else if (array[mid] > target)
            return findTarget(array, start, mid, target);
        else
            return findTarget(array, mid + 1, end, target);

    }

    public static void main(String[] args) {
        SearchMatrix searchMatrix = new SearchMatrix();
        int[][]matrix = {{}};

        System.out.println(searchMatrix.searchMatrix(matrix, 2));
    }



}
