package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: zhuang
 * Date: 5/6/13
 * Time: 11:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class LongestPalindromSubString {
    /*
     *  Given a string S, find the longest palindromic substring in S.
     *  You may assume that the maximum length of S is 1000, and there exists one unique longest palindromic substring.
     */
    public String longestPalindrome(String s) {
        // Start typing your Java solution below
        // DO NOT write main() function

        int bestStart = 0;
        int bestEnd = 0;
        int dist = 0;

        boolean[][] isPalindrome = new boolean[s.length()][s.length()];

        for (int i = 0; i < s.length(); i++) {
            isPalindrome[i][i] = true;
        }

        for (int end = 1; end < s.length(); end++) {
            for (int start = 0; start < end; start++) {
                if (s.charAt(start) == s.charAt(end) && (isPalindrome[start + 1][end - 1] || end - start < 2)) {
                    isPalindrome[start][end] = true;
                    int currentDist = end - start;
                    if (currentDist > dist) {
                        bestStart = start;
                        bestEnd = end;
                        dist = currentDist;
                    }
                }
            }
        }

        return s.substring(bestStart, bestEnd + 1);
    }

    public static void main(String[] args) {
        LongestPalindromSubString longestPalindromSubString = new LongestPalindromSubString();
        System.out.println(longestPalindromSubString.longestPalindrome("aabckkcb123sdf"));
    }
}
