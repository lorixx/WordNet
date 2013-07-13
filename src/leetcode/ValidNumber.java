package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/13/13
 * Time: 1:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValidNumber {

    public static boolean isNumber(String s) {
        // Start typing your Java solution below
        // DO NOT write main() function
        s = s.trim();

        if (s.equals("")) return false;

        boolean hasPoint = false;
        boolean hasE = false;
        boolean hasSign = false;
        boolean started = false;

        for (char c : s.toCharArray()) {
            if (c == ' ') {
                if (started) return false;
                else continue;
            }

            started = true;
            if (c == '+' || c == '-') {
                if (hasSign) return false;
                hasSign = true;


            } else if (c - '0' >= 0 && c - '0' <= 9)
                continue;
            else if (c == 'e' || c == 'E') {
                if (hasE) return false;
                hasE = true;

            } else if (c == '.') {
                if (hasPoint) return false;
                hasPoint = true;
            } else
                return false;

        }
        return true;

    }

    public static void main(String[] args) {
        System.out.println(ValidNumber.isNumber("  +483e23 "));
        System.out.println(ValidNumber.isNumber("  +483.34 "));
        System.out.println(ValidNumber.isNumber("  +48.3e23 "));
        System.out.println(ValidNumber.isNumber("  +483e03 "));
        System.out.println(ValidNumber.isNumber("  0.1 "));
        System.out.println(ValidNumber.isNumber("  abc "));
        System.out.println(ValidNumber.isNumber("  1 a "));



        System.out.println(ValidNumber.isNumber("  - 45.45e34  "));


    }



}
