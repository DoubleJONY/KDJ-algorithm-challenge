class Solution {
    final int MOD = 1000000007;
    
    int rows;
    int cols;
    int[][] cache;

    public int solution(int m, int n, int[][] puddles) {
        init(m, n, puddles);
        return countAllCases(1, 1);
    }

    private void init(int m, int n, int[][] puddles) {
        rows = n;
        cols = m;

        cache = new int[rows+1][cols+1];
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                cache[i][j] = -1;
            }
        }
        for (int[] p : puddles) {
            cache[p[1]][p[0]] = 0;
        }
        cache[rows][cols] = 1;
    }

    private int countAllCases(int row, int col) {
        if (row < 1 || row > rows) return 0;
        if (col < 1 || col > cols) return 0;
        if (cache[row][col] != -1) return cache[row][col];

        int nCasesWhenGoRight = countAllCases(row, col+1);
        int nCasesWhenGoDown = countAllCases(row+1, col);
        int nTotalCases = (nCasesWhenGoRight + nCasesWhenGoDown) % MOD;
        return (cache[row][col] = nTotalCases);
    }
}
