package leetcode;

/**
 * Brute-force solution
 *
 * Be careful about the edge cases.
 * *The requirement of the method.
 *
 */
public class StrStr {

    public String strStr(String haystack, String needle) {
        // Start typing your Java solution below
        // DO NOT write main() function

        if (needle.equals("")) return haystack;

        if (haystack.equals("") || haystack.length() < needle.length()) return null;



        char[] haystackArray = haystack.toCharArray();
        char[] needleArray = needle.toCharArray();



        for (int i = 0; i < haystackArray.length - needleArray.length + 1;) { // covering all cases

            if (haystackArray[i] == needleArray[0]) {

                int potential = i;
                boolean found = true;
                for (int j = 0; j < needleArray.length; j++) {
                    if (haystackArray[i++] != needleArray[j]) {
                        found = false;
                        break;
                    }
                }

                if (found) return haystack.substring(potential);

                i = potential + 1; // back to the next one


            } else
                i++;
        }
        return null;
    }

    public static void main(String[] args) {
        StrStr solution = new StrStr();
        System.out.println(solution.strStr("hahaha, it is good!", "is"));
    }

}
