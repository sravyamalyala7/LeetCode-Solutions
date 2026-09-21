/*
Problem: 3524. Find X Value of Array I
Difficulty: Medium
Topic: Dynamic Programming, Array, Math

Approach:
1. Use DP to count subarrays ending at the current position
   for each possible product remainder modulo k.
2. Start a new subarray with the current number.
3. Extend every previous subarray by multiplying its remainder
   with the current number.
4. Store the number of subarrays for each remainder.
5. Add these counts to the final result.

Time Complexity: O(n * k)
Space Complexity: O(k)
*/

class Solution {

    public long[] resultArray(int[] nums, int k) {

        long[] result = new long[k];
        long[] dp = new long[k];

        for (int num : nums) {

            long[] next = new long[k];

            // Start a new subarray with only the current number
            int remainder = num % k;
            next[remainder]++;

            // Extend all previous subarrays
            for (int r = 0; r < k; r++) {

                if (dp[r] > 0) {

                    int newRemainder =
                        (int) ((long) r * num % k);

                    next[newRemainder] += dp[r];
                }
            }

            // Add counts to the final result
            for (int r = 0; r < k; r++) {
                result[r] += next[r];
            }

            dp = next;
        }

        return result;
    }
}