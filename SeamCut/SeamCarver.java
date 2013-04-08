import java.awt.*;

public class SeamCarver {

    private Picture pic;

    private static final int EDGE_ENERGY = 195075;

    public SeamCarver(Picture picture) {
        pic = picture;

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
        if (x < 0 || y < 0 || x > width() - 1 || y > height() - 1)
            throw new IndexOutOfBoundsException();

        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1)
            return EDGE_ENERGY;

        Color leftColor = pic.get(x - 1, y);
        Color rightColor = pic.get(x + 1, y);
        Color topColor = pic.get(x, y - 1);
        Color bottomColor = pic.get(x, y + 1);

        return calculateEnergyForTwoColor(leftColor, rightColor) + calculateEnergyForTwoColor(topColor, bottomColor);
    }

    private int calculateEnergyForTwoColor(Color a, Color b) {
        int red = (int)Math.pow(a.getRed() - b.getRed(), 2);
        int blue = (int)Math.pow(a.getBlue() - b.getBlue(), 2);
        int green = (int)Math.pow(a.getGreen() - b.getGreen(), 2);

        return red + blue + green;
    }

    public int[] findHorizontalSeam()            // sequence of indices for horizontal seam
    {
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

        System.out.printf("Printing energy calculated for each pixel.\n");

        for (int j = 0; j < sc.height(); j++)
        {
            for (int i = 0; i < sc.width(); i++)
                System.out.printf("%9.0f ", sc.energy(i, j));

            System.out.println();
        }
    }


}