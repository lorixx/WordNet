import java.awt.*;

public class SeamCarver {

    private Picture pic;

    private static final int EDGE_ENERGY = 195075;

    private double[][] energies;

    private double[][] distTo; // calculate distTo for all, might from top-down or left-right

    private int[][] edgeTo; // calculate edgeTo either from top-down or left-right

    private int[][] marked; // 0: unmarked, 1: temporarily marked, 2: permanently marked


    private class Node {
        int x;
        int y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    //top-down first
    private Stack<Node> getTopologicalOrder() {

        Queue<Node> nodes = new Queue<Node>();
        Stack<Node> results = new Stack<Node>();
        for (int x = 0; x < pic.width(); x++) {
            for (int y = 0; y < pic.height(); y++) {
                nodes.enqueue(new Node(x, y));
            }
        }

        while (!nodes.isEmpty()) {
            Node currentNode = nodes.dequeue();
            visit(currentNode.x, currentNode.y, results);
        }

        return results;
    }


    // not efficient yet since we create new nodes
    private void visit(int x, int y, Stack<Node> results) {

        if (marked[x][y] == 1) {
            StdOut.println("This is not a DAG.");
            return;
        } else if (marked[x][y] == 0) {
            marked[x][y] = 1;


            //visit all
            if (y == height() - 1 || x == width() - 1) {
                // do nothing since we are already at the bottom

            } else if (x == 0) {
                visit(x, y + 1, results);
                visit(x + 1, y + 1, results);

            } else {
                visit(x - 1, y + 1, results);
                visit(x, y + 1, results);
                visit(x + 1, y + 1, results);
            }

            marked[x][y] = 2;
            results.push(new Node(x, y));
        }
    }


    public SeamCarver(Picture picture) {
        pic = picture;

        energies = new double[pic.width()][pic.height()];
        marked = new int [pic.width()][pic.height()];
        edgeTo = new int [pic.width()][pic.height()];
        distTo = new double [pic.width()][pic.height()];

        for (int x = 0; x < pic.width(); x++) {
            for (int y = 0; y < pic.height(); y++) {
                energies[x][y] = energy(x, y);
                marked[x][y] = 0;
                edgeTo[x][y] = 0;
                distTo[x][y] = Double.POSITIVE_INFINITY;
            }
        }

    }

    public Picture picture()                       // current picture
    {
        return pic;
    }

    public int width()                         // width of current picture
    {
        return pic.width();

    }

    public int height()                        // height of current picture
    {
        return pic.height();

    }

    public double energy(int x, int y)            // energy of pixel at column x and row y
    {
        if (x < 0 || y < 0 || x > width() - 1 || y > height() - 1) {
            throw new IndexOutOfBoundsException();
        }

        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1) {
            return EDGE_ENERGY;
        }

        Color leftColor = pic.get(x - 1, y);
        Color rightColor = pic.get(x + 1, y);
        Color topColor = pic.get(x, y - 1);
        Color bottomColor = pic.get(x, y + 1);

        return calculateEnergyForTwoColor(leftColor, rightColor) + calculateEnergyForTwoColor(topColor, bottomColor);
    }

    private int calculateEnergyForTwoColor(Color a, Color b) {
        int red = (int) Math.pow(a.getRed() - b.getRed(), 2);
        int blue = (int) Math.pow(a.getBlue() - b.getBlue(), 2);
        int green = (int) Math.pow(a.getGreen() - b.getGreen(), 2);

        return red + blue + green;
    }

    public int[] findHorizontalSeam()            // sequence of indices for horizontal seam
    {


        Stack<Node> results = this.getTopologicalOrder();

        for (Node i : results) {
            StdOut.printf("(%s, %s)\n", i.x, i.y);
        }

        return null;

    }

    public int[] findVerticalSeam()              // sequence of indices for vertical seam
    {
        return null;

    }

    public void removeHorizontalSeam(int[] a)   // remove horizontal seam from picture
    {

    }

    public void removeVerticalSeam(int[] a)     // remove vertical seam from picture
    {

    }

    public static void main(String[] args) {
        StdOut.println("haha");
        Picture inputImg = new Picture(args[0]);
        System.out.printf("image is %d pixels wide by %d pixels high.\n", inputImg.width(), inputImg.height());

        SeamCarver sc = new SeamCarver(inputImg);

        sc.findHorizontalSeam();

        System.out.printf("Printing energy calculated for each pixel.\n");

        for (int j = 0; j < sc.height(); j++) {
            for (int i = 0; i < sc.width(); i++) {
                System.out.printf("%9.0f ", sc.energy(i, j));
            }

            System.out.println();
        }
    }


}