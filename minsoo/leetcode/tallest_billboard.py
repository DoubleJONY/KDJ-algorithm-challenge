# https://leetcode.com/problems/tallest-billboard/

# Runtime: 448 ms, faster than 100.00% of Python3 online submissions for Tallest Billboard.
# Memory Usage: 14.3 MB, less than 96.12% of Python3 online submissions for Tallest Billboard.


from collections import defaultdict
from typing import List


class Solution:
    def tallestBillboard(self, rods: List[int]) -> int:
        dp = defaultdict(int)
        dp[0] = 0

        for h in rods:
            _dp = dp.copy()
            for h_diff, _h in dp.items():
                _dp[h_diff + h] = max(_dp[h_diff + h], _h)
                if h_diff >= h:
                    _dp[h_diff - h] = max(_dp[h_diff - h], _h + h)
                else:
                    _dp[h - h_diff] = max(_dp[h - h_diff], _h + h_diff)
            dp = _dp
        return dp[0]
