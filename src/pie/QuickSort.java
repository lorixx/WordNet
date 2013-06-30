package pie;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 6/29/13
 * Time: 1:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class QuickSort {

    public void quickSort(int a[]) {
        quickSort(a, 0, a.length - 1);
    }

    private void quickSort(int a[], int low, int high) {

        if (low >= high) return;

        int pivotIndex = indexAfterPartition(a, low, high);
        quickSort(a, low, pivotIndex - 1);
        quickSort(a, pivotIndex + 1, high);
    }

    private int indexAfterPartition(int a[], int low, int high) {

        int pivot = a[low];
        int l = low;
        int r = high + 1;

        while (true) {
            while (a[++l] < pivot) if (l == high) break;
            while (pivot < a[--r]) if (r == low) break;
            if (l >= r) break;
            swap(a, l, r);
        }

        swap(a, low, r);
        return r;
    }

    private void swap(int a[], int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {
        QuickSort quickSort = new QuickSort();
        int []result = {6, 3, 8, 10, 2, 5, 1, 0, 12};
        //int []result = {2, 1, 0, 5, 3};
        //int [] result = {2, 1};

        quickSort.quickSort(result);

        for (int i : result) System.out.println(i);
    }
}
