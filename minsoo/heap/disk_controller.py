# https://programmers.co.kr/learn/courses/30/lessons/42627?language=python3

import heapq

def solution(jobs):
    min_heap = []
    heapq.heapify(min_heap)
    
    jobs.sort()
    _len, i = len(jobs), 0
    t, t_sum = 0, 0
    while i < _len or min_heap:
        while i < _len and t >= jobs[i][0]:
            heapq.heappush(min_heap, jobs[i][::-1])
            i += 1
        if min_heap:
            t_takes, t_start = heapq.heappop(min_heap)
            t += t_takes
            t_sum += t - t_start
        else:
            t = jobs[i][0]
            
    return t_sum // _len
