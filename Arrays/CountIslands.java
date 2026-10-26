class Solution {
    public int countIslands(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length, ans = 0;
        int[] dirs = {1, 0, -1, 0, 1};  
        int[] island = new int[m * n];  
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) continue;
                long total = grid[i][j];  
                grid[i][j] = 0;
                int size = 0;
                island[size++] = i * n + j;
                for (int h = 0; h < size; h++) {  
                    int x = island[h] / n, y = island[h] % n;
                    for (int d = 0; d < 4; d++) {
                        int a = x + dirs[d], b = y + dirs[d + 1];
                        if (a >= 0 && a < m && b >= 0 && b < n && grid[a][b] != 0) {
                            total += grid[a][b];
                            grid[a][b] = 0;                              island[size++] = a * n + b;
                        }
                    }
                }
                if (total % k == 0) ans++;
            }
        return ans;
    }
}