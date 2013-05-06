package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: zhuang
 * Date: 5/6/13
 * Time: 11:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class MedianOfTwoArrays {
    /*
     * There are two sorted arrays A and B of size m and n respectively.
     * Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
     *
     * http://fisherlei.blogspot.com/2012/12/leetcode-median-of-two-sorted-arrays.html
     *
     */

    public double findMedianSortedArrays(int A[], int B[]) {
        // Start typing your Java solution below
        // DO NOT write main() function

        // handle A, B length <= 2 situation

        if (A.length == 1 && B.length == 1) {
            return (A[0] + B[0]) / 2;
        }
        if (A.length + B.length == 4) {
            return getMedian(A, B);
        }


        int len1 = A.length;
        int len2 = B.length;

        int startA = 0, endA = len1 - 1;
        int startB = 0, endB = len2 - 1;

        int[] finalA = new int[2]; finalA[0] = A[0]; finalA[1] = A[1];
        int[] finalB = new int[2]; finalB[0] = B[0]; finalB[1] = B[1];

        int m1 = 0, m2 = 0;
        while (endA - startA > 1 || endB - startB > 1) {

            if (endA - startA > 1) {
               m1 = startA + (endA - startA) / 2;
            }
            if (endB - startB > 1) {
                m2 = startB + (endB - startB) / 2;
            }

            if (A[m1] == B[m2]) return A[m1];
            else if (A[m1] < B[m2]) {
                if (endA - startA > 1)
                    startA = m1 + 1;
                if (endB - startB > 1)
                    endB = m2;
            } else {
                if (endA - startA > 1)
                    startA = m1 + 1;
                if (endB - startB > 1)
                    endB = m2;
            }
        }

        finalA[0] = A[startA]; finalA[1] = A[endA];
        finalB[0] = B[startB]; finalB[1] = B[endB];

        return getMedian(finalA, finalB);




    }

    private double getMedian(int[] a, int[] b) {
       return (double)(Math.max(a[0], b[0]) + Math.min(a[1], b[1])) / 2;
    }

    public static void main(String[] args) {
        MedianOfTwoArrays medianOfTwoArrays = new MedianOfTwoArrays();

        int[] a = {1, 2, 3};
        int[] b = {5, 5, 5, 5};
        System.out.println(medianOfTwoArrays.findMedianSortedArrays(a, b));
    }


}
