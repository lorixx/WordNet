package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 6/30/13
 * Time: 11:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReverseInteger {

    public static int reverse(int x) {
        // Start typing your Java solution below
        // DO NOT write main() function
        int value = x;
        if (x < 0) value = -x; // turn to positive value

        // first calculate how many digits we need
        int digit = 0;
        int tempValue = value;
        while (tempValue != 0) {
            tempValue /= 10;
            digit++;
        }

        // second create a digits array
        int[] digits = new int[digit];
        int i = 0;
        tempValue = value;
        while (tempValue != 0) {
            digits[i++] = tempValue % 10; // store its last bit into the array
            tempValue /= 10;
        }

        // third generate the final result from the digit array
        int result = 0;
        for (int j = 0; j < digits.length; j++) {
            result += digits[j] * Math.pow(10, digits.length - j - 1);
        }

        if (x < 0) result = -result;

        return result;
    }

    public static void main(String[] args) {
          System.out.println(ReverseInteger.reverse(-90));
    }

}
