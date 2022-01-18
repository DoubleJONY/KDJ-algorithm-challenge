from typing import List


class Solution:
    def maxIncreaseKeepingSkyline(self, grid: List[List[int]]) -> int:
        n = len(grid)

        maximum_total_sum = 0
        for r in range(n):
            for c in range(n):
                maximum_height = self.get_maximum_height(grid, r, c)
                height = grid[r][c]
                diff = maximum_height - height
                maximum_total_sum += diff
                print(f'{maximum_height:2d} ', end='')
            print()

        return maximum_total_sum

    def get_maximum_height(self, grid: List[List[int]], r: int, c: int) -> int:
        maximum_of_row = self.get_maximum_of_row(grid, r)
        maximum_of_col = self.get_maximum_of_col(grid, c)
        return min(maximum_of_row, maximum_of_col)

    def get_maximum_of_row(self, grid: List[List[int]], r: int) -> int:
        max_value = 0
        n = len(grid)
        for c in range(n):
            max_value = max(max_value, grid[r][c])
        return max_value

    def get_maximum_of_col(self, grid: List[List[int]], c: int) -> int:
        max_value = 0
        n = len(grid)
        for r in range(n):
            max_value = max(max_value, grid[r][c])
        return max_value
