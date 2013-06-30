package leetcode;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 6/29/13
 * Time: 5:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class FourSum {

    public ArrayList<ArrayList<Integer>> fourSum(int[] num, int target) {
        // Start typing your Java solution below
        // DO NOT write main() function
        ArrayList<ArrayList<Integer>> results = new ArrayList<ArrayList<Integer>>();
        if (num == null || num.length < 4) return results;

        int[] aux = Arrays.copyOf(num, num.length);

        Arrays.sort(aux);


        for (int a = 0; a < aux.length - 3; a++) {

            if (a > 0 && aux[a] == aux[a - 1]) continue;  // handle duplicate

            int threeSum = target - aux[a];
            for (int b = a + 1; b < aux.length - 2; b++) {

                if (b > a + 1 && aux[b] == aux[b - 1]) continue; // handle duplicate

                int twoSum = threeSum - aux[b];
                int c = b + 1; // the start of array
                int d = aux.length - 1; // the last of the array

                while (c < d) {
                    if (aux[c] + aux[d] > twoSum) d--;
                    else if (aux[c] + aux[d] < twoSum) c++;
                    else {

                        ArrayList<Integer> result = new ArrayList<Integer>();
                        result.add(aux[a]); result.add(aux[b]); result.add(aux[c]); result.add(aux[d]);
                        results.add(result);

                        do {
                            c++;
                        } while (c < d && aux[c] == aux[c - 1]);
                        do {
                            d--;
                        } while (c < d && aux[d] == aux[d + 1]);
                    }
                }
            }
        }

        return results;
    }


    public static void main(String[] args) {

        // test 1
        int[] array = {-1, 3, 4, 5, 8, -2, 7, 10, 43, 2, 0, 1, 6};
        FourSum fourSum = new FourSum();
        ArrayList<ArrayList<Integer>> result =  fourSum.fourSum(array, 14);
        for (ArrayList<Integer> r : result) {
            for (int i : r)
                System.out.println(i);
            System.out.println("========");
        }

        // test 2
//        int[] array2 = {5,5,5,5,5,5,5,5,5,5,5};
//        FourSum fourSum = new FourSum();
//        ArrayList<ArrayList<Integer>> result2 =  fourSum.fourSum(array2, 20);
//        for (ArrayList<Integer> r : result2) {
//            for (int i : r)
//                System.out.println(i);
//            System.out.println("========");
//        }

        // test 3
//        int[] array2 = {-5, -4, -3, -2, -1, 0, 0, 1, 2, 3, 4, 5};
//        FourSum fourSum = new FourSum();
//        ArrayList<ArrayList<Integer>> result2 = fourSum.fourSum(array2, 0);
//        for (ArrayList<Integer> r : result2) {
//            for (int i : r)
//                System.out.println(i);
//            System.out.println("========");
//        }
    }

}
