# https://leetcode.com/problems/max-increase-to-keep-city-skyline/


class Solution:
    def maxIncreaseKeepingSkyline(self, grid: list[list[int]]) -> int:
        row_max_values = [max(row) for row in grid]
        col_max_values = [max(col) for col in zip(*grid)]

        max_increase = 0
        for r, row in enumerate(grid):
            for c, val in enumerate(row):
                max_increase += min(row_max_values[r], col_max_values[c]) - val

        return max_increase
