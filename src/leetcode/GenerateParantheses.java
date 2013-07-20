package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/19/13
 * Time: 10:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class GenerateParantheses {

    public ArrayList<String> generateParenthesis(int n) {
        // Start typing your Java solution below
        // DO NOT write main() function
        if (n < 1) return null;
        ArrayList<String> result = new ArrayList<String>();
        if (n == 1) {
            result.add("()");
            return result;
        } else {
            HashSet<String> set = new HashSet<String>();
            ArrayList<String> subset = generateParenthesis(n - 1);
            for (String subString : subset) {
                StringBuilder sb = new StringBuilder();
                sb.append("()");
                sb.append(subString);

                String newString = sb.toString();
                if (!set.contains(newString)) {
                    result.add(newString);
                    set.add(newString);
                }

                for (int i = 0; i < subString.length(); i++) {

                    if (subString.charAt(i) == ')') {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("(");
                        sb2.append(subString.substring())



                    }


                }

                StringBuilder sb2 = new StringBuilder();
                sb2.append("(");
                sb2.append(subString);
                sb2.append(")");
                newString = sb2.toString();
                if (!set.contains(newString)) {
                    result.add(newString);
                    set.add(newString);
                }

                StringBuilder sb3 = new StringBuilder();
                sb3.append(subString);
                sb3.append("()");
                newString = sb3.toString();
                if (!set.contains(newString)) {
                    result.add(newString);
                    set.add(newString);
                }
            }




            return result;
        }
    }
}
