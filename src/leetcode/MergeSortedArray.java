package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/15/13
 * Time: 11:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class MergeSortedArray {

    /**
     * The tricky part here is merge from the end, so that we do not need to move the data
     */
    public void merge(int A[], int m, int B[], int n) {
        while (n > 0) {
            if (m <= 0 || A[m - 1] < B[n - 1]) // cover when m = 0 case
                A[n + m - 1] = B[--n];
            else
                A[n + m - 1] = A[--m];
        }
    }

    public static void main(String[] args) {
        int[] A = {1, 2, 3, 5, 6, 0, 0, 0, 0};
        int[] B = {0, 2, 3, 7};

        MergeSortedArray mergeSortedArray = new MergeSortedArray();
        mergeSortedArray.merge(A, 5, B, 4);
        for (int i : A) {
            System.out.println(i);
        }
    }


}
