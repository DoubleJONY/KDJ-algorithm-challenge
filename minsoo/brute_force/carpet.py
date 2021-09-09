# https://programmers.co.kr/learn/courses/30/lessons/42842

def solution(brown, yellow):
    return [(fst := 1 + brown / 4) + (scd := (fst ** 2 - (brown + yellow)) ** 0.5), fst - scd]
    