/*
Problem: 1096. Brace Expansion II
Difficulty: Hard
Topic: Strings, Parsing, Set, Recursion

Approach:
1. Use recursive parsing to process the expression.
2. parseExpression() handles union using commas.
3. parseConcatenation() handles characters and brace groups
   that appear next to each other.
4. For concatenation, generate the Cartesian product of
   the possible strings from both parts.
5. Use a HashSet to remove duplicate strings.
6. Sort the final result lexicographically.

Time Complexity: O(number of generated strings × expression length)
Space Complexity: O(number of generated strings)
*/

import java.util.*;

class Solution {

    private String s;
    private int index;

    public List<String> braceExpansionII(String expression) {

        s = expression;
        index = 0;

        Set<String> result = parseExpression();

        List<String> answer = new ArrayList<>(result);

        Collections.sort(answer);

        return answer;
    }

    // Handles union:
    // a,b,c
    // {a,b}
    private Set<String> parseExpression() {

        Set<String> result = new HashSet<>();

        result.addAll(parseConcatenation());

        // Handle commas (union)
        while (index < s.length() && s.charAt(index) == ',') {

            index++;

            result.addAll(parseConcatenation());
        }

        // Skip closing brace
        if (index < s.length() && s.charAt(index) == '}') {
            index++;
        }

        return result;
    }

    // Handles concatenation:
    // abc
    // {a,b}{c,d}
    // a{b,c}d
    private Set<String> parseConcatenation() {

        Set<String> result = new HashSet<>();

        result.add("");

        while (index < s.length()
                && s.charAt(index) != ','
                && s.charAt(index) != '}') {

            Set<String> current;

            char ch = s.charAt(index);

            if (ch == '{') {

                index++;

                current = parseExpression();

            } else {

                current = new HashSet<>();

                current.add(String.valueOf(ch));

                index++;
            }

            // Cartesian product for concatenation
            Set<String> next = new HashSet<>();

            for (String a : result) {
                for (String b : current) {
                    next.add(a + b);
                }
            }

            result = next;
        }

        return result;
    }
}