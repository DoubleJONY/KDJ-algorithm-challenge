# https://leetcode.com/problems/preimage-size-of-factorial-zeroes-function/solutions/117821/four-binary-search-solutions-based-on-different-ideas/
# ref: https://leetcode.com/problems/preimage-size-of-factorial-zeroes-function/solutions/117821/four-binary-search-solutions-based-on-different-ideas/


class Solution:
    def preimageSizeFZF(self, k: int) -> int:
        m = 305175781
        while m > 0:
            if k == 5 * m:
                return 0
            k %= m
            m = (m - 1) // 5

        return 5
