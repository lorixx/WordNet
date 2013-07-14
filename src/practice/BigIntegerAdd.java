package practice;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/14/13
 * Time: 12:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class BigIntegerAdd {

    public static String add(String a, String b) {
        String longer;
        String shorter;

        if (a.length() > b.length()) {
            longer = a; shorter = b;
        } else {
            longer = b; shorter = a;
        }

        int[] result = new int[longer.length() + 1];
        int[] firstIntArray = new int[longer.length()];
        int[] secondIntArray = new int[shorter.length()];

        for (int i = 0; i < longer.length(); i++)
            firstIntArray[i] = longer.charAt(i) - '0';

        for (int j = 0; j < shorter.length(); j++)
            secondIntArray[j] = shorter.charAt(j) - '0';

        int extra = 0;

        int longerPointer = longer.length() - 1;
        int shorterPointer = shorter.length() - 1;
        for (int i = result.length - 1; i >= 0; i--) {

            if (longerPointer >= 0 && shorterPointer >= 0)
                result[i] = firstIntArray[longerPointer--] + secondIntArray[shorterPointer--] + extra;
            else if (longerPointer >= 0)
                result[i] = firstIntArray[longerPointer--] + extra;
            else
                result[i] = extra;

            extra = 0;
            if (result[i] > 9) {
                result[i] -= 10;
                extra = 1; // waiting for the next add
            }
        }

        StringBuilder sb = new StringBuilder();

        if (result[0] != 0) sb.append(result[0]);

        for (int i = 1; i < result.length; i++)
            sb.append(result[i]);


        return sb.toString();


    }

    public static void main(String[] args) {
       System.out.println(BigIntegerAdd.add("11111111", "88888888"));
    }


}
