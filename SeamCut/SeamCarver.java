import java.awt.*;

public class SeamCarver {

    private Picture pic;

    private static final int EDGE_ENERGY = 195075;

    private double[][] energies;

    private int[][]parent;

    public SeamCarver(Picture picture) {

        setPicture(picture);
    }

    /**
     * Helper method for setting picture
     * It will re-create the energies array
     * @param picture
     */
    private void setPicture(Picture picture) {
        pic = picture;

        energies = new double[pic.width()][pic.height()];
        parent = new int[pic.width()][pic.height()];
        for (int row = 0; row < height(); row++) {
            for (int column = 0; column < width(); column++) {
                energies[column][row] = energy(column, row);
                parent[column][row] = -1;
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

    public int[] findHorizontalSeam()            // sequence of indices for horizontal seam
    {
        Stopwatch sw = new Stopwatch();
        int[] rows = new int[width()];
        double[] oldDist = new double[height()];
        double[] distTo = new double[height()];

        for (int column = 0; column < width(); column++) {

            for (int row = 0; row < height(); row++) {

                horizontalVisit(column, row, distTo, oldDist);

            }

            System.arraycopy(distTo, 0, oldDist, 0, height());
            //oldDist = distTo;
        }

        double minDist = oldDist[0];
        int bestIndex = 0;
        for (int index = 0; index < height(); index++) {
            if (oldDist[index] < minDist) {
                minDist = oldDist[index];
                bestIndex = index;
            }
        }

        rows[width() - 1] = bestIndex;

        for (int j = width() - 2; j >= 0; j--) {
            rows[j] = parent[j + 1][bestIndex];
            bestIndex = parent[j + 1][bestIndex];
        }

//        for (int i : rows)
//            StdOut.printf("%d  ", i);
//
//        StdOut.println();
        System.out.println("Find a horizontal seam takes time: " + sw.elapsedTime() + " seconds.");
        return rows;
    }

    public int[] findVerticalSeam()              // sequence of indices for vertical seam
    {
        Stopwatch sw = new Stopwatch();

        int[] columns = new int[height()];
        double[] oldDist = new double[width()];
        double[] distTo = new double[width()];
        for (int row = 0; row < height(); row++) {



            for (int column = 0; column < width(); column++) {

                //visiting each vertex in topological order
                verticalVisit(column, row, distTo, oldDist);
            }

            System.arraycopy(distTo, 0, oldDist, 0, width());

            //oldDist = distTo;
//            for (double i : oldDist) {
//                StdOut.printf("%f  ", i);
//            }
//            StdOut.println();
        }

        double minDist = oldDist[0];
        int bestIndex = 0;
        for (int index = 0; index < width(); index++) {
            if (oldDist[index] < minDist) {
                minDist = oldDist[index];
                bestIndex = index;
            }
        }

        columns[height() - 1] = bestIndex;

        for (int j = height() - 2; j >= 0; j--) {
            columns[j] = parent[bestIndex][j + 1];
            bestIndex = parent[bestIndex][j + 1];
        }
//
//        for (int i = 0; i < height(); i++) {
//            for (int j = 0; j < width(); j++) {
//                StdOut.printf("%d  ", parent[j][i]);
//            }
//            StdOut.println();
//        }

//        for (int i : columns)
//            StdOut.printf("%d  ", i);
//
//        StdOut.println();

        System.out.println("Find a vertical seam takes time: " + sw.elapsedTime() + " seconds.");

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

        double[][] oldEnergies = energies;
        energies = new double[width()][height()];

        for (int column = 0; column < width(); column++) {
            for (int row = 0; row < height(); row++) {

                // if the adjacent is changed
                if (row == a[column] - 1 || row == a[column]) {
                    energies[column][row] = energy(column, row);
                } else {

                    if (row > a[column]) // if it is passed the changed point
                        energies[column][row] = oldEnergies[column][row + 1];
                    else
                        energies[column][row] = oldEnergies[column][row];
                }
            }
        }


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

        // process the energies table
        double[][] oldEnergies = energies;
        energies = new double[width()][height()];

        for (int row = 0; row < height(); row++) {
            for (int column = 0; column < width(); column++) {

                // if the adjacent is changed
                if (column == a[row] - 1 || column == a[row]) {
                    energies[column][row] = energy(column, row);
                } else {

                    if (column > a[row]) // if it is passed the changed point
                        energies[column][row] = oldEnergies[column + 1][row];
                    else
                        energies[column][row] = oldEnergies[column][row];
                }
            }
        }
    }

    private double min(double a, double b, double c) {
        double min = Math.min(a, b);

        if (min < b)
            min = Math.min(a, c);
        else
            min = Math.min(b, c);
        return min;
    }

    private void horizontalVisit(int column, int row, double[] distTo, double[] oldDist) {

        if (column == 0) {
            distTo[row] = EDGE_ENERGY;
            parent[column][row] = -1;
        } else {

            if (row == 0) {
                // 2 edges
                distTo[row] = Math.min(oldDist[0], oldDist[1]) + energies[column][row];
                if (Math.min(oldDist[0], oldDist[1]) < oldDist[1])
                    parent[column][row] = 0;
                else
                    parent[column][row] = 1;
            } else if (row == height() - 1) {
                // 2 edges
                distTo[row] = Math.min(oldDist[row - 1], oldDist[row]) + energies[column][row];
                if (Math.min(oldDist[row - 1], oldDist[row]) < oldDist[row])
                    parent[column][row] = row - 1;
                else
                    parent[column][row] = row;
            } else {
                // 3 edges
                Double top = oldDist[row - 1];
                Double mid = oldDist[row];
                Double bottom = oldDist[row + 1];

                double min = min(top, mid, bottom);
                distTo[row] = min + energies[column][row];
                if (min == top) {
                    parent[column][row] = row - 1;
                } else if (min == mid) {
                    parent[column][row] = row;
                } else
                    parent[column][row] = row + 1;

            }
        }
    }

    private void verticalVisit(int column, int row, double[] distTo, double[] oldDist) {

        if (row == 0) {
            distTo[column] = EDGE_ENERGY;
            parent[column][row] = -1;
        } else {
            if (column == 0) {
                // 2 edges
                distTo[column] = Math.min(oldDist[0], oldDist[1]) + energies[column][row];
                if (Math.min(oldDist[0], oldDist[1]) < oldDist[1])
                    parent[column][row] = 0;
                else
                    parent[column][row] = 1;

            } else if (column == width() - 1) {
                // 2 edges
                distTo[column] = Math.min(oldDist[column], oldDist[column - 1]) + energies[column][row];
                if (Math.min(oldDist[column], oldDist[column - 1]) < oldDist[column - 1])
                    parent[column][row] = column;
                else
                    parent[column][row] = column - 1;
            } else {
                // 3 edges
                Double left = oldDist[column - 1];
                Double mid = oldDist[column];
                Double right = oldDist[column + 1];

                double min = min(left, mid, right);
                distTo[column] = min + energies[column][row];
                if (min == left) {
                    parent[column][row] = column - 1;
                } else if (min == mid) {
                    parent[column][row] = column;
                } else
                    parent[column][row] = column + 1;

            }
        }
    }


    private int calculateEnergyForTwoColor(Color a, Color b) {
        int red = (int) Math.pow(a.getRed() - b.getRed(), 2);
        int blue = (int) Math.pow(a.getBlue() - b.getBlue(), 2);
        int green = (int) Math.pow(a.getGreen() - b.getGreen(), 2);

        return red + blue + green;
    }



    public static void main(String[] args) {

        Picture inputImg = new Picture(args[0]);
        System.out.printf("image is %d pixels wide by %d pixels high.\n", inputImg.width(), inputImg.height());

        SeamCarver sc = new SeamCarver(inputImg);

        //sc.findVerticalSeam();
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 50; i++)
            sc.findHorizontalSeam();

        System.out.println("Find 50 horizontal seams takes time: " + sw.elapsedTime() + " seconds.");



        System.out.printf("Printing energy calculated for each pixel.\n");
//
//        for (int j = 0; j < sc.height(); j++) {
//            for (int i = 0; i < sc.width(); i++) {
//                System.out.printf("%9.0f ", sc.energy(i, j));
//            }
//            System.out.println();
//        }
    }
}