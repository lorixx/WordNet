package leetcode;

import java.util.*;

/**
 Given an array of strings, return all groups of strings that are anagrams.

 Note: All inputs will be in lower-case.
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

    public ArrayList<String> betterAnagrams(String[] strs) {
        HashMap<String, LinkedList<String>> map = new HashMap<String, LinkedList<String>>();
        ArrayList<String> result = new ArrayList<String>();

        for (String s : strs) {
            char[] sorted = s.toCharArray();
            Arrays.sort(sorted);
            String newString = new String(sorted);
            if (map.containsKey(newString)) {
                map.get(newString).add(s);
            } else {
                LinkedList<String> list = new LinkedList<String>();
                list.add(s);
                map.put(newString, list);
            }
        }

        for (String key : map.keySet()) {
            LinkedList<String> currentList = map.get(key);
            if (currentList.size() > 1) {
                for (String str : currentList)
                    result.add(str);
            }
        }

        return result;
    }
}