package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/13/13
 * Time: 12:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClimbStair {

    public int climbStairs(int n) {
        // Start typing your Java solution below
        // DO NOT write main() function

        int[] memo = new int[n + 1]; // for fast access

        return topDownDP(n, memo);

    }

    private int topDownDP(int n, int[] memo) {
        if (memo[n] != 0)
            return memo[n];

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

        memo[n] = topDownDP(n - 1, memo) + topDownDP(n - 2, memo);
        return memo[n];

    }

    public static void main(String[] args) {
        ClimbStair climbStair = new ClimbStair();
        System.out.println(climbStair.climbStairs(39));
    }
}
