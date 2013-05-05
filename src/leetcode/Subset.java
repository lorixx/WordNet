package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 5/5/13
 * Time: 2:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class Subset {

    /**
     * The idea behind this is that we need to understand what the real behavior among all these combinations
     * keep a "visited" queue/arrayList to keep track what we have visited, then append the new character into the visited.
     *
     * The hint is from PIE: Recursion chapter.
     *
     * @param S
     * @return
     */
    public ArrayList<ArrayList<Integer>> subsets(int[] S) {
        // Start typing your Java solution below
        // DO NOT write main() function

        int[] array = Arrays.copyOf(S, S.length);

        Arrays.sort(array);
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> visited = new ArrayList<ArrayList<Integer>>();

        for (int i = array.length - 1; i >= 0; i--) {

            ArrayList<Integer> single = new ArrayList<Integer>();
            single.add(array[i]);
            result.add(single);

            ArrayList<ArrayList<Integer>> recordForVisited = new ArrayList<ArrayList<Integer>>();
            for (ArrayList<Integer> currentArray : visited) {
                ArrayList<Integer> temp = new ArrayList<Integer>();
                temp.add(array[i]); // append the current character
                for (int j : currentArray) {
                    temp.add(j);
                }
                result.add(temp); //add to result
                recordForVisited.add(temp); // add to record For visit
            }

            visited.add(single);  // add the single to the visite
            for (ArrayList<Integer> rest : recordForVisited) {
                visited.add(rest);
            }
        }

        ArrayList<Integer> empty = new ArrayList<Integer>();
        result.add(empty);


        return result;
    }
}
