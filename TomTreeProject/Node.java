import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: zhuang
 * Date: 4/24/13
 * Time: 11:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class Node {
    public double[] atom; //the vector info, equals TOMDic.get(curr_idx)
    public ArrayList<Node> children; //list of children atom index in TOMDic
    public boolean isLeaf = false;
    public int key;

    //constructor
    public Node(double[] vector){
        children = new ArrayList<Node>();
        isLeaf = false;
        atom = vector;
    }

    public ArrayList<Node> children(){
        return children;
    }

    public double[] value(){
        return atom;
    }

    public boolean isLeaf(){
        return isLeaf;
    }


}
