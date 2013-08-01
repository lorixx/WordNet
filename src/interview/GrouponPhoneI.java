package interview;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/30/13
 * Time: 8:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class GrouponPhoneI {

    //>> Write a function to reverse a string?

    public char[] reverse(String str) {
        // str = "";

        char[] chars = str.toCharArray(); //[]

        int start = 0;
        int end = chars.length - 1; // -1

        char temp;
        while (end > start) {

            temp = chars[end];
            chars[end] = chars[start];
            chars[start] = temp;

            start++;
            end--;
        }

        return chars; //[]
    }
//
//    >> Find all multiple occurences of a element in an array.
//            >> example input.: [ 1, 2, 2, 4 , 2, 6, 6, 1 ]
//            >> ouput:
//            >> 1
//            >> 2
//            >> 6
//
//            >> [1,>>3<<, 2, 2, 3]
//            >> 3
//            >> 2
//
//            >> 2
//            >> 3
//
//    public void findAllDuplicates(int[] array) {
//
//        if (array == null || array.length == 0) return;
//
//        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
//
//
//        for (int i : array) {
//
//            if (map.contains(i)) {
//                >            if (map.get(i) == 1) {
//                    >                System.out.println(i);
//                    >            }
//                map.set(i, map.get(i) + 1); // accumulate the occurrence
//            } else {
//
//                map.set(i, 1); // first time see the element
//            }
//
//        }
//
//        >    for (int i : array) {
//            >
//            >       if (map.get(i) == 1) {
//                >            System.out.println(i);
//                >       }
//            >
//            >    }
//
//    }
//
//    NSArray *coolArray = @[@(1), @(2)];
//
//
//    for (NSNumber *number in coolArray) {
//
//
//
//    }


}
