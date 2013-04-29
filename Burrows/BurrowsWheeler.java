import java.util.Arrays;

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

        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        BinaryStdIn.close();

        // init two char arrays
        char[] encodedCharArray = s.toCharArray();
        char[] sortedCharArray = s.toCharArray();
        Arrays.sort(sortedCharArray);

        int[] next = new int[s.length()];

        // create a ST to keep track of char positions
        ST<Character, Queue<Integer>> table = new ST<Character, Queue<Integer>>();
        for (int i = 0; i < s.length(); i++) {

            char currentChar = encodedCharArray[i];
            if (!table.contains(currentChar)) {
                Queue<Integer> queue = new Queue<Integer>();
                table.put(currentChar, queue);
            }
            table.get(currentChar).enqueue(i);
        }

        // create next array
        for (int i = 0; i < s.length(); i++) {
            char ch = sortedCharArray[i];
            int nextIndex = table.get(ch).dequeue();
            next[i] = nextIndex;
        }

//        for (int i : next) {
//            StdOut.println(i);
//        }

        // Print the original out using the first index
        int currentIndex = first;
        //StdOut.println("First is " + first);
        for (int i = 0; i < s.length(); i++) {

            BinaryStdOut.write(sortedCharArray[currentIndex]);
            currentIndex = next[currentIndex];

//            if (currentIndex == first)
//                break;

        }
        BinaryStdOut.close();
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
