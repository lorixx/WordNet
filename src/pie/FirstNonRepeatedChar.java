package pie;

import java.util.HashMap;


public class FirstNonRepeatedChar {

    public char find(String s) {
        char[] chars = s.toCharArray();
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        int count;
        for (char c : chars) {
            if (!map.containsKey(c)) {
                map.put(c, 1);
            } else  {
                count = map.get(c) + 1;
                map.put(c, count);
            }
        }

        for (char c : chars) {
            if (map.get(c) == 1) return c;
        }

        return '\0';
    }

    public static void main(String[] args) {
        FirstNonRepeatedChar firstNonRepeatedChar = new FirstNonRepeatedChar();
        System.out.println(firstNonRepeatedChar.find("total"));
    }
}
