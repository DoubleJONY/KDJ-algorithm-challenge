class Solution {
    final int MOD = 1000000007;

    public int solution(int cols, int rows, int[][] puddles) {
        boolean[][] puddleMap = new boolean[rows+2][cols+2];
        for (int[] p : puddles)
            puddleMap[p[1]][p[0]] = true;

        int[][] cache = new int[rows+2][cols+2];
        int r, c;

        c = cols+1;
        for (r = 1; r <= rows; r++)
            cache[r][c] = 0;

        r = rows+1;
        for (c = 1; c <= cols; c++)
            cache[r][c] = 0;

        cache[rows][cols + 1] = 1;  // 뒷 계산에서 목적지 체크를 안하기 위한 꼼수
        
        for (r = rows; r >= 1; r--)
            for (c = cols; c >= 1; c--)
                cache[r][c] = puddleMap[r][c] ? 0 : ((cache[r+1][c] + cache[r][c+1]) % MOD);

        return cache[1][1];
    }
}
