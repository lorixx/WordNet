package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/3/13
 * Time: 2:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class SearchInRotateArray {

    public int search(int[] A, int target) {
        // Start typing your Java solution below
        // DO NOT write main() function
        return search(A, 0, A.length - 1, target);
    }

    private int search(int[] A, int start, int end, int target) {

        if (start == end) {
            return A[start] == target ? start : -1;
        }

        if (target == A[start]) return start;
        if (target == A[end]) return end;

        boolean isInBig = target > A[start];
        boolean isInSmall = target < A[end];


        int newRight = start + (end - start) / 2;

        int newLeft = newRight + 1;

        if (A[newRight] > A[newLeft]) {

            if (isInBig) {
                int searchFirst = binarySearch(A, start, newRight, target);
                if (searchFirst != -1)
                    return searchFirst;

                int searchSecond = search(A, newLeft, end, target);
                if (searchSecond != -1)
                    return searchSecond;
            } else if (isInSmall){
                return search(A, newLeft, end, target);
            }
        } else {
            if (isInBig) {
                return search(A, start, newRight, target);
            } else if (isInSmall) {
                int searchFirst = search(A, start, newRight, target);
                if (searchFirst != -1)
                    return searchFirst;

                int searchSecond = binarySearch(A, newLeft, end, target);
                if (searchSecond != -1)
                    return searchSecond;
            }
        }
        return -1;
    }

    public int binarySearch(int[] A, int start, int end, int target) {

        if (start == end) {
            if (A[start] == target) return start;
            return -1;
        }

        int middle = start + (end - start) / 2;
        if (A[middle] == target)
            return middle;
        if (target > A[middle]) {
            return binarySearch(A, middle + 1, end, target);
        } else
            return binarySearch(A, start, middle, target);
    }

    public static void main(String[] args) {
         SearchInRotateArray searchInRotateArray = new SearchInRotateArray();

        int[] array = {1, 3, 5, 8, 9, 12, 15};
        System.out.println(searchInRotateArray.binarySearch(array, 0, array.length - 1, 8));

        int[] array2 = {8, 9, 12, 15, 1, 3, 5};
        int[] array3 = {4,5,6,7,8,1,2,3};
        //for (int i : array3 )
            System.out.println(searchInRotateArray.search(array3, 8));


    }

}
