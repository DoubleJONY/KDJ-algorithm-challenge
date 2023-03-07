# https://leetcode.com/problems/strange-printer/


class Solution:
    def strangePrinter(self, s: str) -> int:
        dp = [[0 for _ in s] for _ in s]

        for i in reversed(range(len(s))):
            dp[i][i] = 1
            for j in range(i + 1, len(s)):
                if s[i] == s[j]:
                    dp[i][j] = dp[i][j - 1] # or dp[i + 1][j]
                else:
                    dp[i][j] = min(dp[i][k] + dp[k + 1][j] for k in range(i, j))

        return dp[0][-1]
