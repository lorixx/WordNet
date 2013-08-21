package practice;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 8/21/13
 * Time: 12:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class FindMaxIncreasingRange {

    public static int[] maxIncreasingRange(int[] array) {

        int maxLength = 0;
        int maxStart = -1;

        int [][]maxs = new int[array.length][array.length];
        int [][]indices = new int[array.length][array.length];

        for (int i = 0; i < array.length; i++) {
            maxs[i][i] = 1;
            indices[i][i] = i;
        }

        for (int i = 0; i < array.length; i++) {

            for (int j = 0; j < i; j++) {

                if (array[i - j] > array[i - j - 1]) {
                    maxs[j][i] = maxs[j][i - 1] + 1;   // update the max table
                    indices[j][i] = indices[j][i - 1]; // same as before
                } else {
                    maxs[j][i] = 1;
                    indices[j][i] = i;
                }
                if (maxs[j][i] > maxLength) {
                    maxLength = maxs[j][i];
                    maxStart = indices[j][i];
                }
            }
        }

        int[] result = {maxStart, maxLength};

        return result;
    }


    public static void main(String[] args) {
        int[] test = {-1, 2, 2, 2, 2, 2, 3, 4 ,5, -10, -9, -11};

        int[] result = FindMaxIncreasingRange.maxIncreasingRange(test);

        System.out.println("(" + result[0] + ", " + result[1] + ")");

    }

}
