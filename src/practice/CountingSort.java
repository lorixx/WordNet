package practice;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 6/26/13
 * Time: 11:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class CountingSort {

    public int[] sort (int[] array) {

        int[] keys = new int[256];
        int[] sortedArray = new int[array.length];
        for (int key : array) {
            keys[key]++;
        }

        int total = 0;
        for (int i = 0; i < keys.length; i++) {
            int oldCount = keys[i];
            keys[i] = total;
            total += oldCount;
        }

        for (int i = 0; i < array.length; i++) {
            sortedArray[keys[array[i]]] = array[i];
            keys[array[i]]++;
        }

        return sortedArray;
    }

    public static void main(String[] args) {
        CountingSort cs = new CountingSort();
        int[] array = {3, 2, 3, 1, 0, 2, 3};
        int[] result = cs.sort(array);
        for (int i : result)
            System.out.println(i);
    }
}
