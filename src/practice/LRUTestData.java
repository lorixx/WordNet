package practice;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 6/22/13
 * Time: 2:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class LRUTestData {

    public static void main(String[] args) {

        try {
            PrintWriter out = new PrintWriter("testdata.txt");
            int N = 1000000;
            out.println(N);
            Random randomGenerator = new Random(2324);
            LinkedList<String> keys = new LinkedList<String>();
            keys.addLast("aaa");
            for (int i = 0; i < N; i++) {

                int random = randomGenerator.nextInt(5);

                switch (random) {
                    case 0:
                        out.println("BOUNDS " + randomGenerator.nextInt(99999));
                        break;
                    case 1:
                        if (keys.size() == 0) break;
                        out.println("GET " + keys.getLast());
                        break;
                    case 2:
                        if (keys.size() == 0) break;

                        out.println("PEEK " + keys.getLast());
                        break;
                    case 3:
                        String key = Integer.toString(randomGenerator.nextInt(9876433));
                        String value = Integer.toString(randomGenerator.nextInt(3823474));
                        keys.addLast(key);

                        out.println("SET " + key + " " + value);
                        break;

                    case 4:
                        out.println("DUMP");
                        break;
                }



            }
            //out.println("BOUNDS 34");
            out.flush();
            out.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
