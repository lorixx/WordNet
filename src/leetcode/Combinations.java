package leetcode;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/23/13
 * Time: 9:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class Combinations {


    public ArrayList<ArrayList<Integer>> combine(int n, int k) {

        if (n < 1 || k < 1 || k > n) return null;

        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

        if (k == 1) {

            for (int i = 1; i <= n; i++) {
                ArrayList<Integer> temp = new ArrayList<Integer>();
                temp.add(i);
                result.add(temp);
            }
            return result;
        } else {

            ArrayList<ArrayList<Integer>> oldCombinations = combine(n, k - 1);

            for(ArrayList<Integer> list : oldCombinations) {

                int last = list.get(list.size() - 1);
                for (int i = last + 1; i <= n; i++) {
                    list.add(i);
                    result.add(list);
                }
            }
            return result;

        }
    }
}