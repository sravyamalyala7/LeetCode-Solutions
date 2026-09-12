import java.util.*;

class Solution {

    long[][] dp;
    int[][][] best;
    int[][] intervals;
    int[] next;

    public int[] maximumWeight(List<List<Integer>> intervalsList) {

        int n = intervalsList.size();

        intervals = new int[n][4];

        for (int i = 0; i < n; i++) {
            intervals[i][0] = intervalsList.get(i).get(0);
            intervals[i][1] = intervalsList.get(i).get(1);
            intervals[i][2] = intervalsList.get(i).get(2);
            intervals[i][3] = i;
        }

        Arrays.sort(intervals, (a, b) -> {
            if (a[0] != b[0])
                return Integer.compare(a[0], b[0]);
            return Integer.compare(a[1], b[1]);
        });

        next = new int[n];

        for (int i = 0; i < n; i++) {
            next[i] = findNext(i, n);
        }

        dp = new long[n + 1][5];
        best = new int[n + 1][5][];

        for (int i = n - 1; i >= 0; i--) {

            for (int k = 1; k <= 4; k++) {

                long skipScore = dp[i + 1][k];
                int[] skipArray = best[i + 1][k];

                long takeScore = intervals[i][2];

                int[] nextArray = best[next[i]][k - 1];

                if (nextArray != null) {
                    takeScore += dp[next[i]][k - 1];
                }

                int[] takeArray = addIndex(nextArray, intervals[i][3]);

                if (takeScore > skipScore) {
                    dp[i][k] = takeScore;
                    best[i][k] = takeArray;

                } else if (takeScore < skipScore) {
                    dp[i][k] = skipScore;
                    best[i][k] = skipArray;

                } else {
                    dp[i][k] = takeScore;
                    best[i][k] = lexicographicallySmaller(
                        takeArray, skipArray
                    );
                }
            }
        }

        return best[0][4];
    }

    private int findNext(int i, int n) {

        int target = intervals[i][1];

        int low = i + 1;
        int high = n;

        while (low < high) {

            int mid = low + (high - low) / 2;

            if (intervals[mid][0] > target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    private int[] addIndex(int[] arr, int index) {

        int size = arr == null ? 0 : arr.length;

        int[] result = new int[size + 1];

        if (arr != null) {
            System.arraycopy(arr, 0, result, 0, size);
        }

        result[size] = index;
        Arrays.sort(result);

        return result;
    }

    private int[] lexicographicallySmaller(int[] a, int[] b) {

        if (a == null)
            return b;

        if (b == null)
            return a;

        int len = Math.min(a.length, b.length);

        for (int i = 0; i < len; i++) {

            if (a[i] < b[i])
                return a;

            if (a[i] > b[i])
                return b;
        }

        return a.length <= b.length ? a : b;
    }
}