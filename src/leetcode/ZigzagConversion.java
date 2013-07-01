package leetcode;

import java.util.ArrayList;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 6/30/13
 * Time: 10:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class ZigzagConversion {

    /**
     * Math, need to figure out the pattern for the index
     */
    public String convert(String s, int nRows) {

        if (nRows <= 1) return s;

        ArrayList<Character> q = new ArrayList<Character>();
        int currentIndex;
        for (int r = 0; r < nRows; r++) {
            currentIndex = r;

            while (currentIndex < s.length()) {
                if (r == 0 || r == nRows - 1) {

                    q.add(s.charAt(currentIndex));
                    currentIndex += 2 * (nRows - 1);
                } else {
                    q.add(s.charAt(currentIndex));
                    currentIndex += 2 * (nRows - 1 - r);
                    if (currentIndex >= s.length()) break;
                    q.add(s.charAt(currentIndex));
                    currentIndex += 2 * r;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (char c : q) {
            sb.append(c);
        }

        return sb.toString();

    }

    public static void main(String[] args) {
        ZigzagConversion zigzagConversion = new ZigzagConversion();
        System.out.println(zigzagConversion.convert("PAYPALISHIRING", 3));
    }
}


