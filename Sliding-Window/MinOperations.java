/*
Problem: 1658. Minimum Operations to Reduce X to Zero
Difficulty: Medium
Topic: Sliding Window, Prefix Sum, Array

Approach:
1. Find the total sum of the array.
2. Instead of removing elements from both ends, find the longest
   subarray whose sum is totalSum - x.
3. Use a sliding window to find this longest subarray.
4. The minimum operations required are:
   n - longest subarray length.

Time Complexity: O(n)
Space Complexity: O(1)
*/

class Solution {

    public int minOperations(int[] nums, int x) {

        int n = nums.length;

        int total = 0;

        for (int num : nums) {
            total += num;
        }

        int target = total - x;

        // If target is negative, it is impossible
        if (target < 0) {
            return -1;
        }

        int left = 0;
        int sum = 0;
        int maxLen = -1;

        // Find the longest subarray with sum = target
        for (int right = 0; right < n; right++) {

            sum += nums[right];

            while (sum > target && left <= right) {
                sum -= nums[left];
                left++;
            }

            if (sum == target) {
                maxLen = Math.max(maxLen, right - left + 1);
            }
        }

        // No valid subarray found
        if (maxLen == -1) {
            return -1;
        }

        return n - maxLen;
    }
}
