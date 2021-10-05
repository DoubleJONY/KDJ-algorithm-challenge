# https://programmers.co.kr/learn/courses/30/lessons/42862?language=cpp

def solution(n, lost, reserve):
    in_common = set(lost) & set(reserve)
    lost = set(lost) - in_common
    reserve = set(reserve) - in_common

    cannot_attend = set()
    for who in lost:
        if who - 1 in reserve:
            reserve.remove(who - 1)
        elif who + 1 in reserve:
            reserve.remove(who + 1)
        else:
            cannot_attend.add(who)

    return n - len(cannot_attend)
