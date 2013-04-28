
public class CircularSuffixArray {

    private String originalString;

    private int[] csIndex;

    public CircularSuffixArray(String s) {

        originalString = s;
        csIndex = new int[s.length()];

        for (int i = 0; i < originalString.length(); i++) {
            csIndex[i] = i;
        }

        sort(csIndex);
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

    /**
     * Insertion sort for the target array
     *
     * @param suffixArray
     */
    private void sort(int[] suffixArray) {
        int N = suffixArray.length;
        for (int i = 1; i < N; i++) {
            for (int j = i; j > 0 && less(suffixArray[j], suffixArray[j - 1]); j--) {
                exch(suffixArray, j, j - 1);
            }
        }
    }

    private boolean less(int suffixA, int suffixB) {
        for (int i = 0; i < originalString.length(); i++) {
            if (getChar(suffixB, i) > getChar(suffixA, i))
                return true;
            else if (getChar(suffixB, i) == getChar(suffixA, i))
                continue;
            else
                return false;
        }
        return false;
    }

    private void exch(int[] suffixArray, int i, int j) {
        int temp = suffixArray[i];
        suffixArray[i] = suffixArray[j];
        suffixArray[j] = temp;
    }

//    public static void main(String[] args) {
//        CircularSuffixArray circularSuffixArray = new CircularSuffixArray("ABRACADABRA!");
//        for (int i = 0; i < circularSuffixArray.length(); i++) {
//            StdOut.print(circularSuffixArray.index(i) + " ");
//            //StdOut.print(circularSuffixArray.getChar(circularSuffixArray.length() - 3, i));
//        }
//    }
}