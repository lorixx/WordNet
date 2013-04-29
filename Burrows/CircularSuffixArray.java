import java.util.Arrays;

public class CircularSuffixArray {

    private String originalString;

    private int[] csIndex;

    public CircularSuffixArray(String s) {

        originalString = s;
        csIndex = new int[s.length()];

        Node[] nodes = new Node[s.length()];

        for (int i = 0; i < originalString.length(); i++) {
            //csIndex[i] = i;
            nodes[i] = new Node(originalString, i);
        }

        Arrays.sort(nodes);

        for (int i = 0; i < nodes.length; i++) {
            csIndex[i] = nodes[i].suffix;

        }
    }

    public int length() {
        return originalString.length();
    }

    public int index(int i) {
        return csIndex[i];
    }

    private char getChar(int startSuffixForOrigin, int index) {

        if ( index < originalString.length() - startSuffixForOrigin)
            return originalString.charAt(startSuffixForOrigin + index);
        else
            return originalString.charAt(index - (originalString.length() - startSuffixForOrigin));

    }

//    /**
//     * Insertion sort for the target array
//     *
//     * @param suffixArray
//     */
//    private void sort(int[] suffixArray) {
//        int N = suffixArray.length;
//        for (int i = 1; i < N; i++) {
//            for (int j = i; j > 0 && less(suffixArray[j], suffixArray[j - 1]); j--) {
//                exch(suffixArray, j, j - 1);
//            }
//        }
//    }

//    // quicksort the array
//    private void sort(int[] a) {
//        StdRandom.shuffle(a);
//        sort(a, 0, a.length - 1);
//    }
//
//    // quicksort the subarray from a[lo] to a[hi]
//    private void sort(int[] a, int lo, int hi) {
//        if (hi <= lo) return;
//        int j = partition(a, lo, hi);
//        sort(a, lo, j-1);
//        sort(a, j+1, hi);
//    }
//
//    // partition the subarray a[lo .. hi] by returning an index j
//    // so that a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
//    private int partition(int[] a, int lo, int hi) {
//        int i = lo;
//        int j = hi + 1;
//        //Comparable v = a[lo];
//        while (true) {
//
//            // find item on lo to swap
//            while (less(++i, lo))
//                if (i == hi) break;
//
//            // find item on hi to swap
//            while (less(lo, --j))
//                if (j == lo) break;      // redundant since a[lo] acts as sentinel
//
//            // check if pointers cross
//            if (i >= j) break;
//
//            exch(a, i, j);
//        }
//
//        // put v = a[j] into position
//        exch(a, lo, j);
//
//        // with a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
//        return j;
//    }




//    private void exch(int[] suffixArray, int i, int j) {
//        int temp = suffixArray[i];
//        suffixArray[i] = suffixArray[j];
//        suffixArray[j] = temp;
//    }

    private class Node implements Comparable<Node> {
        private String originalString;
        private int suffix;

        public Node(String originalString, int suffix) {
            this.suffix = suffix;
            this.originalString = originalString;

        }

        public int compareTo(Node node) {
            return less(suffix, node.suffix);
        }

        private int less(int suffixA, int suffixB) {
            for (int i = 0; i < originalString.length(); i++) {
                if (getChar(suffixB, i) > getChar(suffixA, i))
                    return -1;
                else if (getChar(suffixB, i) == getChar(suffixA, i))
                    continue;
                else
                    return 1;
            }
            return 1;
        }
    }

    public static void main(String[] args) {
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray("ABRACADABRA!");
        for (int i = 0; i < circularSuffixArray.length(); i++) {
            StdOut.print(circularSuffixArray.index(i) + " ");
            //StdOut.print(circularSuffixArray.getChar(circularSuffixArray.length() - 3, i));
        }
    }
}