package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: zhuang
 * Date: 5/6/13
 * Time: 11:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class EditDistance {
    /*
     * Given two words word1 and word2, find the minimum number of steps required to convert word1 to word2. (each operation is counted as 1 step.)

        You have the following 3 operations permitted on a word:

        a) Insert a character
        b) Delete a character
        c) Replace a character

        Refer to Dynamic Programming book from "Algorithms"
     */
    public int minDistance(String word1, String word2) {
        // Start typing your Java solution below
        // DO NOT write main() function
        int length1 = word1.length();
        int length2 = word2.length();

        int[][] dist = new int[length1 + 1][length2 + 1];

        // init
        for (int i = 0; i < length1 + 1; i++) {
            dist[i][0] = i;
        }

        for (int j = 0; j< length2 + 1; j++) {
            dist[0][j] = j;
        }

        for (int j = 1; j < length2 + 1; j++) {
            for (int i = 1; i < length1 + 1; i++) {
                int distForLastChar = diff(word1, word2, i, j);
                dist[i][j] = Math.min(distForLastChar + dist[i - 1][j - 1],
                                      Math.min(dist[i - 1][j] + 1, dist[i][j - 1] + 1));
            }
        }

        return dist[length1][length2];

    }

    private int diff(String w1, String w2, int i, int j) {
        if (i > w1.length() || j > w2.length()) // be careful with the array suffix
            return 1;
        if (w1.charAt(i - 1) != w2.charAt(j - 1))
            return 1;
        else
            return 0;
    }

    public static void main(String[] args) {
        EditDistance editDistance = new EditDistance();
        System.out.println(editDistance.minDistance("a", "ab"));
    }
}
