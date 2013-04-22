import java.util.ArrayList;
//lib for matrix http://math.nist.gov/javanumerics/jama/
import Jama.*;

public class TOMTree {

    /**
     * Dictionary storing all the vector information
     */
	private TomDictionary tomDictionary;

    /**
     * Root node for the tree
     */
    private Node root;

    /**
     * Threshold for computing modules/atoms
     */
	private double threshold = 0;

    private static int number = 0;

    /**
     * Constructor
     *
     * @param delta
     * @param dictionary
     */
    public TOMTree(double delta, TomDictionary dictionary) {
        threshold = delta * delta * dictionary.dim;
        tomDictionary = dictionary;

        ArrayList<Node> nodes = new ArrayList<Node>();

        for (double[] array : tomDictionary.vectorData) {
            Node newNode = new Node(array); // create a new node, but without any parent
            newNode.isLeaf = true; // set all these nodes to be as leaf
            nodes.add(newNode);
        }

        if (!build(nodes))
            System.out.println("We are not able to build the tree");

    }

    /**
     * Method used to build the tree
     *
     * @param nodes
     * @return
     */
    private boolean build(ArrayList<Node> nodes) {

        outeloop:
        for (int i = 0; i < nodes.size(); i++) {

            for (int j = i + 1; j < nodes.size(); j++) {

                Node firstNode = nodes.get(i);
                Node secondNode = nodes.get(j);
                double product = product(firstNode.atom, secondNode.atom);
                if (1 - product < threshold) {
                    //System.out.print(i + "   " + j + "\n");

                    Node newNode = createMolecule(firstNode, secondNode);
                    nodes.remove(firstNode); // remove old node from the calculating tree
                    nodes.remove(secondNode); // remove old node from the calculating tree
                    nodes.add(newNode); // add the new node into the calculating tree

                    if (build(nodes))  // recursive call
                        break outeloop; // this should break all iteration both for inner loop or outer loop
                }
            }
        }

        // If we reach here, we have no other atoms to combine,

        if (this.root == null) {
            Node rootNode = new Node(null);
            rootNode.children.addAll(nodes);
            this.root = rootNode; // assign to global
        }

        return true;
    }

    private Node createMolecule(Node a, Node b) {
        double[] vectorData = createVector(a.atom, b.atom);

        Node molecule = new Node(vectorData);
        molecule.children.add(a);
        molecule.children.add(b);

        return molecule;
    }

    /**
     * Utility method, product between two arrays
     *
     * @param t1
     * @param t2
     * @return
     */
	public double product(double[] t1, double[] t2){
		double result = 0;
		for(int i = 0; i < t1.length; i++)
			result += t1[i] * t2[i];
		return result;
	}

    /**
     * This method is used to create the new vector data based on two vector
     *
     * @param firstVector
     * @param secondVector
     * @return
     */
    double[] createVector(double[] firstVector, double[] secondVector) {
    	int dim = firstVector.length;
	double[][] array = {firstVector, secondVector};
	Matrix M = new Matrix(array);
	EigenvalueDecomposition e = M.transpose().times(M).eig();
	double[][] temp = e.getV().getArray();
	
	double[] result = new double[temp.length];
	for(int i = 0; i<dim; i++)
		result[i] = temp[i][dim-1];
	
        return result;
    }
	
	private class Node {
		private int curr_idx; //atom index of this node in TOMDic
		private double[] atom; //the vector info, equals TOMDic.get(curr_idx)
        private Node parent;
		private ArrayList<Node> children; //list of children atom index in TOMDic
		public boolean isLeaf = false;
        private int key;
		
		//constructor
		public Node(double[] vector){
            children = new ArrayList<Node>();
			isLeaf = false;
            parent = null;
			atom = vector;
            key = number++;
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

    /**
     * Level print the tree
     *
     * @param nodes
     */
    public void printTree(ArrayList<Node> nodes) {
        //System.out.print(root.key);

        ArrayList<Node> nextLevel = new ArrayList<Node>();
        for (Node node : nodes) {
            System.out.print(node.key);
            if (node.isLeaf) System.out.print("(isLeaf)");
            System.out.print("  ");

            nextLevel.addAll(node.children);
        }
        System.out.println();
        if (nextLevel.size()!= 0)
            printTree(nextLevel);
    }

    /**
     * Test client
     *
     * @param args
     */
    public static void main(String[] args) {

        TOMTree tomTree = new TOMTree(0.1, new TomDictionary(args[0]));

        if (tomTree.root != null)
            System.out.println("We have a successful tree built!");

        //traverse the tree

        ArrayList<Node> rootArray = new ArrayList<Node>();
        rootArray.add(tomTree.root);
        tomTree.printTree(rootArray);


    }
	

}
