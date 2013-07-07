package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/7/13
 * Time: 1:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class Sqrt {

    public int sqrt(int x) {
        // Start typing your Java solution below
        // DO NOT write main() function
        if (x < 0) return -1;

        return sqrt(0, x, x);

    }

    private int sqrt(int start, int end, int target) {
        if (end == start) {
            if (end * end == target || (end*end < target && (end + 1) * (end + 1) > target))
                return end;
            else
                return -1; // should never get here
        }

        int mid = start + (end - start) / 2;

        if (mid * mid == target || (mid*mid < target && (mid + 1) * (mid + 1) > target)) {
            return mid;
        } else if (mid * mid > target) {
            return sqrt(start, mid, target);
        } else
            return sqrt(mid + 1, end, target);

    }

    //todo: need to handle big integer case
}
