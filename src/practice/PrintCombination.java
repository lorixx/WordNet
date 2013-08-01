package practice;

/**
 * Created with IntelliJ IDEA.
 * User: zhuang
 * Date: 8/1/13
 * Time: 2:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class PrintCombination {

    /*
    given a string with length t
    for example char str[]="adg?b?dd?g"
    each ? represents either '0' or '1'

    print out all combinations of the string
    */

    public void printAll(String str) {

       dfs(str, 0);

    }

    private void dfs(String str, int start) {
        if (start == str.length())
            System.out.println(str);
        else {

            if (str.charAt(start) == '?') {
                StringBuilder sb = new StringBuilder(str);
                sb.setCharAt(start, '0');
                dfs(sb.toString(), start + 1);

                StringBuilder sb2 = new StringBuilder(str);
                sb2.setCharAt(start, '1');
                dfs(sb2.toString(), start + 1);

            } else {
                dfs(str, start + 1);
            }
        }
    }

    public static void main(String[] args) {

        PrintCombination printCombination = new PrintCombination();
        printCombination.printAll("adg?b?dd?g");


    }
}
