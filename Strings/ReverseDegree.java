/*
Problem: Reverse Degree of a String
Difficulty: Easy
Topic: Strings, Math

Approach:
1. Traverse the string from left to right.
2. Find the reverse alphabet value of each character:
   a = 26, b = 25, ..., z = 1.
3. Multiply the reverse value by the character's 1-based position.
4. Add all products to get the reverse degree.

Time Complexity: O(n)
Space Complexity: O(1)
*/

class Solution {

    public int reverseDegree(String s) {

        int sum = 0;

        for (int i = 0; i < s.length(); i++) {

            // Reverse alphabet value:
            // a = 26, b = 25, ..., z = 1
            int reverseValue = 26 - (s.charAt(i) - 'a');

            // Position in the string (1-indexed)
            int position = i + 1;

            sum += reverseValue * position;
        }

        return sum;
    }
}
