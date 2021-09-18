# https://programmers.co.kr/learn/courses/30/lessons/42885

from collections import deque

def solution(people, limit):
    weights = deque(sorted(people))
    cnt = 0
    while weights:
        tot_weight = weights.pop()
        while weights and tot_weight + weights[0] <= limit:
            tot_weight += weights.popleft()
        cnt += 1
    return cnt
