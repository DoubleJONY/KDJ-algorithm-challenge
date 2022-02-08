# https://leetcode.com/problems/longest-increasing-path-in-a-matrix/

# Runtime: 550 ms, faster than 54.78% of Python3 online submissions for Longest Increasing Path in a Matrix.
# Memory Usage: 18.8 MB, less than 26.30% of Python3 online submissions for Longest Increasing Path in a Matrix.

from functools import cache


class Solution:
    def longestIncreasingPath(self, matrix: list[list[int]]) -> int:
        row, col = len(matrix), len(matrix[0])

        def is_valid(r, c):
            return 0 <= r < row and 0 <= c < col

        @cache
        def dfs(r, c):
            return 1 + max(
                dfs(_r, _c) if is_valid(_r, _c) and matrix[_r][_c] > matrix[r][c] else 0
                for _r, _c in [(r + dr, c + dc) for dr, dc in [(-1, 0), (0, -1), (1, 0), (0, 1)]]
            )

        return max(dfs(r, c) for r in range(row) for c in range(col))
