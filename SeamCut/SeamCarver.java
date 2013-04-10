import java.awt.*;

public class SeamCarver {

    private Picture pic;

    private static final int EDGE_ENERGY = 195075;

    private double[][] energies;

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

        for (int row = 0; row < height(); row++) {
            for (int column = 0; column < width(); column++) {
                energies[column][row] = energy(column, row);
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

        int bestRow = 0;
        int minEnergies = 0;

        for (int row = 0; row < height(); row++) {

            int totalEnergies = 0;
            for (int column = 0; column < width(); column++) {

            }
        }


        return rows;
    }

    public int[] findVerticalSeam()              // sequence of indices for vertical seam
    {
        int[] columns = new int[height()];



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