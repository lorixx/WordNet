package pie;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 5/5/13
 * Time: 6:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class Combinations {
    /**
     * This is example code from PIE, can't really understand how it works.
     */
    private StringBuilder out = new StringBuilder();
    private final String in;

    public Combinations(final String str) {in = str;}

    public void combine() {
        combine(0);
    }

    private void combine(int start) {
        for (int i = start; i < in.length(); i++) {
            out.append(in.charAt(i));
            System.out.println(out);
            if (i < in.length())
                combine(i + 1);
            out.setLength(out.length() - 1);
        }
    }

    public static void main(String[] args) {
        Combinations cb = new Combinations("wxyz");
        cb.combine();
    }
}
