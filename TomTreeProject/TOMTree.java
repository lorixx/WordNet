import java.util.ArrayList;

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
            nodes.add(newNode);
        }

        if (!build(nodes))
            System.out.println("We are not able to build the tree");

    }

    /**
     *
     * @param nodes
     * @return
     */
    private boolean build(ArrayList<Node> nodes) {


        for (int i = 0; i < nodes.size(); i++) {
            for (int j = i + 1; j < nodes.size(); j++) {
                double product = product(nodes.get(i).atom, nodes.get(j).atom);
                if (1 - product < threshold) {
                    Node newNode = createMolecule(nodes.get(i), nodes.get(j));
                    nodes.remove(i); // remove old node from the calculating tree
                    nodes.remove(j); // remove old node from the calculating tree
                    nodes.add(newNode); // add the new node into the calculating tree

                    if (build(nodes))  // recursive call
                        break; // this should break all iteration both for inner loop or outer loop
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
        // Implementation
        // todo:

        return null;
    }
	
	private class Node {
		private int curr_idx; //atom index of this node in TOMDic
		private double[] atom; //the vector info, equals TOMDic.get(curr_idx)
        private Node parent;
		private ArrayList<Node> children; //list of children atom index in TOMDic
		private boolean isLeaf = false;
		
		//constructor
		public Node(double[] vector){
            children = new ArrayList<Node>();
			isLeaf = false;
            parent = null;
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

    /**
     * Test client
     *
     * @param args
     */
    public static void main(String[] args) {

    }
	

}
