package practice;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 9/2/13
 * Time: 7:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReverseWords {

    public String reverseWords(String s) {

        char[] chars = s.toCharArray();

        int start = 0; int end = chars.length - 1;
        while (start < end) {
            swap(chars, start, end);
            start++; end--;
        }

        int newStart = -1; int newEnd = -1;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ' ') {
                if (newStart == -1) continue;
                else {
                    //swap the word
                    while (newStart < newEnd) {
                        swap(chars, newStart++, newEnd--);
                    }
                    newStart = -1;
                    newEnd = -1;
                }
            } else {
                if (newStart == -1) newStart = newEnd = i;
                else newEnd++;
            }
        }

        // handle the last word
        if (newStart != -1) {
            while (newStart < newEnd) {
                swap(chars, newStart++, newEnd--);
            }
        }

        String result = new String(chars);
        return result;

    }

    private void swap(char[] chars, int start, int end) {
        char temp = chars[start];
        chars[start] = chars[end];
        chars[end] = temp;
    }

    public static void main(String[] args) {
        ReverseWords rw = new ReverseWords();
        System.out.println(rw.reverseWords("This .  is   not a good  time to fly."));
    }
}
