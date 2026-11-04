import java.util.*;

class Solution {

    class MedianWindow {
        TreeMap<Integer, Integer> low = new TreeMap<>();
        TreeMap<Integer, Integer> high = new TreeMap<>();

        int lowSize = 0, highSize = 0;
        long lowSum = 0, highSum = 0;

        void addMap(TreeMap<Integer, Integer> map, int val) {
            map.put(val, map.getOrDefault(val, 0) + 1);
        }

        void removeMap(TreeMap<Integer, Integer> map, int val) {
            int count = map.get(val);
            if (count == 1) map.remove(val);
            else map.put(val, count - 1);
        }

        void add(int val) {
            if (low.isEmpty() || val <= low.lastKey()) {
                addMap(low, val);
                lowSize++;
                lowSum += val;
            } else {
                addMap(high, val);
                highSize++;
                highSum += val;
            }
            balance();
        }

        void remove(int val) {
            if (low.containsKey(val)) {
                removeMap(low, val);
                lowSize--;
                lowSum -= val;
            } else {
                removeMap(high, val);
                highSize--;
                highSum -= val;
            }
            balance();
        }

        void balance() {
            while (lowSize > highSize + 1) {
                int val = low.lastKey();
                removeMap(low, val);
                lowSize--;
                lowSum -= val;

                addMap(high, val);
                highSize++;
                highSum += val;
            }

            while (lowSize < highSize) {
                int val = high.firstKey();
                removeMap(high, val);
                highSize--;
                highSum -= val;

                addMap(low, val);
                lowSize++;
                lowSum += val;
            }
        }

        long getCost() {
            int median = low.lastKey();

            return (long) median * lowSize - lowSum
                    + highSum - (long) median * highSize;
        }
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;
        int windows = n - x + 1;

        // Step 1: Calculate the cost of every window
        long[] cost = new long[windows];

        MedianWindow mw = new MedianWindow();

        for (int i = 0; i < x; i++) {
            mw.add(nums[i]);
        }

        cost[0] = mw.getCost();

        for (int i = 1; i < windows; i++) {
            mw.remove(nums[i - 1]);
            mw.add(nums[i + x - 1]);

            cost[i] = mw.getCost();
        }

        // Step 2: Dynamic Programming
        long INF = Long.MAX_VALUE / 4;

        long[] prev = new long[n + 1];
        long[] curr = new long[n + 1];

        // Select 0 windows: cost is 0
        Arrays.fill(prev, 0);

        for (int t = 1; t <= k; t++) {

            Arrays.fill(curr, INF);

            for (int i = 1; i <= n; i++) {

                // Choice 1: Skip current element
                curr[i] = curr[i - 1];

                // Choice 2: Select a window of size x
                if (i >= x && prev[i - x] != INF) {

                    long take = prev[i - x] + cost[i - x];

                    curr[i] = Math.min(curr[i], take);
                }
            }

            long[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[n];
    }
}