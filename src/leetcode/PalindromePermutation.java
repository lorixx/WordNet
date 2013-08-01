package leetcode;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/31/13
 * Time: 12:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class PalindromePermutation {

    private ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();


    public ArrayList<ArrayList<String>> partition(String s) {
        // Start typing your Java solution below
        // DO NOT write main() function
        this.result.clear();
        ArrayList<String> currentList = new ArrayList<String>();
        dfs(s, 0, currentList);
        return this.result;

    }


    private void dfs(String s, int start, ArrayList<String> currentList) {

        if (start == s.length()) {
            this.result.add(new ArrayList<String>(currentList));
            return;
        } else {

            for (int i = start; i < s.length(); i++) {

                if (isPalindrome(s.substring(start, i + 1))) {
                    currentList.add(s.substring(start, i + 1));
                    dfs(s, i + 1, currentList);
                    currentList.remove(currentList.size() - 1); //remove the last one ???
                }
            }
        }
    }

    private boolean isPalindrome(String s) {
        int start = 0;
        int end = s.length() - 1;

        while (end > start) {
            if (s.charAt(end) != s.charAt(start))
                return false;
            start++;
            end--;
        }
        return true;
    }

    public static void main(String[] args) {
        PalindromePermutation pp =new PalindromePermutation();
        ArrayList<ArrayList<String>> result = pp.partition("adda");

        for (ArrayList<String> temp : result) {

            for (String s : temp) {
                System.out.print(s + "  ");
            }
            System.out.println();

        }
    }
}
