# https://leetcode.com/problems/longest-increasing-path-in-a-matrix/

# Runtime: 328 ms, faster than 99.91% of Python3 online submissions for Longest Increasing Path in a Matrix.
# Memory Usage: 18.2 MB, less than 38.56% of Python3 online submissions for Longest Increasing Path in a Matrix.

from functools import cache


class Solution:
    def longestIncreasingPath(self, matrix: list[list[int]]) -> int:
        row, col = len(matrix), len(matrix[0])

        @cache
        def dfs(r, c):
            val = matrix[r][c]
            return 1 + max(
                dfs(r - 1, c) if r - 1 >= 0 and matrix[r - 1][c] > val else 0,
                dfs(r + 1, c) if r + 1 < row and matrix[r + 1][c] > val else 0,
                dfs(r, c - 1) if c - 1 >= 0 and matrix[r][c - 1] > val else 0,
                dfs(r, c + 1) if c + 1 < col and matrix[r][c + 1] > val else 0,
            )

        return max(dfs(r, c) for r in range(row) for c in range(col))
