package pie;

import java.util.ArrayList;


public class PrintPhoneNumberCombination {

    public ArrayList<String> letterCombinations(String digits) {
        // Start typing your Java solution below
        // DO NOT write main() function
        if (digits == null) return null;

        if (digits.equals("")) {
            return new ArrayList<String>();
        }

        if (digits.length() == 1) return lettersForNumber(digits);
        else {
            ArrayList<String> result = new ArrayList<String>();
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
        ArrayList<String> result = printPhoneNumberCombination.letterCombinations("23");
        for (String s : result)
            System.out.println(s);
    }
}
