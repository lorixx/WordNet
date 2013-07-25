package leetcode;

/**
 * Two pointers, same technique as quick sort
 */
public class ValidPalindrome {

    public boolean isPalindrome(String s) {
        // Start typing your Java solution below
        // DO NOT write main() function
        s = s.trim();
        s = s.toLowerCase();
        if (s.equals("")) return true;

        int start = -1, end = s.length();

        while (start < end) {
            while (!isAlphabec(s.charAt(++start))) if (start == s.length() - 1) break;
            while (!isAlphabec(s.charAt(--end))) if (end == 0) break;

            if (start >= end) break;

            if (s.charAt(start) != s.charAt(end))
                return false;
        }

        return true;

    }

    private boolean isAlphabec(char c) {
        return (c - 'a' >= 0 && c - 'z' <= 0) || (c - '0' >= 0 && c- '9' <= 0);
    }

    public static void main(String[] args) {
        ValidPalindrome validPalindrome = new ValidPalindrome();
        System.out.println(validPalindrome.isPalindrome("race a car"));
    }
}
