/*
Problem: 2472. Maximum Number of Non-overlapping Palindrome Substrings
Difficulty: Medium
Topic: Strings, Dynamic Programming

Approach:
1. Build a palindrome table to check whether any substring is a palindrome.
2. Use DP where dp[i] represents the maximum number of palindromes
   that can be selected from the first i characters.
3. For every ending position, either skip it or select a palindrome
   of length at least k ending there.
4. Since selected palindromes must not overlap, use dp[start] + 1.

Time Complexity: O(n^2)
Space Complexity: O(n^2)
*/

class Solution {

    public int maxPalindromes(String s, int k) {

        int n = s.length();

        // palindrome[i][j] is true if s[i...j] is a palindrome
        boolean[][] palindrome = new boolean[n][n];

        // Build palindrome table
        for (int i = n - 1; i >= 0; i--) {

            for (int j = i; j < n; j++) {

                if (s.charAt(i) == s.charAt(j) &&
                    (j - i <= 2 || palindrome[i + 1][j - 1])) {

                    palindrome[i][j] = true;
                }
            }
        }

        // dp[i] = maximum number of palindromes
        // using the first i characters
        int[] dp = new int[n + 1];

        for (int end = 0; end < n; end++) {

            // Option 1: Skip this character
            dp[end + 1] = Math.max(dp[end + 1], dp[end]);

            // Try every possible starting position
            for (int start = 0; start <= end; start++) {

                int length = end - start + 1;

                if (length >= k && palindrome[start][end]) {

                    dp[end + 1] = Math.max(
                        dp[end + 1],
                        dp[start] + 1
                    );
                }
            }
        }

        return dp[n];
    }
}
