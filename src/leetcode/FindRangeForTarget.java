package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/31/13
 * Time: 11:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class FindRangeForTarget {

    //[1, 2, 3, 3, 3, 5, 6, 8], find the range for 3, should return [2, 4]
    public int[] searchRange(int[] A, int target) {
        int startIndex = findStartIndex(A, target);
        if (startIndex == -1) {  // if the start index is not found, there is no need to continue
            int[] result = {-1, -1};
            return result;
        }

        int endIndex = findEndIndex(A, target);
        int[] result = {startIndex, endIndex};
        return result;
    }

    // starting from the -1 to A.length - 1 position since we are finding the start index
    public int findStartIndex(int[] A, int target) {
        int start = -1;
        int end = A.length - 1;

        while (end - start > 1) {
            int mid = start + (end - start) / 2;
            if (A[mid] >= target) end = mid;  // if A[mid] == target, we still go left since we look for the start index
            else
                start = mid;
        }


        return A[end] == target ? end : -1;
    }

    // starting from 0 to A.length, we use A.length since we make sure A[start] will never overflow
    public int findEndIndex(int[] A, int target) {
        int start = 0;
        int end = A.length;
        while (end - start > 1) {
            int mid = start + (end - start) / 2;
            if (A[mid] > target) end = mid;
            else start = mid;          // now when A[mid] == target we go right since we look for the end index
        }

        return A[start] == target ? start : -1;
    }


    public int myFindStartIndex(int[] A, int target) {
        int start = 0;
        int end = A.length - 1;

        while (end > start) {
            int mid = start + (end - start) / 2;
            if (A[mid] < target)
                start = mid + 1;
            else if (A[mid] > target)
                end = mid - 1;
            else
                end = mid - 1;
        }

        return A[start] == target ? start : -1;

    }

    public int myFindEndIndex(int[] A, int target) {
        int start = 0;
        int end = A.length - 1;
        int mid;

        while (end > start) {
            mid = start + (end - start) / 2;
            if (A[mid] > target)
                end = mid - 1;
            else if (A[mid] < target)
                start = mid + 1;
            else
                end = mid - 1;
        }

        //bug

        return A[start] == target ? start : -1;
    }


    public int[] searchRangeOldMethod(int[] A, int target) {
        // Start typing your Java solution below
        // DO NOT write main() function
        if (A.length < 1) {
            int[] result = {-1, -1};
            return result;
        }
        int finalStart = -1;
        int finalEnd = -1;
        int start = 0;
        int end = A.length - 1;
        int mid;

        while (start <= end) {
            mid = start + (end - start) / 2;
            if (A[mid] != target) {
                if (mid == A.length - 1) { // it is the end and not found
                    int[] result = {-1, -1};
                    return result;
                } else {
                    if (A[mid + 1] == target) {
                        finalStart = mid + 1;
                        break;
                    }
                }

                if (A[mid] < target) start = mid + 1;
                else end = mid - 1;


            } else {  //A[mid] == target
                if (mid == 0) {
                    finalStart = 0;   // found it
                    break;
                }

                end = mid - 1;

            }
        }

        start = 0;
        end = A.length - 1;
        while (start <= end) {

            if (A[end] == target) {   // IMPORTANT! need this check here since end might be found here
                finalEnd = end;
                break;
            }

            mid = start + (end - start) / 2;
            if (A[mid] != target) {
                if (mid == 0) { // it is the start and not found
                    int[] result = {-1, -1};
                    return result;
                } else {
                    if (A[mid - 1] == target) {
                        finalEnd = mid - 1;
                        break;
                    }
                }

                if (A[mid] < target) start = mid + 1;
                else end = mid - 1;


            } else {  //A[mid] == target
                if (mid == A.length - 1) {
                    finalEnd = A.length - 1;   // found it
                    break;
                }

                start = mid + 1; // go to the bigger part

            }
        }

        int[] result = {finalStart, finalEnd};
        return result;



    }


    public static void main(String[] args) {
        FindRangeForTarget findRangeForTarget = new FindRangeForTarget();

        int[] testStartArray = {1};
        System.out.println(findRangeForTarget.findEndIndex(testStartArray, 1));



        int[] array = {1, 4};
        int[] result = findRangeForTarget.searchRange(array, 4);
        for (int i : result) System.out.println(i);


        int[] newArray = {1, 3, 3, 3, 4};
        System.out.println(findRangeForTarget.myFindStartIndex(newArray, 3));
        System.out.println(findRangeForTarget.myFindEndIndex(newArray, 3));
    }

}
