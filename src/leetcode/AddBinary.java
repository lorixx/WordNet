package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/23/13
 * Time: 12:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class AddBinary {

    public String addBinary(String a, String b) {


        String longer, shorter;
        if (a.length() > b.length()) {
            longer = a; shorter = b;
        } else {
            longer = b; shorter = a;
        }

        int longerEnd = longer.length() - 1, shorterEnd = shorter.length() - 1;
        int sign = 0;
        StringBuilder sb = new StringBuilder();
        int current;
        while (longerEnd >= 0) {
            if (shorterEnd >= 0)
               current  = longer.charAt(longerEnd) - '0' + shorter.charAt(shorterEnd) - '0' + sign;
            else
                current = longer.charAt(longerEnd) - '0' + sign;


            sign = current / 2;
            if (current % 2 == 1)
                sb.append('1');
            else
                sb.append('0');
            longerEnd--;
            shorterEnd--;
        }

        if (sign == 1)
            sb.append('1');

        sb.reverse();
        return sb.toString();

    }

    public static void main(String[] args) {
        AddBinary addBinary = new AddBinary();

        System.out.println(addBinary.addBinary("10", "11"));
    }
}