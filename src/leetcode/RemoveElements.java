package leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/22/13
 * Time: 10:42 PM
 * To change this template use File | Settings | File Templates.
 */

// Two pointers variation

public class RemoveElements {
    public int removeElement(int[] A, int elem) {

        int len = A.length;

        for (int i = 0; i < len;) {
            if (A[i] == elem)
                A[i] = A[--len];
            else
                i++;
        }

        return len;


    }
}