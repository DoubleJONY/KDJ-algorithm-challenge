# https://programmers.co.kr/learn/courses/30/lessons/42578

from collections import Counter
from math import prod

def solution(clothes):
    cnt = Counter(list(zip(*clothes))[1])
    return prod(map(lambda n: n + 1, cnt.values())) - 1
