# https://programmers.co.kr/learn/courses/30/lessons/42587

import collections, heapq

def solution(priorities, location):
    in_order = list(map(lambda p: -p, priorities))
    heapq.heapify(in_order)
    priorities = collections.deque(enumerate(priorities))

    turn = 1
    while priorities:
        p = priorities.popleft()
        if p[1] >= -in_order[0]:
            if p[0] == location:
                return turn
            heapq.heappop(in_order)
            turn += 1
        else:
            priorities.append(p)
