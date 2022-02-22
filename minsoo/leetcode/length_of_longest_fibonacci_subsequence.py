# https://leetcode.com/problems/length-of-longest-fibonacci-subsequence/

# Runtime: 576 ms, faster than 99.53% of Python3 online submissions for Length of Longest Fibonacci Subsequence.
# Memory Usage: 15 MB, less than 40.47% of Python3 online submissions for Length of Longest Fibonacci Subsequence.

from collections import defaultdict
from typing import List


class Solution:
    def lenLongestFibSubseq(self, arr: List[int]) -> int:
        dp = defaultdict(lambda: 2)
        num2idx = {num: idx for idx, num in enumerate(arr)}

        max_fib_len = 2
        for i, num in enumerate(arr):
            for j in range(i)[::-1]:
                if num >= 2 * arr[j]:
                    break

                if (_num := num - arr[j]) in num2idx:
                    dp[j, i] = max(dp[j, i], 1 + dp[num2idx[_num], j])
                    max_fib_len = max(max_fib_len, dp[j, i])

        return max_fib_len if max_fib_len > 2 else 0
