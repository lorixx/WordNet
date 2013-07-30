package practice;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/30/13
 * Time: 12:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class CalmelCase {

    public String canmelize(String input) {
        StringBuilder sb = new StringBuilder();

        boolean needCanmel = false; //since the first word needs not camel case
        for (int i = 0; i < input.length(); i++) {
            char x = input.charAt(i);
            if ( x >= 'a' && x <= 'z') {
                if (needCanmel) {
                    sb.append(input.substring(i, i + 1).toUpperCase());
                    needCanmel = false;
                } else {
                    sb.append(x);
                }

            } else {
                needCanmel = true;
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        CalmelCase calmelCase = new CalmelCase();
        System.out.println(calmelCase.canmelize("this is a good news"));
    }
}
