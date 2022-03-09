# https://leetcode.com/problems/custom-sort-string/

# Runtime: 37 ms, faster than 68.23% of Python3 online submissions for Custom Sort String.
# Memory Usage: 13.9 MB, less than 47.26% of Python3 online submissions for Custom Sort String.

from collections import Counter


class Solution:
    def customSortString(self, order: str, s: str) -> str:
        cnt = Counter(s)
        return "".join(c * cnt.pop(c, 0) for c in order) + "".join(c * n for c, n in cnt.items())
