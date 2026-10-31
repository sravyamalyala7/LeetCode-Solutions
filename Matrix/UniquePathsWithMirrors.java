class Solution {
    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int MOD = 1000000007;

        long[][] right = new long[m][n];
        long[][] down = new long[m][n];

        // First move from the starting cell
        right[0][1] = 1;
        down[1][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                // Destination reached
                if (i == m - 1 && j == n - 1) {
                    return (int) ((right[i][j] + down[i][j]) % MOD);
                }

                if (grid[i][j] == 1) {

                    // Entered mirror while moving right
                    // Reflect down
                    if (i + 1 < m) {
                        down[i + 1][j] =
                            (down[i + 1][j] + right[i][j]) % MOD;
                    }

                    // Entered mirror while moving down
                    // Reflect right
                    if (j + 1 < n) {
                        right[i][j + 1] =
                            (right[i][j + 1] + down[i][j]) % MOD;
                    }

                } else {

                    // Empty cell: robot can move right or down
                    long total = (right[i][j] + down[i][j]) % MOD;

                    if (j + 1 < n) {
                        right[i][j + 1] =
                            (right[i][j + 1] + total) % MOD;
                    }

                    if (i + 1 < m) {
                        down[i + 1][j] =
                            (down[i + 1][j] + total) % MOD;
                    }
                }
            }
        }

        return 0;
    }
}

