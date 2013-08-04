package basic;


public class BinarySearch {

    /**
     * Binary Search:
     * be careful that the mid index is calculated by start + (end - start) / 2
     * And then the half is (mid + 1, end)
     * The other half is (start, mid)
     *
     * Otherwise it's not correct
     */
    public int binarySearch(int[] A, int target) {

        return binarySearch(A, 0, A.length - 1, target);
    }

    private int binarySearch(int[] A, int start, int end, int target) {
        if (start == end) {
            return A[start] == target ? start : -1;
        }

        int currentIndex = start + (end - start) / 2;
        if (A[currentIndex] == target) return currentIndex;
        if (A[currentIndex] < target)  // go right
            return binarySearch(A, currentIndex + 1, end, target);
        else                           // go left
            return binarySearch(A, start, currentIndex, target);    // binarySearch ending should be corresponding to currentIndex
    }

    private int binarySearchIterative(int[] A, int target) {
        int start = 0;
        int end = A.length - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (A[mid] == target)
                return mid;
            else if (A[mid] < target)
                start = mid + 1;
            else
                end = mid - 1;
        }
        return -1;
    }


    /**
     * Find out the min value in the rotate array, return the index for it. -1 if not found.
     *
     * base case is when the length is less than 3 elements and find the smallest one
     * there are only 3 cases:
     * 1. either the mid both less than the start and the end
     * 2. either the mid both larger than the start and the end
     * 3. if mid  < start and mid < end, then it is the smallest element we are looking for 
     *
     */
    public int findMinInRotate(int[] A) {
        return findMinInRotate(A, 0, A.length - 1);
    }

    private int findMinInRotate(int[]A, int start, int end) {
        if (end - start < 2) {
            if (start == end)
                return start;
            else
                return A[start] > A[end] ? end : start; //return the smaller one
        }

        int midIndex = start + (end - start) / 2;
        if (A[midIndex] < A[start] && A[midIndex] < A[end])
            return midIndex;
        if (A[midIndex] > A[start] && A[midIndex] > A[end])
            return findMinInRotate(A, midIndex + 1, end);
        else
            return findMinInRotate(A, start, midIndex);
    }

    /**
     * Find the array for the target value,
     * there are two cases:
     * 1. either mid larger than both ends
     * 2. either mid smaller than both ends
     *
     * Depends on the value of the target compare to the start, mid, and end, we need to find index
     * in the correct range.
     */
    public int findIndexInRotateArray(int[] A, int target) {
        return findIndexInRotateArray(A, 0, A.length - 1, target);
    }

    private int findIndexInRotateArray(int[] A, int start, int end, int target) {
        if (start == end)
            return A[start] == target ? start : -1;

        int mid = start + (end - start) / 2;

        if (A[mid] == target) return mid;

        if (A[mid] > A[start] && A[mid] > A[end]) {
            if (target == A[start])
                return start;
            if (target > A[start] && target < A[mid])
                return findIndexInRotateArray(A, start, mid, target); // go LEFT
            if (target > A[mid] || target < A[start])
                return findIndexInRotateArray(A, mid + 1, end, target); // go RIGHT
        } else {
            if (target == A[end])
                return end;
            if (target > A[mid] && target < A[end])
                return findIndexInRotateArray(A, mid + 1, end, target); // go RIGHT
            if (target < A[mid] || target > A[end])
                return findIndexInRotateArray(A, start, mid, target); // go LEFT
        }

        return -1;
    }


    public static void main(String[] args) {
        BinarySearch binarySearch = new BinarySearch();
        int[] oneElement = {5};
        System.out.println(binarySearch.binarySearch(oneElement, 0));
        System.out.println(binarySearch.binarySearch(oneElement, 5));

        System.out.println("===========");

        int[] twoElements = {3, 9};
        System.out.println(binarySearch.binarySearch(twoElements, 0));
        System.out.println(binarySearch.binarySearch(twoElements, 3));
        System.out.println(binarySearch.binarySearch(twoElements, 9));


        System.out.println("===========");

        int[] threeElements = {3, 9, 11};
        System.out.println(binarySearch.binarySearch(threeElements, 0));
        System.out.println(binarySearch.binarySearch(threeElements, 3));
        System.out.println(binarySearch.binarySearch(threeElements, 9));
        System.out.println(binarySearch.binarySearch(threeElements, 11));

        System.out.println("===========");

        int[] fourElements = {3, 9, 11, 13};
        System.out.println(binarySearch.binarySearch(fourElements, 0));
        System.out.println(binarySearch.binarySearch(fourElements, 3));
        System.out.println(binarySearch.binarySearch(fourElements, 9));
        System.out.println(binarySearch.binarySearch(fourElements, 11));
        System.out.println(binarySearch.binarySearch(fourElements, 13));

        System.out.println("===========");

        int[] rotate = {5, 1, 2, 3};
        System.out.println(binarySearch.findIndexInRotateArray(rotate, 5));
        System.out.println(binarySearch.findIndexInRotateArray(rotate, 1));
        System.out.println(binarySearch.findIndexInRotateArray(rotate, 2));
        System.out.println(binarySearch.findIndexInRotateArray(rotate, 3));
        System.out.println(binarySearch.findIndexInRotateArray(rotate, 4));


        System.out.println("===========");

    }

}
