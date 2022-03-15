# https://leetcode.com/problems/maximum-genetic-difference-query/

# Runtime: 9460 ms, faster than 20.00% of Python3 online submissions for Maximum Genetic Difference Query.
# Memory Usage: 233.5 MB, less than 24.00% of Python3 online submissions for Maximum Genetic Difference Query.

from collections import defaultdict
from typing import List

MAX_DIGITS = 18


class Trie:
    def __init__(self):
        self.child = {}
        self.active_cnt = 0

    def activate(self, node: int, _addition: int = 1):
        curr = self
        for i in range(MAX_DIGITS)[::-1]:
            bit = (node >> i) & 1
            if bit not in curr.child:
                curr.child[bit] = Trie()
            curr = curr.child[bit]
            curr.active_cnt += _addition

    def deactivate(self, node: int):
        self.activate(node, -1)

    def get_max_diff(self, value: int):
        curr = self
        max_diff = 0
        for i in range(MAX_DIGITS)[::-1]:
            bit = (value >> i) & 1
            flipped_bit = 1 - bit
            if flipped_bit in curr.child and curr.child[flipped_bit].active_cnt > 0:
                curr = curr.child[flipped_bit]
                max_diff |= 1 << i
            else:
                curr = curr.child[bit]

        return max_diff


class Solution:
    def maxGeneticDifference(self, parents: List[int], queries: List[List[int]]) -> List[int]:
        graph = defaultdict(list)
        for n, p in enumerate(parents):
            graph[p].append(n)

        query_graph = defaultdict(list)
        for i, (n, v) in enumerate(queries):
            query_graph[n].append((i, v))

        trie = Trie()
        max_diffs = [-1 for _ in queries]

        def dfs(node: int):
            trie.activate(node)
            for i, v in query_graph[node]:
                max_diffs[i] = trie.get_max_diff(v)
            for child in graph[node]:
                dfs(child)
            trie.deactivate(node)

        dfs(graph[-1][0])
        return max_diffs
