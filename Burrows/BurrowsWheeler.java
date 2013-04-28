/**
 * Created with IntelliJ IDEA.
 * User: zhuang
 * Date: 4/26/13
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class BurrowsWheeler {
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(s);

        int[] suffixArray = new int[circularSuffixArray.length()];
        char[] charArray = new char[circularSuffixArray.length()];
        int first = 0;

        for (int i = 0; i < suffixArray.length; i++) {
            if (circularSuffixArray.index(i) == 0)
                first = i;

            char lastChar;
            if (circularSuffixArray.index(i) == 0) lastChar = s.charAt(s.length() - 1);
            else lastChar = s.charAt(circularSuffixArray.index(i) - 1);

            charArray[i] = lastChar;

        }

        BinaryStdOut.write(first);

        for (char c : charArray)
            BinaryStdOut.write(c);

        BinaryStdOut.close();

    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {

    }

    private char getChar( String originalString, int startSuffixForOrigin, int index) {

        if ( index < originalString.length() - startSuffixForOrigin)
            return originalString.charAt(startSuffixForOrigin + index);
        else
            return originalString.charAt(index - (originalString.length() - startSuffixForOrigin));

    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            BurrowsWheeler.encode();
        } else if (args[0].equals("+")) {
            BurrowsWheeler.decode();
        }
    }
}
