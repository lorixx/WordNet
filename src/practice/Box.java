package practice;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 6/25/13
 * Time: 12:53 AM
 * To change this template use File | Settings | File Templates.
 */

public class Box {
    int length;
    int width;
    int height;

    public Box(int l, int w, int h) {
        length = l;
        width = w;
        height = h;
    }

    public ArrayList<ArrayList<Integer>> permutations() {

        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

        if (length == width && length == height) {    // only one
            ArrayList<Integer> first = new ArrayList<Integer>();
            first.add(length); first.add(width);
            result.add(first);

        } else if (length == width) {

            ArrayList<Integer> first = new ArrayList<Integer>();
            first.add(length); first.add(width);

            ArrayList<Integer> second = new ArrayList<Integer>();
            second.add(length); second.add(height);

            ArrayList<Integer> third = new ArrayList<Integer>();
            third.add(height); third.add(length);

        }
        // todo


        return result;


    }
}