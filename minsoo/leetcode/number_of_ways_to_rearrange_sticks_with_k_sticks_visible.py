# https://leetcode.com/problems/number-of-ways-to-rearrange-sticks-with-k-sticks-visible/

# Runtime: 278 ms, faster than 97.34% of Python3 online submissions for Number of Ways to Rearrange Sticks With K Sticks Visible.
# Memory Usage: 64.4 MB, less than 74.34% of Python3 online submissions for Number of Ways to Rearrange Sticks With K Sticks Visible.

from functools import cache

mod = 1_000_000_007


@cache
def dp(n: int, k: int):
    if n == k:
        return 1
    if n <= 0 or k <= 0:
        return 0
    return (dp(n - 1, k - 1) + (n - 1) * dp(n - 1, k)) % mod


class Solution:
    def rearrangeSticks(self, n: int, k: int) -> int:
        return dp(n, k)
