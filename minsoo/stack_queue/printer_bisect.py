# https://programmers.co.kr/learn/courses/30/lessons/42587

from collections import defaultdict
from bisect import bisect

def solution(priorities, location):
    q_dict = defaultdict(list)
    for i, p in enumerate(priorities):
        q_dict[p].append(i)
        
    find_start_idx = lambda p, prev_last_idx: bisect(q_dict[p], prev_last_idx)

    prev_last_idx, cnt = -1, 1
    for p in sorted(set(priorities), reverse=True):
        start_idx = find_start_idx(p, prev_last_idx)
        if p > priorities[location]:
            prev_last_idx = q_dict[p][start_idx - 1]
            cnt += len(q_dict[p])
        else:
            target_idx = q_dict[p].index(location)
            cnt += target_idx - start_idx
            return cnt + (start_idx > target_idx) * len(q_dict[p])
