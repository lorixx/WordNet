package pie;

import java.util.ArrayList;

/**
 * Recursive version
 */
public class PrintPhoneNumberCombination {

    public ArrayList<String> letterCombinations(String digits) {

        if (digits == null) return null;
        ArrayList<String> result = new ArrayList<String>();

        if (digits.equals("")) {
            result.add("");
            return result;
        }

        if (digits.length() == 1) return lettersForNumber(digits);
        else {
            ArrayList<String> subSet = letterCombinations(digits.substring(1));
            ArrayList<String> letters = lettersForNumber(digits.substring(0, 1));
            for (String letter : letters) {
                for (String subString : subSet) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(letter);
                    sb.append(subString);
                    result.add(sb.toString());
                }
            }
            return result;
        }
    }

    /**
     * Iterative version of phone number combination, we keep track of an array of indices
     * This indices array keeping track of the position of letter place. If the last bit iteration finished, then
     * we move to the next-to-last bit and increase the index.
     *
     * In the end, when we discover that the index for the first character reach to the end, then we stop and we have
     * a list of letter combinations.
     */
    public ArrayList<String> iterativeLetterCombinations(String digits) {
        if (digits == null) return null;
        ArrayList<String> result = new ArrayList<String>();

        if (digits.equals("")) {
            result.add("");
            return result;
        }

        if (digits.length() == 1) return lettersForNumber(digits);
        else {
            int[] indices = new int[digits.length()];
            for (int i = 0; i < indices.length; i++)
                indices[i] = 0; // start from index 0 for each
            while (true) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < digits.length(); i++) {
                    sb.append(lettersForNumber(digits.substring(i, i+1)).get(indices[i])); // append letter
                }
                result.add(sb.toString());
                indices[indices.length - 1]++; // update the value in the last slot

                //update the indices array
                for (int j = indices.length - 1; j >= 0; j--) {
                    if (indices[j] == lettersForNumber(digits.substring(j, j+1)).size()) {

                        if (j == 0) return result;

                        indices[j] = 0; // back to original state
                        indices[j - 1]++;
                    }
                }
            }
        }
    }

    private ArrayList<String> lettersForNumber(String digit) {
        ArrayList<String> letters = new ArrayList<String>();

        if (digit.equals("0") || digit.equals("1") || digit.equals("*") || digit.equals("#"))
            letters.add(digit);

        int num = Integer.parseInt(digit);
        switch (num) {
            case 2:
                letters.add("a"); letters.add("b");letters.add("c");
                break;
            case 3:
                letters.add("d"); letters.add("e");letters.add("f");
                break;
            case 4:
                letters.add("g"); letters.add("h");letters.add("i");
                break;
            case 5:
                letters.add("j"); letters.add("k");letters.add("l");
                break;
            case 6:
                letters.add("m"); letters.add("n");letters.add("o");
                break;
            case 7:
                letters.add("p"); letters.add("q");letters.add("r"); letters.add("s");
                break;
            case 8:
                letters.add("t"); letters.add("u");letters.add("v");
                break;
            case 9:
                letters.add("w"); letters.add("x");letters.add("y"); letters.add("z");
                break;
            default:
                break;
        }
        return letters;
    }

    public static void main(String[] args) {
        PrintPhoneNumberCombination printPhoneNumberCombination = new PrintPhoneNumberCombination();
        ArrayList<String> result = printPhoneNumberCombination.iterativeLetterCombinations("23");
        for (String s : result)
            System.out.println(s);
    }
}
