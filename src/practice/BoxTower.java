package practice;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BoxTower {


    public int maxHeight(Box[] boxes) {

        int maxHeightSoFar = 0;
        Box[] maxBoxComb = null;

        ArrayList<ArrayList<Box>> computingBoxes = new ArrayList<ArrayList<Box>>();  // use for for all computing

        for (Box b : boxes) {
            ArrayList<Box> temp = new ArrayList<Box>();
            temp.add(b.boxWithMaxHeight());   // add one box with the max height version
            computingBoxes.add(temp);
        }

        if (boxes.length == 1) {
            return computingBoxes.get(0).get(0).height;
        }


        for (int num = 1; num < boxes.length; num++) {

            for (int i = 0; i < computingBoxes.size(); i++) {  // iterate each list


                ArrayList<Box> currentBoxes = computingBoxes.get(i);
                int originalHeight = accumulateHeight(currentBoxes);
                int localBest = originalHeight;


                ArrayList<Box> localBestBoxes = new ArrayList<Box>(currentBoxes);

                for (int j = 0; j < boxes.length; j++) { // for each other box

                    boolean notValid = false;
                    for (int k = 0; k < currentBoxes.size(); k++) {
                        if (currentBoxes.get(k).index == j) { //index in box should match the index in array
                            notValid = true;
                            break;
                        }
                    }
                    if (notValid) continue;

                    Box testBox = boxes[j];
                    testBox.permutations = testBox.createPermutations();

                    // now start test this box
                    Box currentBox = null;
                    for (int k = 0; k < currentBoxes.size(); k++) {

                        currentBox = currentBoxes.get(k);
                        if (k == 0) {

                            for (Box box : testBox.permutations) {
                                if (box.width <= currentBox.width && box.length <= currentBox.length
                                        && box.height + originalHeight > localBest) {

                                    // now we could put it into local Best
                                    localBest = box.height + originalHeight;
                                    ArrayList<Box> localBestTemp = new ArrayList<Box>(currentBoxes); // from original copy
                                    localBestTemp.add(k, box);
                                    localBestBoxes = localBestTemp; // update the local best boxes array list
                                    computingBoxes.set(i, localBestBoxes);  // update the computing boxes array for next iteration

                                    if (localBest > maxHeightSoFar) {
                                        maxHeightSoFar = localBest;
                                        maxBoxComb = localBestBoxes.toArray(new Box[0]);
                                    }

                                }
                            }
                            continue;
                        } // end of if

                        Box prev = currentBoxes.get(k - 1);
                        for (Box box : testBox.permutations) {
                            if (box.width >= prev.width && box.length >= prev.length && box.width <= currentBox.width
                                    && box.length <= currentBox.length && box.height + originalHeight > localBest) {
                                localBest = box.height + originalHeight;
                                ArrayList<Box> localBestTemp = new ArrayList<Box>(currentBoxes);
                                localBestTemp.add(k, box);
                                localBestBoxes = localBestTemp;
                                computingBoxes.set(i, localBestBoxes);  // update the computing boxes array for next iteration

                                if (localBest > maxHeightSoFar) {
                                    maxHeightSoFar = localBest;
                                    maxBoxComb = localBestBoxes.toArray(new Box[0]);
                                }
                            }
                        }


                        if (k == currentBoxes.size() - 1) {
                            // now need to check the last one
                            for (Box box : testBox.permutations) {
                                if (box.width >= currentBox.width && box.length >= currentBox.length
                                        && box.height + originalHeight > localBest) {
                                    localBest = box.height + originalHeight;
                                    ArrayList<Box> localBestTemp = new ArrayList<Box>(currentBoxes);
                                    localBestTemp.add(box); // add to the last position
                                    localBestBoxes = localBestTemp;
                                    computingBoxes.set(i, localBestBoxes);  // update the computing boxes array for next iteration

                                    if (localBest > maxHeightSoFar) {
                                        maxHeightSoFar = localBest;
                                        maxBoxComb = localBestBoxes.toArray(new Box[0]);
                                    }
                                }
                            }
                        }

                    }

                }

            } // end of outer for loop

        }

        for (Box finalBox : maxBoxComb) {
            System.out.println("index is " + finalBox.index);
            System.out.println("Length  : " + finalBox.length);
            System.out.println("Width   : " + finalBox.width);
            System.out.println("Height  : " + finalBox.height);
            System.out.println("===================================");
        }

        return maxHeightSoFar;
    }

    private int accumulateHeight(ArrayList<Box> boxes) {
        int result = 0;

        for (Box box : boxes) {
            result += box.height;
        }
        return result;
    }

    static class Box {
        int length;
        int width;
        int height;
        int index;

        Box[] permutations;

        public Box(int l, int w, int h, int ind) {
            length = l;
            width = w;
            height = h;
            index = ind;  // used for recognition

            //permutations = createPermutations();
        }

        public Box[] createPermutations() {

            Box[] boxes;
            if (length == width && width == height) {
                boxes = new Box[1];
                boxes[0] = new Box(length, width, height, index);
            } else if (width == height || length == height || width == length) {
                int sameEdge, differentEdge;
                if (width == height) {
                    sameEdge = width;
                    differentEdge = length;
                } else if (length == height) {
                    sameEdge = length;
                    differentEdge = width;
                } else {  // (width == length)
                    sameEdge = width;
                    differentEdge = height;
                }

                boxes = new Box[3];
                boxes[0] = new Box(sameEdge, differentEdge, sameEdge, index);
                boxes[1] = new Box(sameEdge, sameEdge, differentEdge, index);
                boxes[2] = new Box(differentEdge, sameEdge, sameEdge, index);

            } else {
                boxes = new Box[6];
                boxes[0] = new Box(length, width, height, index);
                boxes[1] = new Box(length, height, width, index);
                boxes[2] = new Box(width, length, height, index);
                boxes[3] = new Box(width, height, length, index);
                boxes[4] = new Box(height, width, length, index);
                boxes[5] = new Box(height, length, width, index);
            }

            return boxes;
        }

        public Box boxWithMaxHeight() {
            Box target = this;
            int heightSoFar = 0;
            Box[] permutations = this.createPermutations();
            for (Box box : permutations) {
                if (box.height > heightSoFar) {
                    heightSoFar = box.height;
                    target = box;
                }
            }

            return target;

        }
    }

    public static void main(String[] args) {

//        Box a = new Box(5, 2, 4, 0);
//        Box b = new Box(1, 4, 2, 1);
//        Box c = new Box(4, 4, 2, 2);
        Box a = new Box(1, 3, 1, 0);
        // Box b = new Box(1, 1, 1, 1);
//        Box c = new Box(1, 1, 1, 2);
        Box[] array = new Box[1];
        array[0] = a; //array[1] = b; //array[2] = c;
//
//        Box d = new Box(1, 2, 10, 3);
//        Box[] array = new Box[4];
//        array[0] = a; array[1] = b; array[2] = c; array[3] = d;
//
//
//
        BoxTower solution = new BoxTower();
        System.out.println(solution.maxHeight(array));


        try {
            Box[] boxes;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line = br.readLine();
            int N = Integer.parseInt(line);
            boxes = new Box[N];
            for (int i = 0; i < N; i++) {
                line = br.readLine();
                String[] result = line.split(" ");

                boxes[i] = new Box(Integer.parseInt(result[0]), Integer.parseInt(result[1]), Integer.parseInt(result[2]), i);


            }
            System.out.println(solution.maxHeight(boxes));

        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
