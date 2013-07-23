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

        recursive(n, n, "", result);

        return result;
    }

    private void recursive(int numOfLeft, int numOfRight, String oldString, ArrayList<String> result) {

        if (numOfLeft > 0) {
            recursive(numOfLeft - 1, numOfRight, oldString + "(", result);
        }

        if (numOfLeft < numOfRight)
            recursive(numOfLeft, numOfRight - 1, oldString + ")", result);

        if (numOfRight == 0)
            result.add(oldString);
    }


    public static void main(String[] args) {
        GenerateParantheses generateParantheses = new GenerateParantheses();
        ArrayList<String> result = generateParantheses.generateParenthesis(3);

        for (String s : result)
            System.out.println(s);
    }
}
