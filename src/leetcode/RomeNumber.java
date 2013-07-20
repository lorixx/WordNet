package leetcode;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/7/13
 * Time: 6:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class RomeNumber {


    enum Numeral {
        I(1), IV(4), V(5), IX(9), X(10), XL(40), L(50), XC(90), C(100), CD(400), D(500), CM(900), M(1000);
        int weight;

        Numeral(int weight) {
            this.weight = weight;
        }
    };

    public String intToRoman(int n) {

        StringBuilder buf = new StringBuilder();

        final Numeral[] values = Numeral.values();
        for (int i = values.length - 1; i >= 0; i--) {
            while (n >= values[i].weight) {
                buf.append(values[i]);
                n -= values[i].weight;
            }
        }
        return buf.toString();
    }

    /**
     * Spent some time to figure this out.
     * Since we need to check whether we need to substract the prev char as well,
     * we need to check char at (start - 1) to see if it is less than char at (start)
     *
     */
    public int romanToInt(String s) {

        if (s.length() == 0) return 0;
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        map.put('M', 1000);
        map.put('D', 500);
        map.put('C', 100);
        map.put('L', 50);
        map.put('X', 10);
        map.put('V', 5);
        map.put('I', 1);


        int start = s.length() - 1;
        int result = 0;
        while (start >= 0) {

            if (start > 0) {
                if (map.get(s.charAt(start - 1)) < map.get(s.charAt(start))) {
                    result += map.get(s.charAt(start)) - map.get(s.charAt(start - 1));
                    start -= 2;
                } else {
                    result += map.get(s.charAt(start));
                    start--;
                }

            } else {
                result += map.get(s.charAt(start--));
            }

        }

        return result;
    }

}
