class Solution {
    int n;
    int[][] triangle;
    int[][] results;

    public int solution(int[][] triangle) {
        this.n = triangle.length;
        this.triangle = triangle;
        this.results = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                results[i][j] = -1;
            }
        }

        return findMaxSum(0, 0);
    }

    public int findMaxSum(int depth, int index) {
        if (depth >= n)
            return 0;
        if (index < 0 || index > depth)
            return 0;
        if (results[depth][index] != -1)
            return results[depth][index];

        int caseA = triangle[depth][index] + findMaxSum(depth+1, index);
        int caseB = triangle[depth][index] + findMaxSum(depth+1, index+1);
        return (results[depth][index] = Math.max(caseA, caseB));
    }
}
