# https://leetcode.com/problems/pizza-with-3n-slices/

# Runtime: 240 ms, faster than 90.43% of Python3 online submissions for Pizza With 3n Slices.
# Memory Usage: 40.1 MB, less than 42.55% of Python3 online submissions for Pizza With 3n Slices.

from functools import cache
from typing import List


class Solution:
    def maxSizeSlices(self, slices: List[int]) -> int:
        @cache
        def dp(start, end, num_to_pick, avoid_adjacent=0):
            if end - start < 2 * num_to_pick - 1:
                return float("-inf")
            if num_to_pick == 1:
                return max(slices[start:end])

            return max(
                dp(start + avoid_adjacent, end - 2, num_to_pick - 1) + slices[end - 1],
                dp(start, end - 1, num_to_pick),
            )

        return dp(0, len(slices), len(slices) // 3, 1)
