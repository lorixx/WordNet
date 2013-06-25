package practice;

public class Box {
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
            } else  {  // (width == length)
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