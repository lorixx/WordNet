import java.awt.*;

public class SeamCarver {

    private Picture pic;

    private static final int EDGE_ENERGY = 195075;

    private double[][] energies;

    private double[][] distTo; // calculate distTo for all, might from top-down or left-right

    private int[][] edgeTo; // calculate edgeTo either from top-down or left-right

    private int[][] marked; // 0: unmarked, 1: temporarily marked, 2: permanently marked

    private Node [][] nodes;

    public SeamCarver(Picture picture) {
        pic = picture;

        energies = new double[pic.width()][pic.height()];
        marked = new int[pic.width()][pic.height()];
        edgeTo = new int[pic.width()][pic.height()];
        distTo = new double[pic.width()][pic.height()];

        // init the node array
        nodes = new Node[pic.width()][pic.height()];
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                nodes[j][i] = new Node(j, i);
            }
        }

        //prepareForMode(0); //set up with horizontal first
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

    public int[] findHorizontalSeam()            // sequence of indices for horizontal seam
    {
        prepareForMode(0); //prepare for horizontal
        int[] rows = new int[width()];

        // this is a left-right topological sort
        Stack<Node> results = this.getTopologicalOrder(0);
        for (Node node : results) {
            horizontalRelax(node);
        }

        double min = distTo[width() - 1][0];
        int index = 0;
        for (int i = 0; i < height(); i++) {
            if (min > distTo[width() - 1][i]) {
                min = distTo[width() - 1][i];
                index = i;
            }
        }

        rows[width() - 1] = index;

        for (int j = width() - 2; j >= 0; j--) {
            rows[j] = edgeTo[j + 1][index];
            index = edgeTo[j + 1][index];
        }

        return rows;


    }

    public int[] findVerticalSeam()              // sequence of indices for vertical seam
    {
        prepareForMode(1); //prepare for vertical
        int[] columns = new int[height()];

        //this is top-down topological sort
        Stack<Node> results = this.getTopologicalOrder(1);

        for (Node node : results) {
            verticalRelax(node);
        }

        double min = distTo[0][height() - 1];
        int index = 0;
        for (int i = 0; i < width(); i++) {
            if (min > distTo[i][height() - 1]) {
                min = distTo[i][height() - 1];
                index = i;
            }
        }

        columns[height() - 1] = index;

        for (int j = height() - 2; j >= 0; j--) {
            columns[j] = edgeTo[index][j + 1];
            index = edgeTo[index][j + 1];
        }

        return columns;
    }

    public void removeHorizontalSeam(int[] a)   // remove horizontal seam from picture
    {
        if (width() <= 1 || height() <= 1) {
            throw new IllegalArgumentException();
        }

        if (a.length <= 0 || a.length > width()) {
            throw new IllegalArgumentException();
        }

        if (a.length > 1) {
            for (int i = 0; i < a.length - 1; i++) {
                if (Math.abs(a[i] - a[i + 1]) > 1) {
                    throw new IllegalArgumentException();
                }
            }
        }

        Picture picture = new Picture(width(), height() - 1);

        for (int i = 0; i < width(); i++) {
            for (int j = 0, k = 0; j < height(); j++) {
                if (j != a[i]) {
                    picture.set(i, k, pic.get(i, j));
                    k++;
                }
            }
        }

        this.pic = picture;
    }

    public void removeVerticalSeam(int[] a)     // remove vertical seam from picture
    {
        if (width() <= 1 || height() <= 1) {
            throw new IllegalArgumentException();
        }

        if (a.length <= 0 || a.length > height()) {
            throw new IllegalArgumentException();
        }

        if (a.length > 1) {
            for (int i = 0; i < a.length - 1; i++) {
                if (Math.abs(a[i] - a[i + 1]) > 1) {
                    throw new IllegalArgumentException();
                }
            }
        }

        Picture picture = new Picture(width() - 1, height());

        for (int i = 0; i < height(); i++) {
            for (int j = 0, k = 0; j < width(); j++) {
                if (j != a[i]) {
                    picture.set(k, i, pic.get(j, i));
                    k++;
                }
            }
        }

        this.pic = picture;
    }

    private class Node {
        int x;
        int y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private Stack<Node> getTopologicalOrder(int mode) {

        Stack<Node> results = new Stack<Node>();
        for (int x = 0; x < pic.width(); x++) {
            for (int y = 0; y < pic.height(); y++) {
                visit(x, y, results, mode);
            }
        }
        return results;
    }

    // not efficient yet since we create new nodes
    private void visit(int x, int y, Stack<Node> results, int mode) {

        if (marked[x][y] == 1) {
            StdOut.println("This is not a DAG.");
        } else if (marked[x][y] == 0) {
            marked[x][y] = 1;

            if (mode == 0) {
                if (y == height() - 1 || x == width() - 1) {
                    // do nothing since we are already at the bottom

                } else if (y == 0) {
                    visit(x + 1, y, results, mode);
                    visit(x + 1, y + 1, results, mode);

                } else {
                    visit(x + 1, y - 1, results, mode);
                    visit(x + 1, y, results, mode);
                    visit(x + 1, y + 1, results, mode);
                }

            } else if (mode == 1) { // vertical topological sort order
                //visit all
                if (y == height() - 1 || x == width() - 1) {
                    // do nothing since we are already at the bottom

                } else if (x == 0) {
                    visit(x, y + 1, results, mode);
                    visit(x + 1, y + 1, results, mode);

                } else {
                    visit(x - 1, y + 1, results, mode);
                    visit(x, y + 1, results, mode);
                    visit(x + 1, y + 1, results, mode);
                }
            }

            marked[x][y] = 2;
            results.push(nodes[x][y]);
        }
    }

    // set up the data for searching horizontal or vertical
    // if mode == 0, then do horizontal
    // else if mode == 1, do vertical
    private void prepareForMode(int mode) {

        for (int x = 0; x < pic.width(); x++) {
            for (int y = 0; y < pic.height(); y++) {
                energies[x][y] = energy(x, y);
                marked[x][y] = 0;
                edgeTo[x][y] = -1;
                distTo[x][y] = Double.POSITIVE_INFINITY;

                if (mode == 0) {  //set left most edge
                    if (x == 0) {
                        distTo[x][y] = EDGE_ENERGY;
                        edgeTo[x][y] = y;
                    }
                } else if (mode == 1) { //set top most edge
                    if (y == 0) {
                        distTo[x][y] = EDGE_ENERGY;
                        edgeTo[x][y] = x;
                    }
                }
            }
        }
    }

    private int calculateEnergyForTwoColor(Color a, Color b) {
        int red = (int) Math.pow(a.getRed() - b.getRed(), 2);
        int blue = (int) Math.pow(a.getBlue() - b.getBlue(), 2);
        int green = (int) Math.pow(a.getGreen() - b.getGreen(), 2);

        return red + blue + green;
    }

    private void verticalRelax(Node n) {
        int x = n.x;
        int y = n.y;
        if (y == height() - 1 || x == width() - 1) {
            // do nothing since we are already at the bottom

        } else if (x == 0) {
            // handle two edges
            if (distTo[x][y + 1] > distTo[x][y] + energies[x][y + 1]) {
                distTo[x][y + 1] = distTo[x][y] + energies[x][y + 1];
                edgeTo[x][y + 1] = x; //set the edge to for its end
            }

            if (distTo[x + 1][y + 1] > distTo[x][y] + energies[x + 1][y + 1]) {
                distTo[x + 1][y + 1] = distTo[x][y] + energies[x + 1][y + 1];
                edgeTo[x + 1][y + 1] = x; //set the edge to for its end
            }

        } else {
            // handle three edges
            if (distTo[x - 1][y + 1] > distTo[x][y] + energies[x - 1][y + 1]) {
                distTo[x - 1][y + 1] = distTo[x][y] + energies[x - 1][y + 1];
                edgeTo[x - 1][y + 1] = x; //set the edge to for its end
            }

            if (distTo[x][y + 1] > distTo[x][y] + energies[x][y + 1]) {
                distTo[x][y + 1] = distTo[x][y] + energies[x][y + 1];
                edgeTo[x][y + 1] = x; //set the edge to for its end
            }

            if (distTo[x + 1][y + 1] > distTo[x][y] + energies[x + 1][y + 1]) {
                distTo[x + 1][y + 1] = distTo[x][y] + energies[x + 1][y + 1];
                edgeTo[x + 1][y + 1] = x; //set the edge to for its end
            }
        }
    }

    private void horizontalRelax(Node n) {
        int x = n.x;
        int y = n.y;
        if (y == height() - 1 || x == width() - 1) {
            // do nothing since we are already at the bottom

        } else if (y == 0) {
            // handle two edges
            if (distTo[x + 1][y] > distTo[x][y] + energies[x + 1][y]) {
                distTo[x + 1][y] = distTo[x][y] + energies[x + 1][y];
                edgeTo[x + 1][y] = y; //set the edge to for its end
            }

            if (distTo[x + 1][y + 1] > distTo[x][y] + energies[x + 1][y + 1]) {
                distTo[x + 1][y + 1] = distTo[x][y] + energies[x + 1][y + 1];
                edgeTo[x + 1][y + 1] = y; //set the edge to for its end
            }

        } else {
            // handle three edges
            if (distTo[x + 1][y - 1] > distTo[x][y] + energies[x + 1][y - 1]) {
                distTo[x + 1][y - 1] = distTo[x][y] + energies[x + 1][y - 1];
                edgeTo[x + 1][y - 1] = y; //set the edge to for its end
            }

            if (distTo[x + 1][y] > distTo[x][y] + energies[x + 1][y]) {
                distTo[x + 1][y] = distTo[x][y] + energies[x + 1][y];
                edgeTo[x + 1][y] = y; //set the edge to for its end
            }

            if (distTo[x + 1][y + 1] > distTo[x][y] + energies[x + 1][y + 1]) {
                distTo[x + 1][y + 1] = distTo[x][y] + energies[x + 1][y + 1];
                edgeTo[x + 1][y + 1] = y; //set the edge to for its end
            }
        }
    }

    public static void main(String[] args) {

        Picture inputImg = new Picture(args[0]);
        System.out.printf("image is %d pixels wide by %d pixels high.\n", inputImg.width(), inputImg.height());

        SeamCarver sc = new SeamCarver(inputImg);

        sc.findVerticalSeam();
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