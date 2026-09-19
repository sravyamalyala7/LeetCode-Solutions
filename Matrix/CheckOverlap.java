/*
Problem: 1401. Circle and Rectangle Overlapping
Difficulty: Medium
Topic: Geometry, Math

Approach:
1. Find the point in the rectangle that is closest to the circle's center.
2. Calculate the squared distance between the circle's center
   and this closest point.
3. If the squared distance is less than or equal to the
   squared radius, the circle overlaps the rectangle.
4. Otherwise, there is no overlap.

Time Complexity: O(1)
Space Complexity: O(1)
*/

class Solution {

    public boolean checkOverlap(int radius, int xCenter, int yCenter,
                                int x1, int y1, int x2, int y2) {

        // Find the closest x-coordinate in the rectangle
        int closestX = Math.max(x1, Math.min(xCenter, x2));

        // Find the closest y-coordinate in the rectangle
        int closestY = Math.max(y1, Math.min(yCenter, y2));

        // Calculate the difference between the circle center
        // and the closest point
        int dx = xCenter - closestX;
        int dy = yCenter - closestY;

        // Compare squared distance with squared radius
        return dx * dx + dy * dy <= radius * radius;
    }
}