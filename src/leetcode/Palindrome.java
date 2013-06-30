package leetcode;

import java.util.Timer;

/**
 * Created with IntelliJ IDEA.
 * User: zhuang
 * Date: 5/2/13
 * Time: 3:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class Palindrome {

    public static boolean isPalindrome(String s) {
        int start = 0, end = s.length() - 1;
        while (start < end) {
            if (s.charAt(start++) != s.charAt(end--)) return false;
        }
        return true;
    }


    /**
     * It is a correct Dynamic Programming solution
     *
     *
     * @param s
     * @return
     */
    public static int minCut(String s) {

//     /* This version is a O(n^3) implementation  */  Running time is O(n^3), but it failed on the online judge.
//        if (s == null) return 0;
//        if (s.length() <= 1) return 0;
//
//        int length = s.length();
//        int[][] table = new int[length][length];
//        int min;
//
//        for (int size = 1; size <= length; size++) {
//            for (int i = 0; i < length - size + 1; i++) {
//                int j = i + size - 1; // since we start from 0, need to minus 1 for array index
//                if (isPalindrome(s.substring(i, j + 1))) {
//                    table[i][j] = 0;
//                    continue;
//                }
//
//                min = 999999;
//                for (int k = i; k < j; k++) {
//                    if (min > table[i][k] + table[k + 1][j] + 1) {
//                        min = table[i][k] + table[k + 1][j] + 1;
//                    }
//                }
//                table[i][j] = min;
//            }
//        }
//
//        return table[0][length - 1];

        int length = s.length();
        int[] minCut = new int[length];
        boolean[][] isPalindrome = new boolean[length][length];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (i == j) isPalindrome[i][j] = true;
            }
        }

        // minCut is how many cut from 0 to i, [0, i] is the area that we use
        for (int i = 0; i < length; i++) {
            minCut[i] = i;
        }
        //minCut[0] = 0;

        for (int end = 1; end < length; end++) {

            /* Critical check for those are palindrome */
//            if (size > 1 && s.charAt(0) == s.charAt(size - 1) && (isPalindrome[1][size - 2] || size < 3)) {
//                minCut[size - 1] = 0;
//                isPalindrome[0][size - 1] = true;
//                continue;
//            }
            if (s.charAt(0) == s.charAt(end) && (isPalindrome[1][end - 1] || end < 2)) {
                minCut[end] = 0;
                isPalindrome[0][end] = true;
                continue;
            }


            for (int i = 0; i < end; i++) {  //size minus 1 due to the edge

                if ( end - i > 1 && s.charAt(i + 1) == s.charAt(end) && (isPalindrome[i + 2][end - 1] || end - i < 2 )) {
                    isPalindrome[i + 1][end] = true;
                    if (minCut[end] > minCut[i] + 1) {
                        minCut[end] = minCut[i] + 1;
                    }
                }


            }
        }

        return minCut[length - 1];

    }

    public static void main(String[] args) {
        System.out.println(Palindrome.minCut("abcbammakakss"));
        System.out.println(Palindrome.minCut("aaa"));

        long start = System.nanoTime();
        System.out.println(Palindrome.minCut("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
        long duration = System.nanoTime() - start;
        System.out.println((double) duration / 1000000000 + "second");
    }
}
