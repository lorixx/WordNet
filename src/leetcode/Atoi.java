package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/2/13
 * Time: 12:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class Atoi {
    public int atoi(String str) {
        // Start typing your Java solution below
        // DO NOT write main() function

        if (str.equals(""))
            return 0;

        boolean sign = false;
        int result = 0;
        boolean start = false;

        int startIndex = -1, length = 0;

        for (int i = 0; i < str.length(); i++) {

            if (str.charAt(i) == ' ' && !start) continue;  //skip the first white space character ??

            if (str.charAt(i) == '+' || str.charAt(i) == '-') {

                if (!start) {
                    start = true;
                    if (str.charAt(i) == '-') sign = true;
                    continue;
                } else
                    break;
            }

            if (str.charAt(i) - '0' < 0 || str.charAt(i) - '0' > 9) break;

            start = true;
            if (startIndex == -1) startIndex = i;

            length++; // update the length
        }

        for (int i = 0; i < length; i++) {
            result += (str.charAt(startIndex + i) - '0') * Math.pow(10, length - i - 1);
        }


        if (result >= Integer.MAX_VALUE) {

            if (result == Integer.MAX_VALUE && sign) return -Integer.MAX_VALUE;

            if (sign) return Integer.MIN_VALUE;
            return Integer.MAX_VALUE; //overflow
        }

        if (sign) result = -result;

        return result;






//        boolean lessThanZero = str.charAt(0) == '-';
//        String s = str.charAt(0) == '-' ? str.substring(1, str.length()) : str;
//
//        if (!isValid(s))
//            return 0;
//        int result = 0;
//        for (int i = 0; i < s.length(); i++) {
//            result += (s.charAt(i) - '0') * Math.pow(10, s.length() - 1 - i);
//        }
//
//        if (lessThanZero) result = -result;
//        return result;

    }

    private boolean isValid(String s) {
        for (char c : s.toCharArray()) {
            if (c - '0' < 0 || c - '0' > 9) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Atoi atoi = new Atoi();
        System.out.println(atoi.atoi("-2147483649"));

    }
}
