 namespace TestApp1;

public class H956
{
    public int TallestBillboard(int[] rods)
    {
        int sum = 0;
        int n = rods.Length;
        int N = 2 * 5000;
        List<List<int>> dp = new List<List<int>>();
        for (int i = 0; i < n + 1; i++)
        {
            dp.Add(new List<int>());
        }

        for (int i = 0; i < n + 1; i++)
        {
            dp.Add(Enumerable.Repeat(-1, N + 1).ToList());
        }

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j <= N; j++)
            {
                int x = rods[i];
                if (j - x >= 0 && dp[i][j - x] != -1)
                {
                    dp[i + 1][j] = Math.Max(dp[i + 1][j], dp[i][j - x] +  x);
                }

                if (j + x <= N && dp[i][j + x] != -1)
                {
                    dp[i + 1][j] = Math.Max(dp[i + 1][j], dp[i][j + x]);
                }

                if (dp[i][j] != -1)
                {
                    dp[i + 1][j] = Math.Max(dp[i][j], dp[i + 1][j]);
                }
            }
        }

        return dp[n][5000];
    }
}