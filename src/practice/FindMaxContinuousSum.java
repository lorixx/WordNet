package practice;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 8/21/13
 * Time: 4:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class FindMaxContinuousSum {

    public static LinkedList<Integer> findMaxContinuousSum(int[] a) {

        int[] results = new int[a.length];
        int[] prev = new int[a.length];

        int bestEnding = 0;
        int bestSum = a[0];

        for (int i = 0; i < a.length; i++) {
            results[i] = a[i];
            prev[i] = i;
        }

        for (int i = 1; i < a.length; i++) {

            if (a[i] + results[i - 1] > a[i]) {
                results[i] = a[i] + results[i - 1];
                prev[i] = i - 1;
            }

            if (results[i] > bestSum) {
                bestSum = results[i];
                bestEnding = i;
            }
        }

        LinkedList<Integer> result = new LinkedList<Integer>();
        result.add(bestEnding); // add the best ending first
        for (int i = bestEnding; i >= 0; i--) {
            if (i == prev[i]) {
                break;
            }
            result.add(prev[i]);
        }


        return result;
    }

    public static void main(String[] args) {
        int[] test = {5, 15, -30, 10, -5, 40, 10};

        LinkedList<Integer> result = FindMaxContinuousSum.findMaxContinuousSum(test);
        for (int i : result)
            System.out.println(i);
    }

}
