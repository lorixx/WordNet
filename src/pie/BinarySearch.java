package pie;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 5/5/13
 * Time: 2:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class BinarySearch {

    public static int binarySearch(int[] a, int v) {
        //return recursiveBS(a, 0, a.length - 1, v);
        return iterativeBS(a, v);
    }
    /*
     * Recursive call for Binary Search
     */
    private static int recursiveBS(int[] a, int start, int end, int v) {
        if (start == end) {
            if (a[start] == v) return start;
            else return -1;
        }

        int mid = start + (end - start) / 2;

        if (a[mid] == v) return mid;
        else if (a[mid] > v) return recursiveBS(a, start, mid - 1, v);
        else return recursiveBS(a, mid + 1, end, v);
    }


    /*
     * Iterative implementation
     */
    private static int iterativeBS(int[] a, int v) {
        int start = 0, end = a.length - 1, mid;

        while (end >= start) {

            if (end == start) {
                if (a[start] == v) return start;
                else return -1;
            }

            mid = start + (end - start) / 2;
            if (a[mid] == v) return mid;
            else if (a[mid] > v) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }

        return -1;

    }

    public static void main(String[] args) {
        int a[] = {1,3,4,5,7,8,9,10};

        System.out.println(BinarySearch.binarySearch(a, 11));
        System.out.println(BinarySearch.binarySearch(a, 1));
        System.out.println(BinarySearch.binarySearch(a, 10));
        System.out.println(BinarySearch.binarySearch(a, 5));


    }
}
