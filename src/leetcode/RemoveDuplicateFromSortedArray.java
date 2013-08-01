package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/31/13
 * Time: 10:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class RemoveDuplicateFromSortedArray {

    public int removeDuplicates(int[] A) {
        // Start typing your Java solution below
        // DO NOT write main() function
        if (A.length < 2) return A.length;
        int count = 1;
        int prev = A[0];

        for (int i = 1; i < A.length; i++) {

            if (A[i] != prev) {
                count++;
                prev = A[i];
            }
        }

        // now we have total count of duplicate
        int current = 1;
        prev = A[0];
        for (int i = 1; i < A.length; i++) {



            if (A[i] != prev) {
                A[current++] = A[i];
                prev = A[i];
            }

        }
        return count;

    }
}
