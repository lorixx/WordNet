package practice;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BoxTower {


    public int maxHeight(ArrayList<Box> boxes) {
        return 0;
    }



    public static void main(String[] args) {

        BoxTower solution = new BoxTower();
        try {
            ArrayList<Box> boxes = new ArrayList<Box>();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line = br.readLine();
            int N = Integer.parseInt(line);
            for (int i = 0; i < N; i++) {
                line = br.readLine();
                String[] result = line.split(" ");

                boxes.add(new Box(Integer.parseInt(result[0]), Integer.parseInt(result[1]), Integer.parseInt(result[2])));

                System.out.println(solution.maxHeight(boxes));

            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
