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
        int[] rows = new int[width()];




        return rows;
    }

    private double min(double a, double b, double c) {
        double min = Math.min(a, b);

        if (min < b)
            min = Math.min(a, c);
        else
            min = Math.min(b, c);
        return min;
    }

    public int[] findVerticalSeam()              // sequence of indices for vertical seam
    {
        int[] columns = new int[height()];

        double[] oldDist = new double[width()];

        for (int row = 0; row < height(); row++) {

            double[] distTo = new double[width()];

            for (int column = 0; column < width(); column++) {

                //visiting each vertex in topological order

                if (row == 0) {
                    distTo[column] = EDGE_ENERGY;
                    parent[column][row] = -1;
                } else {
                    if (column == 0) {
                        // 2 edges
                        distTo[column] = Math.min(oldDist[0], oldDist[1]) + energy(column, row);
                        if (Math.min(oldDist[0], oldDist[1]) < oldDist[1])
                            parent[column][row] = 0;
                        else
                            parent[column][row] = 1;

                    } else if (column == width() - 1) {
                        // 2 edges
                        distTo[column] = Math.min(oldDist[column], oldDist[column - 1]) + energy(column, row);
                        if (Math.min(oldDist[column], oldDist[column - 1]) < oldDist[column - 1])
                            parent[column][row] = column;
                        else
                            parent[column][row] = column - 1;
                    } else {
                        // 3 edges
                        Double left = oldDist[column - 1];
                        Double mid = oldDist[column];
                        Double right = oldDist[column + 1];

                        //double min = min(left, mid, right);


                        double min = Math.min(left, mid);

                        if (min < mid) {
                            min = Math.min(left, right);

                            if (min < right) {
                                //left
                                parent[column][row] = column - 1;
                            } else
                                // right
                                parent[column][row] = column + 1;


                        } else {
                            min = Math.min(mid, right);

                            if (min < right) {
                                //mid
                                parent[column][row] = column;

                            } else
                            // right
                                parent[column][row] = column + 1;
                        }

                        distTo[column] = min + energy(column, row);

                    }
                }
            }

            oldDist = distTo;
            for (double i : oldDist) {
                StdOut.printf("%f  ", i);
            }
            StdOut.println();
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

        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                StdOut.printf("%d  ", parent[j][i]);
            }
            StdOut.println();
        }

        for (int i : columns)
            StdOut.println(i);

        StdOut.println();


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

        sc.findVerticalSeam();
        //sc.findHorizontalSeam();

        System.out.printf("Printing energy calculated for each pixel.\n");

        for (int j = 0; j < sc.height(); j++) {
            for (int i = 0; i < sc.width(); i++) {
                System.out.printf("%9.0f ", sc.energy(i, j));
            }
            System.out.println();
        }
    }
}