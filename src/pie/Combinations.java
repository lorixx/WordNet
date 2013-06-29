package pie;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 5/5/13
 * Time: 6:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class Combinations {
    /**
     * This is example code from PIE, can't really understand how it works.
     */
//    private StringBuilder out = new StringBuilder();
//    private final String in;
//
//    public Combinations(final String str) {in = str;}
//
//    public void combine() {
//        combine(0);
//    }
//
//    private void combine(int start) {
//        for (int i = start; i < in.length(); i++) {
//            out.append(in.charAt(i));
//            System.out.println(out);
//            if (i < in.length())
//                combine(i + 1);
//            out.setLength(out.length() - 1);
//        }
//    }
//
    public static void main(String[] args) {
//        Combinations cb = new Combinations("wxyz");
//        cb.combine();
        ArrayList<String> result = Combinations.combination("wxyz");
        for (String s : result)
            System.out.println(s);

    }


    /**
     * My understanding is that each iteration return an array of sub combination so that it could be used
     * by the upper level, to append and combine for new String list.
     */
    public static ArrayList<String> combination(String string) {
        return combination(string.toCharArray());
    }

    private static ArrayList<String> combination(char[] array) {
        ArrayList<String> result = new ArrayList<String>();
        if (array.length == 1) {
            result.add(new String(array));
        } else {
            char[] newArray = new char[array.length - 1];
            for (int i = 1; i < array.length; i++) {
                newArray[i - 1] = array[i]; //populate array, sub array
            }
            char currentChar = array[0]; //first character
            ArrayList<String> temps = combination(newArray);
            for (String s : temps) {
                StringBuilder sb = new StringBuilder();
                sb.append(currentChar);
                sb.append(s);
                result.add(s);  // need to add the sub list as well
                result.add(sb.toString());  // need to add the appending version
            }
            result.add(new String(array, 0, 1));
        }
        return result;
    }


}
