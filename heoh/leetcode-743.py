from collections import defaultdict, deque
from typing import List


class Solution:
    def networkDelayTime(self, times: List[List[int]], n: int, k: int) -> int:
        edges_of_vertex = defaultdict(list)
        for u, v, w in times:
            edges_of_vertex[u].append((v, w))

        visited_time_of_node = {}

        visit_queue = deque()
        visit_queue.append((k, 0))
        while visit_queue:
            u, t = visit_queue.popleft()
            visited_time_of_node[u] = t
            for v, w in edges_of_vertex[u]:
                visit_queue.append((v, t + w))

        visited_nodes = visited_time_of_node.keys()
        if len(visited_nodes) == n:
            return max(visited_time_of_node.values())
        else:
            return -1
