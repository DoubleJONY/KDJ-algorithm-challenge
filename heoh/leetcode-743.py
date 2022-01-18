from collections import defaultdict
from queue import PriorityQueue
from typing import List


class Solution:
    def networkDelayTime(self, times: List[List[int]], n: int, k: int) -> int:
        edges_of_vertex = defaultdict(list)
        for u, v, w in times:
            edges_of_vertex[u].append((v, w))

        visited_time_of_node = {}

        visit_queue = PriorityQueue()
        visit_queue.put((0, k))
        while visit_queue:
            t, u = visit_queue.get()
            visited_time_of_node[u] = t
            for v, w in edges_of_vertex[u]:
                if v not in visited_time_of_node:
                    visit_queue.put((t + w, v))

        visited_nodes = visited_time_of_node.keys()
        if len(visited_nodes) == n:
            return max(visited_time_of_node.values())
        else:
            return -1
