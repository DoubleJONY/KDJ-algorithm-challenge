# https://leetcode.com/problems/network-delay-time/

from collections import defaultdict
import heapq


class Solution:
    def networkDelayTime(self, times: list[list[int]], n: int, k: int) -> int:
        graph = defaultdict(list)
        for u, v, w in times:
            graph[u].append((v, w))

        dists = defaultdict(int)
        q = [(0, k)]
        while q:
            t, u = heapq.heappop(q)
            if u not in dists:
                dists[u] = t
                for v, w in graph[u]:
                    heapq.heappush(q, (t + w, v))

        return max(dists.values()) if len(dists) == n else -1
