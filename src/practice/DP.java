package practice;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: zhuang
 * Date: 5/3/13
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class DP {
    /**
     * Given a sequence of N numbers - A[1] , A[2] , ..., A[N] . Find the length of the longest non-decreasing sequence.
     *
     * @param array
     * @return
     */
    public static Stack<Integer> findLongestAscend(int[] array) {

        if (array == null) throw new IllegalArgumentException();

        Stack<Integer> result = new Stack<Integer>();

        int[] dp = new int[array.length];
        int[] prev = new int[array.length]; // used to keep track of the step we have so far

        for (int i = 0; i < array.length; i++) {
            dp[i] = 1;
            prev[i] = -1; // -1 means it is the end
        }

        /*
         * Use Dynamic Programming concept to solve this problem
         *
         * 1. The outer loop starts from 1 since we already solve the base case
         * 2. int i = 0; i < size, because we need to check from 0 until size - 1 items that needed to be calculate before
         * 3. The if condition means not only the number need to be in order, but also dp[i] + 1 > dp[size], then we update the dp[size]
         *    as well as prev array
         */
        for (int size = 1; size < array.length; size++) {
            for (int i = 0; i < size; i++) {
                if (array[i] < array[size] && dp[i] + 1 > dp[size]) {
                    dp[size] = dp[i] + 1;
                    prev[size] = i;
                }
            }
        }

        System.out.println(dp[array.length - 1]);

        int currentIndex = array.length - 1;
        while (prev[currentIndex] != -1) {
            result.push(currentIndex);
            currentIndex = prev[currentIndex];
        }

        result.push(currentIndex); //add the last one
        return result;
    }

    public static  void main(String[] args) {
        //int[] array = {5, 7, 8, 1, 2, 9, 4,  10, 11, 14, 16};
        int[] array = {5, 3, 4, 8, 6, 7};
        Stack<Integer> result = DP.findLongestAscend(array);

        while (!result.empty()) {
            System.out.print(result.pop() + "  ");
        }
    }
}
