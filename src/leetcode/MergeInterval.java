package leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/11/13
 * Time: 9:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class MergeInterval {

     public class Interval {
            int start;
             int end;
             Interval() { start = 0; end = 0; }
             Interval(int s, int e) { start = s; end = e; }
     }

    public ArrayList<Interval> merge(ArrayList<Interval> intervals) {

        Collections.sort(intervals, new IntervalComparable());


        Interval currentInterval = null;
        ArrayList<Interval> result = new ArrayList<Interval>();
        for (Interval interval : intervals) {

             if (currentInterval == null) {
                 result.add(interval);
                 currentInterval = interval;
             } else if (interval.start <= currentInterval.end) {
                 currentInterval =  mergeTwo(interval, currentInterval);
                 result.remove(result.size() - 1);
                 result.add(currentInterval);
             } else {
                 result.add(interval);
                 currentInterval = interval;
             }
        }

        return result;


    }

    private Interval mergeTwo(Interval a, Interval b) {
        Interval result = new Interval();
        result.start = a.start < b.start ? a.start : b.start;
        result.end = a.end > b.end ? a.end : b.end;
        return result;
    }

    public class IntervalComparable implements Comparator<Interval> {

        @Override
        public int compare(Interval o1, Interval o2) {
            return (o1.start < o2.start ? -1 : (o1.start == o2.start ? 0 : 1));
        }
    }

    public void test() {
        Interval a = new Interval(1, 4);
        Interval b = new Interval(5, 6);
        ArrayList<Interval> list = new ArrayList<Interval>();
        list.add(a);
        list.add(b);
        for (Interval i : merge(list)) {
            System.out.println("(" + i.start + ", " + i.end + ")");
        }
    }

    public static void main(String[] args) {
        MergeInterval mergeInterval = new MergeInterval();
        mergeInterval.test();
    }
}
