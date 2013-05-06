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
    public ArrayList<ArrayList<Integer>> iterativeSubsets(int[] S) {
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

    public ArrayList<ArrayList<Integer>> recursiveSubsets(int[] S) {

        int[] sortedArray = Arrays.copyOf(S, S.length);
        Arrays.sort(sortedArray); // 1. first sort the array

        ArrayList<ArrayList<Integer>> empty = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> result = recursiveSubsets(sortedArray.length - 1, sortedArray, empty);

        // add the empty array to result
        ArrayList<Integer> emptyArray = new ArrayList<Integer>();
        result.add(emptyArray);
        return result;
    }

    /*
     * Recursive function to create combinations
     */
    private ArrayList<ArrayList<Integer>> recursiveSubsets(int index, int[] array, ArrayList<ArrayList<Integer>> visited) {
        if (index < 0)
            return visited;

        ArrayList<ArrayList<Integer>> newVisited = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> singleInteger = new ArrayList<Integer>();
        singleInteger.add(array[index]);
        newVisited.add(singleInteger);

        for (ArrayList<Integer> current : visited) {

            ArrayList<Integer> temp = new ArrayList<Integer>();

            temp.add(array[index]);
            for (int v : current) {
                temp.add(v);
            }

            newVisited.add(temp);
            newVisited.add(current);
        }

        return recursiveSubsets(index - 1, array, newVisited);
    }


    public static void main(String[] args) {
        Subset subset = new Subset();
        int[] array = {1, 2};
        System.out.println(subset.recursiveSubsets(array));
    }
}
