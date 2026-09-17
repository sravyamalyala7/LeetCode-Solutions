/*
Problem: 1477. Find Two Non-overlapping Sub-arrays Each With Target Sum
Difficulty: Medium
Topic: Prefix Sum, HashMap, Dynamic Programming

Approach:
1. Use prefix sum to find sub-arrays whose sum is equal to target.
2. Store the minimum length of a valid sub-array found up to each position.
3. When a new valid sub-array is found, check if another valid
   sub-array exists completely before it.
4. Combine their lengths and update the minimum answer.
5. Use a HashMap to find target-sum sub-arrays efficiently.

Time Complexity: O(n)
Space Complexity: O(n)
*/

import java.util.*;

class Solution {

    public int minSumOfLengths(int[] arr, int target) {

        int n = arr.length;

        // best[i] = minimum length of a valid sub-array
        // completely inside indices [0 ... i]
        int[] best = new int[n];

        Arrays.fill(best, Integer.MAX_VALUE);

        // prefixSum -> latest index
        HashMap<Integer, Integer> map = new HashMap<>();

        map.put(0, -1);

        int prefixSum = 0;
        int answer = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {

            prefixSum += arr[i];

            // Copy the previous best answer
            if (i > 0) {
                best[i] = best[i - 1];
            }

            // Find a previous prefix sum such that
            // current prefix - previous prefix = target
            int required = prefixSum - target;

            if (map.containsKey(required)) {

                int left = map.get(required);

                // Sub-array is from left + 1 to i
                int length = i - left;

                // Check if another valid sub-array exists before it
                if (left >= 0 && best[left] != Integer.MAX_VALUE) {

                    answer = Math.min(
                        answer,
                        length + best[left]
                    );
                }

                // Update the best valid sub-array
                best[i] = Math.min(best[i], length);
            }

            // Store current prefix sum
            map.put(prefixSum, i);
        }

        return answer == Integer.MAX_VALUE ? -1 : answer;
    }
}