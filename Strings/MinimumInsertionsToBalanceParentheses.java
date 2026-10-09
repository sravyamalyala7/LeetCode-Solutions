/*
Problem: 1541 Minimum Insertions to Balance a Parentheses String
Difficulty: Medium
Topic: Greedy, String
Approach:
1. Maintain open for unmatched opening brackets.
2. Maintain insertions for required insertions.
3. Increment open when '(' is encountered.
4. For ')', check whether the next character is also ')'.
5. If not, insert one ')' to complete the pair.
6. If no unmatched '(' exists, insert one '('.
7. Each remaining '(' requires two ')' characters.

Time Complexity: O(n)
Space Complexity: O(1)
*/

class Solution {
    public int minInsertions(String s) {
        int open = 0;
        int insertions = 0;
        int i = 0;

        while (i < s.length()) {
            if (s.charAt(i) == '(') {
                open++;
                i++;
            } else {
                if (i + 1 < s.length() && s.charAt(i + 1) == ')') {
                    i += 2;
                } else {
                    insertions++;
                    i++;
                }

                if (open > 0) {
                    open--;
                } else {
                    insertions++;
                }
            }
        }

        return insertions + open * 2;
    }
}