package ctci;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 8/2/13
 * Time: 12:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class CompressString {

    public String compress(String str) {

        if (str == null || str.trim().equals("")) return str;

        char prev = str.charAt(0);
        StringBuilder sb = new StringBuilder();
        int currentCount = 1; //count for the repeating characters

        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) != prev) {
                sb.append(prev);
                sb.append(currentCount);
                currentCount = 1; // update
                prev = str.charAt(i); // update
            } else
                currentCount++;
        }

        sb.append(prev);
        sb.append(currentCount);

        if (sb.length() >= str.length()) return str;
        else return sb.toString();


    }


    public static void main(String[] args) {
        CompressString cs = new CompressString();
        System.out.println(cs.compress("aaaabbcccdd"));
    }
}
