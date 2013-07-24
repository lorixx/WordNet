package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/22/13
 * Time: 11:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class Anagrams {
    public ArrayList<String> anagrams(String[] strs) {
        ArrayList<String> result = new ArrayList<String>();
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        for (String s : strs) {
            char[] sorted = s.toCharArray();
            Arrays.sort(sorted);
            String newString = new String(sorted);
            if (map.containsKey(newString))
                map.put(newString, map.get(newString) + 1);
            else
                map.put(newString, 1);
        }

        HashSet<String> anagrams = new HashSet<String>();
        for (String key :map.keySet()) {
            if (map.get(key) > 1)
                anagrams.add(key);
        }

        for (String s : strs) {
            char[] sorted = s.toCharArray();
            Arrays.sort(sorted);
            String newString = new String(sorted);
            if (anagrams.contains(newString))
                result.add(s);
        }

        return result;
    }
}