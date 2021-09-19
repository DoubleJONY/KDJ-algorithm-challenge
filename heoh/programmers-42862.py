from typing import Set

def solution(n, lost, reserve):
    lost, reserve = set(lost), set(reserve)
    lost, reserve = borrow_from_self(lost, reserve)
    lost, reserve = borrow_from_other(lost, reserve)
    return n - len(lost)

def borrow_from_self(lost: Set[int], reserve: Set[int]):
    both = lost & reserve
    lost -= both
    reserve -= both
    return lost, reserve

def borrow_from_other(lost: Set[int], reserve: Set[int]):
    lost = lost.copy()
    reserve = reserve.copy()
    for lost_id in sorted(lost):
        helper_id = find_helper(lost_id, reserve)
        if helper_id != -1:
            lost.remove(lost_id)
            reserve.remove(helper_id)
    return lost, reserve

def find_helper(lost_id, reserve):
    if (lost_id - 1) in reserve:
        return lost_id - 1
    if (lost_id + 1) in reserve:
        return lost_id + 1
    return -1
