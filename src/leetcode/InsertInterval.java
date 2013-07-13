package leetcode;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 7/11/13
 * Time: 10:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class InsertInterval {
    public class Interval {
        int start;
        int end;
        Interval() { start = 0; end = 0; }
        Interval(int s, int e) { start = s; end = e; }
    }


    /**
     * Inserting the interval is tricky and finding the start point is messy.
     * Thus the way is keep track of another array, then iterate over the original array then insert the new interval
     * and update the pointer accordingly.
     *
     * 1. add any potential intervals whose end is less than the start of new interval
     * 2. keep iterating the array, if newInterval's end is bigger or equal to the start of current interval
     *    update the end or the start of the newInterval,
     *    update the point, and keep moving forward.
     * 3. Insert the newInterval into the result array, at this point, the newInterval is merged.
     * 4. append the rest into the result array.
     */
    public ArrayList<Interval> insert(ArrayList<Interval> intervals, Interval newInterval) {
        ArrayList<Interval> result = new ArrayList<Interval>();

        int i = 0;
        int n = intervals.size();
        while(i < n && newInterval.start > intervals.get(i).end)
            result.add(intervals.get(i++));

        while(i < n && newInterval.end >= intervals.get(i).start) {
            newInterval.end = Math.max(newInterval.end, intervals.get(i).end); // pick the bigger one
            newInterval.start = Math.min(newInterval.start, intervals.get(i).start); // pick the smaller one
            ++i; // move forward
        }

        result.add(newInterval);
        while(i < n)
            result.add(intervals.get(i++));

        return result;
    }

    /**
     * Binary search iterative version for finding the result index
     *
     * 1. we need to be careful about the end condition: start <= end
     * 2. depends on the result requirement, the final step should return the lower index
     */
    private int index(ArrayList<Interval> intervals, Interval newInterval) {

        int start = 0;
        int end = intervals.size() - 1;

        int mid;

        while (start <= end) {
            mid = start + (end - start) / 2;

            if (intervals.get(mid).start < newInterval.start)
                start = mid + 1;
            else if (intervals.get(mid).start > newInterval.start)
                end = mid - 1;
            else
                return mid;
        }
        return start;
    }
}
