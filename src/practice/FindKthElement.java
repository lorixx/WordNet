package practice;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: zhuang
 * Date: 7/9/13
 * Time: 5:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class FindKthElement {

    public FindKthElement() {
        randomEngine = new Random();
    }

    private Random randomEngine;

    /**
     * Find the kth element in the array
     *
     * Assume that we start the index from 1, first index would be 1, second would be 2, etc.
     *
     * @param A
     * @param k
     * @return
     */
    public int find(int[] A, int k) {
        if (k > A.length || k <= 0)
            throw new IllegalArgumentException();

        return find(A, 0, A.length - 1, k);
    }

    private int find(int[]A, int start, int end, int k) {

        if (start == end) return A[start]; //base case!!! Be careful when there is ONLY one element

        int index = randomPartition(A, start, end, k);

        if (index == k - 1) return A[index];
        else if (index < k - 1)
            return find(A, index +1, end, k);    // go right
        else
            return find(A, start, index - 1, k); // go left
    }

    /**
     * Randomized pick the index and partition
     */
    private int randomPartition(int[]A, int start, int end, int k) {
        int randomIndex = start + randomEngine.nextInt(end - start + 1);
        swap(A, start, randomIndex);

        int l = start;
        int r = end + 1; // start from the end + 1 since we start from --r to cover all cases
        int pivot = A[start]; //since we swap with the random one, the start is the pivot now

        while (true) {
            while (A[++l] < pivot) if (l == end) break; // break when l reach to the end
            while (A[--r] > pivot) if (r == start) break;  // break when r reach to the start
            if (l >= r) break;
            swap(A, l, r);
        }
        swap(A, start, r);
        return r;
    }

    private void swap(int[]A, int left, int right) {
        int temp = A[right];
        A[right] = A[left];
        A[left] = temp;
    }

    public static void main(String[] args) {
        FindKthElement findKthElement = new FindKthElement();
        int[] A = {4};

        int value = findKthElement.find(A, 1);

        System.out.println(value);
    }


}
