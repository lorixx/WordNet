package basic;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/6/13
 * Time: 9:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class PriorityQueue<Key extends Comparable<Key>> {

    private Key[] objects;
    private int N;

    public PriorityQueue() {
         objects = (Key[]) new Comparable[20];
    }

    public PriorityQueue(int max) {

    }

    public PriorityQueue(Key[] keys) {

    }

    public void add(Key key) {

    }

    public Key max() {
         return null;
    }

    public Key deleteMax() {
         return null;
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return N > 0;
    }


}
