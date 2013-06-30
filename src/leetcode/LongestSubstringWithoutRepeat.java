package leetcode;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 6/29/13
 * Time: 10:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class LongestSubstringWithoutRepeat {

    private HashMap<String, Integer> map = new HashMap<String, Integer>();

    private boolean isSubStringWithoutRepeat(String s) {
        HashSet<Character> set = new HashSet<Character>();
        for (int i = 0; i < s.length(); i++) {
            if (set.contains(s.charAt(i))) return false;
            set.add(s.charAt(i));
        }
        return true;
    }

    // DP version, which is not efficient O(n^3)
    public int lengthOfLongestSubstring(String s) {
        // Start typing your Java solution below
        // DO NOT write main() function

        if (s == null) return 0;
        if (s.equals("")) return 0;
        if (s.length() < 2) return 1;

        int[][] results = new int[s.length()][s.length()]; // start and end index array

        for (int end = 0; end < s.length(); end++) {
            for (int start = end; start >= 0; start--) {
                if (end == start) results[start][end] = 1;
                String subString = s.substring(start, end + 1);

                if (map.containsKey(subString)) {
                    results[start][end] = map.get(subString);
                    continue;
                }

                if (isSubStringWithoutRepeat(subString)) {
                    results[start][end] = subString.length();
                } else {
                    int up = results[start][end - 1];
                    int right = results[start + 1][end];
                    results[start][end] = up > right ? up : right;
                }
                map.put(subString, results[start][end]);
            }
        }

        return results[0][s.length() - 1];

    }

    // LinearVersion
    public int solution(String s) {

        int start = 0;
        int end = 0;
        int max = 0;
        HashMap<Character, Integer> charIndexMap = new HashMap<Character, Integer>();
        while (end < s.length()) {
            if (end == start) {
                if (end - start > max) max = end - start;
                charIndexMap.put(s.charAt(end), end);
                end++;
            } else {
                if (charIndexMap.containsKey(s.charAt(end))) {

                    int currentLength = end - start;
                    start = charIndexMap.get(s.charAt(end)) + 1;
                    charIndexMap.put(s.charAt(end), end); // update its index
                    if (currentLength > max) max = currentLength;
                    end = start;
                    charIndexMap.clear();
                } else {
                    charIndexMap.put(s.charAt(end), end);
                    end++;
                }
            }

        }

        if (end - start > max) max = end - start;
        return max;
    }

    public static void main(String[] args) {
        LongestSubstringWithoutRepeat longestSubstringWithoutRepeat = new LongestSubstringWithoutRepeat();
        //System.out.println(longestSubstringWithoutRepeat.solution("abbabababa"));
        System.out.println(longestSubstringWithoutRepeat.solution("56"));

    }

}
