package leetcode;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 5/5/13
 * Time: 11:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class Permutation {

    public ArrayList<ArrayList<Integer>> permute(int[] num) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        for (int i : num) array.add(i);
        return recursivePermute(array);
    }

    private ArrayList<ArrayList<Integer>> recursivePermute(ArrayList<Integer> allInt) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        if (allInt.size() == 1) {
            result.add(allInt);
        }


        for (int currentAvailable : allInt) {

            ArrayList<Integer> others = new ArrayList<Integer>();
            for (int v : allInt) {
                if (v == currentAvailable) continue;
                others.add(v); //create aux
            }

            // get the permute for next level
            ArrayList<ArrayList<Integer>> permutes = recursivePermute(others);

            for (ArrayList<Integer> currentPermute : permutes) {
                ArrayList<Integer> temp = new ArrayList<Integer>();
                temp.add(currentAvailable);
                for (int v : currentPermute) {
                     temp.add(v);
                }
                result.add(temp);
            }

        }
        return result;
    }
}
