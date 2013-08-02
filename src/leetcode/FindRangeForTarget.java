package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/31/13
 * Time: 11:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class FindRangeForTarget {


    // 1. find the smaller one next to the target as the start index
    // 2. find the bigger one next to the target as the end index, (need to be careful here, since it might overflow)
    public int[] searchRange(int[] A, int target) {
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
        int[] array = {1, 4};
        int[] result = findRangeForTarget.searchRange(array, 4);
        for (int i : result) System.out.println(i);
    }

}
