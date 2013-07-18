package leetcode;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/16/13
 * Time: 8:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class PermutationII {

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

        HashSet<Integer> table = new HashSet<Integer>();
        for (int i = 0; i < allInt.size(); i++) {
            int currentAvailable =  allInt.get(i);
            if (table.contains(currentAvailable)) continue;
            table.add(currentAvailable);

            ArrayList<Integer> others = new ArrayList<Integer>();
            for (int j = 0; j < allInt.size(); j++) {
                if (i == j) continue;
                others.add(allInt.get(j)); //create aux
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

    public static void main(String[] args) {
        int[] A = {1 , 1 , 3};
        PermutationII permutationII = new PermutationII();
        ArrayList<ArrayList<Integer>> result = permutationII.permute(A);

        for (ArrayList<Integer> array : result) {
            for (int i : array)
                System.out.print(i + " ");

            System.out.println();
        }
    }
}
