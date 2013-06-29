package pie;


import java.util.ArrayList;

/**
 * Basically return a list of String at each recursive step
 * The way we create the sub string is that:
 * 1. we pick a character
 * 2. we generate a character list
 * 3. permutate the character list and append each to the target character
 * 4. return the final result
 */
public class Permutation {

    public static ArrayList<String> permutate(String string) {
        char[] originalArray =  string.toCharArray();
        return permutate(originalArray);
    }

    private static ArrayList<String> permutate(char[] array) {
        ArrayList<String> result = new ArrayList<String>();
        if (array.length == 1) {
            result.add(new String(array));
            return result;
        } else {

            for (int i = 0; i < array.length; i++) {
                char currentChar = array[i];
                char[] newArray = new char[array.length - 1];
                for (int a = 0, b = 0; a < array.length; a++) {
                    if (a == i) continue;
                    else newArray[b++] = array[a];
                }
                ArrayList<String> temps = permutate(newArray);
                for (String s : temps) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(currentChar);
                    sb.append(s);
                    result.add(sb.toString());
                }
            }
            return result;
        }
    }

    public static void main(String[] args) {
        ArrayList<String> result = Permutation.permutate("");
        for (String s : result)
            System.out.println(s);
    }


}
