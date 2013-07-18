package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/16/13
 * Time: 9:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class LadderLength {

    public int ladderLength(String start, String end, HashSet<String> dict) {


        if (start.equals(end)) return 0;

        if (dict.size() < 1) return 0;

        ArrayList<String> queue =  new ArrayList<String>();  // Use BFS
        HashMap<String, Integer> map = new HashMap<String, Integer>(); // use to track the level

        queue.add(start);
        map.put(start, 0);

        while (!queue.isEmpty()) {
            String currentString = queue.remove(0);

            for (int i = 0; i < currentString.length(); i++) {

                for (int j = 0; j < 26; j++) {
                    StringBuilder sb = new StringBuilder(currentString);
                    sb.setCharAt(i, (char)('a' + j));
                    String newString = sb.toString();

                    if (newString.equals(end))
                        return map.get(currentString) + 1; // found the result

                    if (dict.contains(newString) && !newString.equals(currentString)) {
                        queue.add(newString);
                        map.put(newString, map.get(currentString) + 1);
                    }
                }
            }
        }

        return 0;

    }

    public  static void main(String[] args) {

        HashSet<String> set = new HashSet<String>();
        set.add("hot");
        set.add("dtg");
        set.add("hog");


        LadderLength ladderLength = new LadderLength();

        System.out.println(ladderLength.ladderLength("hot", "atg", set));

    }

}
