package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: zhuang
 * Date: 8/1/13
 * Time: 10:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class SearchInsertPosition {

/*    Given a sorted array and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.

    You may assume no duplicates in the array.

    Here are few examples.
            [1,3,5,6], 5 → 2
            [1,3,5,6], 2 → 1
            [1,3,5,6], 7 → 4
            [1,3,5,6], 0 → 0*/

    public int searchInsert(int[] A, int target) {
        // Start typing your Java solution below
        // DO NOT write main() function

        int start = -1;
        int end = A.length;

        while (end - start > 1) {
            int mid = start + (end - start) / 2;

            if (A[mid] >= target)
                end = mid;
            else
                start = mid;

        }
        return end;

    }
}
