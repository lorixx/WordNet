import java.util.ArrayList;

//lib for matrix http://math.nist.gov/javanumerics/jama/
import Jama.*;

public class TOMTree {

	/**
	 * Dictionary storing all the vector information
	 */
	Dictionary dictionary;

	/**
	 * Root node for the tree
	 */
	public Node root;

	/**
	 * Threshold for computing modules/atoms
	 */
	private double threshold = 0;

	static int number = 0;

	/**
	 * Constructor
	 *
	 * @param delta
	 * @param dictionary
	 */
	public TOMTree(double delta, Dictionary dictionary) {
		threshold = delta * delta * dictionary.Dim;
		this.dictionary = dictionary;

		ArrayList<Node> nodes = new ArrayList<Node>(); // tom atom lists

		for (int i = 0; i<dictionary.dicSize; i++) {
			Node newNode = new Node(dictionary.Dic.get(i)); // create a new node, but without any parent
			//newNode.curr_idx = i;
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

						Node newNode = createMolecule(firstNode, secondNode);
						nodes.remove(firstNode); // remove old node from the calculating tree
						nodes.remove(secondNode); // remove old node from the calculating tree
						//newNode.curr_idx = nodes.size()+1;
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


}
