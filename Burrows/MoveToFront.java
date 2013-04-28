public class MoveToFront {


    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {

        Alphabet alphabet = Alphabet.EXTENDED_ASCII;
        char[] charList = new char[256];
        ST<Character, Integer> indexTable = new ST<Character, Integer>();

        for (int i = 0; i < 256; i++) {
            charList[i] = alphabet.toChar(i);
            indexTable.put(charList[i], i);
        }

        // read the input
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();

        for (int i = 0; i < input.length; i++) {
            char c = input[i];
            int index = indexTable.get(c);
            BinaryStdOut.write(index, 8);

            // update indexTable
            for (int j = index; j > 0; j--) {
                charList[j] = charList[j - 1];
                indexTable.put(charList[j], j);
            }

            // update the first one
            charList[0] = c;
            indexTable.put(c, 0);
        }

        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {

        Alphabet alphabet = Alphabet.EXTENDED_ASCII;
        char[] charList = new char[256];
        ST<Character, Integer> indexTable = new ST<Character, Integer>();

        for (int i = 0; i < 256; i++) {
            charList[i] = alphabet.toChar(i);
            indexTable.put(charList[i], i);
        }

        while (!BinaryStdIn.isEmpty()) {
            int index = BinaryStdIn.readInt(8);
            char c = charList[index];
            StdOut.print(c);

            for (int j = index; j > 0; j--) {
                charList[j] = charList[j - 1];
                indexTable.put(charList[j], j);
            }

            charList[0] = c;
            indexTable.put(c, 0);

        }

        BinaryStdIn.close();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {



        if (args[0].equals("-")) {
            MoveToFront.encode();
        } else if (args[0].equals("+")) {
            MoveToFront.decode();
        }
    }
}