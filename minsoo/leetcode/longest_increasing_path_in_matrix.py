# https://leetcode.com/problems/longest-increasing-path-in-a-matrix/

# Runtime: 620 ms, faster than 39.92% of Python3 online submissions for Longest Increasing Path in a Matrix.
# Memory Usage: 15.1 MB, less than 83.63% of Python3 online submissions for Longest Increasing Path in a Matrix.

dr = [-1, 0, 1, 0]
dc = [0, -1, 0, 1]


class Solution:
    def longestIncreasingPath(self, matrix: list[list[int]]) -> int:
        self.row = len(matrix)
        self.col = len(matrix[0])
        self.cache = [[0 for _ in row] for row in matrix]

        max_cnt = 1
        for r in range(self.row):
            for c in range(self.col):
                max_cnt = max(self.dfs(matrix, r, c), max_cnt)
        return max_cnt

    def dfs(self, matrix: list[list[int]], r: int, c: int, cnt: int = 1) -> int:
        if self.cache[r][c]:
            return self.cache[r][c]

        max_cnt = cnt
        for _r, _c in [(r + _dr, c + _dc) for _dr, _dc in zip(dr, dc)]:
            if not self.is_valid(_r, _c):
                continue
            if matrix[_r][_c] <= matrix[r][c]:
                continue

            if not self.cache[_r][_c]:
                self.cache[_r][_c] = self.dfs(matrix, _r, _c)
            max_cnt = max(cnt + self.cache[_r][_c], max_cnt)

        self.cache[r][c] = max_cnt
        return max_cnt

    def is_valid(self, r, c):
        return 0 <= r < self.row and 0 <= c < self.col
