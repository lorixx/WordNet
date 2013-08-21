package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/14/13
 * Time: 1:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class BigIntegerMultiply {


    public static String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) return "0";


        String longerString;
        String shorterString;
        if (num1.length() > num2.length()) {
            longerString = num1;
            shorterString = num2;
        } else {
            longerString = num2;
            shorterString = num1;
        }

        int[] result = new int[longerString.length() + shorterString.length()];

        int[] longerArray = new int[longerString.length()];
        int[] shorterArray = new int[shorterString.length()];

        for (int i = 0; i < longerString.length(); i++)
            longerArray[i] = longerString.charAt(i) - '0';

        for (int j = 0; j < shorterString.length(); j++)
            shorterArray[j] = shorterString.charAt(j) - '0';

        for (int i = longerArray.length - 1, r = result.length - 1; i >= 0; i--, r--) {

            for (int j = shorterArray.length - 1, k = r; j >= 0; j--, k--) {
                result[k] += longerArray[i] * shorterArray[j];
                int tmp = result[k] / 10;
                if (tmp > 0) {
                    result[k - 1] += tmp;  // this is the addOn for the next compute
                    result[k] -= tmp * 10;
                }
            }
        }

        StringBuilder sb = new StringBuilder();

        if (result[0] != 0) sb.append(result[0]);  // important, since sometimes there is no element at the first position

        for (int i = 1; i < result.length; i++)
            sb.append(result[i]);

        return sb.toString();

    }


    public static void main(String[] args) {
        System.out.println(BigIntegerMultiply.multiply("123", "456"));
    }
}
