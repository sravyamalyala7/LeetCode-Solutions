/*
 * Problem: Minimum Deletions to Remove Min and Max
 * Difficulty: Medium
 * Topic: Array
 *
 * Approach:
 * Find the indices of the minimum and maximum elements.
 * Calculate the minimum deletions using three possibilities:
 * 1. Remove both from the front
 * 2. Remove both from the back
 * 3. Remove one from the front and one from the back
 *
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */

class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;

        int minIndex = 0;
        int maxIndex = 0;

        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[minIndex]) {
                minIndex = i;
            }

            if (nums[i] > nums[maxIndex]) {
                maxIndex = i;
            }
        }

        int left = Math.min(minIndex, maxIndex);
        int right = Math.max(minIndex, maxIndex);

        int fromFront = right + 1;
        int fromBack = n - left;
        int fromBoth = (left + 1) + (n - right);

        return Math.min(fromFront, Math.min(fromBack, fromBoth));
    }
}