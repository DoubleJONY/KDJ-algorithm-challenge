# https://programmers.co.kr/learn/courses/30/lessons/42626

import heapq

def solution(scoville, K):
    heapq.heapify(scoville)
    cnt = 0
    while len(scoville) > 1 and (lwst := heapq.heappop(scoville)) < K:
        scd_lwst = heapq.heappop(scoville)
        heapq.heappush(scoville, lwst + 2 * scd_lwst)
        cnt += 1
    return -1 if heapq.heappop(scoville) < K else cnt
