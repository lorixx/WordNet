package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/13/13
 * Time: 12:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClimbStair {
    /**
     * You are climbing a stair case. It takes n steps to reach to the top.

     Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        // Start typing your Java solution below
        // DO NOT write main() function

        int[] memo = new int[n + 1]; // for fast access

        return topDownDP(n, memo);

    }

    private int topDownDP(int n, int[] memo) {
        if (memo[n] != 0)
            return memo[n]; // fast access

        /* Start of Base cases */
        if (n == 0) {
            memo[n] = 0;
            return 0;
        }
        if (n == 1) {
            memo[n] = 1;
            return 1;
        }

        if (n == 2) {
            memo[n] = 2;
            return 2;
        }
        /* End of Base cases */

        memo[n] = topDownDP(n - 1, memo) + topDownDP(n - 2, memo);
        return memo[n];

    }

    public static void main(String[] args) {
        ClimbStair climbStair = new ClimbStair();
        System.out.println(climbStair.climbStairs(39));
    }
}
