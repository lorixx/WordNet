package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/3/13
 * Time: 12:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class Pow {
    /**
     *
     * need to be careful about
     * 1. base = 1, number is infinity case
     * 2. number = 0; number = 1
     * 3. number is negative
     * 4. use bit operation to increase performance
     *
     *
     * @param x
     * @param n
     * @return
     */
    public static double pow(double x, int n) {
        // Start typing your Java solution below
        // DO NOT write main() function

        if (n <= Integer.MIN_VALUE) {
            if (x - 1 < 0.000000001 || x + 1 < 0.00000000001) return 1;
            return 0;
        }
        if (n == 0) return 1;
        if (n == 1) return x;

        if (n < 0) return pow(1/x, -n); //handle negative n

        if (n % 2 == 0) {
            return pow(x * x, n >> 1);
        } else
            return x * pow(x * x, (n - 1) >> 1);

    }

    public static void main(String[] args) {
         System.out.println(pow(3.2, -3));
        System.out.println(Math.pow(3.2, -3));
    }
}
