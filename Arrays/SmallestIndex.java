/*
Problem: Smallest Index With Digit Sum Equal to Index
Difficulty: Easy
Topic: Arrays, Math

Approach:
1. Traverse the array and consider each index.
2. Calculate the sum of digits of nums[i].
3. If the digit sum equals the current index, return that index.
4. If no index satisfies the condition, return -1.

Time Complexity: O(n * d)
Space Complexity: O(1)
*/

class Solution {

    public int smallestIndex(int[] nums) {

        for (int i = 0; i < nums.length; i++) {

            int n = nums[i];
            int sum = 0;

            // Calculate the sum of digits
            while (n > 0) {
                sum += n % 10;
                n /= 10;
            }

            // Check if digit sum equals the index
            if (sum == i) {
                return i;
            }
        }

        return -1;
    }
}