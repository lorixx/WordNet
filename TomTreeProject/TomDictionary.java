

import java.io.*;
import java.util.*;

public class TomDictionary {
    public final int MAX_SIZE = 512;
    public final double group_delta = 0.1;
    public ArrayList<double[]> vectorData = null;
    public ArrayList<Integer> index = null;
    public int dicSize; // number of atoms in the given TomDictionary
    public int dim; // length of each atom


    /**
     * Constructor
     * @param fileName
     */
    public TomDictionary(String fileName) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            vectorData = new ArrayList<double[]>();
            index = new ArrayList<Integer>();
            String line = br.readLine();
            int count = 0;
            while (line != null) {
                String[] tmp = line.split("\\t");
                double[] dbtmp = new double[tmp.length];
                for (int i = 0; i < tmp.length; i++)
                    dbtmp[i] = Double.parseDouble(tmp[i]);
                vectorData.add(dbtmp);
                index.add(count);
                line = br.readLine();
                count++;
            }
            br.close();
            dicSize = count;

            dim = vectorData.get(0).length;
            System.out.println(count);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        String fileName = args[0];

        TomDictionary dic = new TomDictionary(fileName);

        System.out.println("dic size is " + dic.dicSize);
        System.out.println("dim is " + dic.dim);

//            for (int i : dic.index) {
//                System.out.println(i);
//            }

        for (int i = 0; i < dic.vectorData.size(); i++) {
            double[] currentArray = dic.vectorData.get(i);
            System.out.println("array size is " + currentArray.length);
            for (double value : currentArray) {
                System.out.print(value + "   ");
            }
            System.out.println();

        }


    }


}
