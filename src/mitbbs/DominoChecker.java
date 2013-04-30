package mitbbs;

import sun.misc.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: zhuang
 * Date: 4/30/13
 * Time: 2:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class DominoChecker {

    private static final int LENGTH = 10;

    private int[] aux = new int[LENGTH];

    public DominoChecker() {

         initAux();

    }

    private void initAux() {
        for (int i = 0; i < aux.length; i++) {
            aux[i] = -1;
        }
    }

    public boolean addBox(int[] a) {

        if (!isValid(a)) throw new IllegalArgumentException();

        boolean same = true;

        for (int i = 0; i < a.length / 2; i = i + 2) {
            int current = a[i];
            int next = a[i + 1];

            if (next != aux[current] || current != aux[next]) {
                same = false;
                break;
            }
        }

        if (!same) {
            initAux();

            for (int i = 0; i < a.length / 2; i = i + 2) {
                int current = a[i];
                int next = a[i + 1];
                aux[current] = next;
                aux[next] = current;
            }
        }

        //Math.random()

        return same;
    }

    private boolean isValid(int[] a) {
        if (a.length != 10)
            return false;
        for (int v : a) {
            if (v < 0 || v > 9)
                return false;
        }
        return true;

    }

    public static void main(String[] args) {
        DominoChecker dc = new DominoChecker();
        int[] a1 = {1, 1, 2, 2, 3, 4, 7, 8, 5, 6};
        int[] a2 = {1, 1, 2, 2, 3, 4, 7, 8, 5, 6};
        int[] a3 = {1, 4, 1, 2, 3, 4, 7, 8, 5, 6};
        int[] a4 = {1, 4, 1, 2, 3, 4, 7, 8, 5, 6};
        System.out.println(dc.addBox(a1));
        System.out.println(dc.addBox(a2));
        System.out.println(dc.addBox(a3));
        System.out.println(dc.addBox(a4));
    }


}
