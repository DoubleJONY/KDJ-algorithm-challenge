# https://leetcode.com/problems/snakes-and-ladders/

# Runtime: 164 ms, faster than 59.02% of Python3 online submissions for Snakes and Ladders.
# Memory Usage: 14.1 MB, less than 55.71% of Python3 online submissions for Snakes and Ladders.

import heapq
from typing import List


class Solution:
    def snakesAndLadders(self, board: List[List[int]]) -> int:
        n = len(board)
        n2 = n ** 2
        
        def num2rc(num: int):
            num = n2 - num
            r, c = divmod(num, n)
            if (n + r) % 2:
                c = n - c - 1
            return r, c
        
        def snake_or_ladder(num: int):
            r, c = num2rc(num)
            return board[r][c] if board[r][c] != -1 else 0

        visited = [None] + [False for _ in range(n2)]
        
        q = [(0, 1)]
        while q:
            cnt, curr = heapq.heappop(q)

            if curr == n2:
                return cnt

            visited[curr] = True
            for dest in range(curr + 1, min(curr + 6, n2) + 1):
                if (_dest := snake_or_ladder(dest)):
                    dest = _dest

                if not visited[dest]:
                    heapq.heappush(q, (cnt + 1, dest))
                    visited[dest] = True
            
        return -1
