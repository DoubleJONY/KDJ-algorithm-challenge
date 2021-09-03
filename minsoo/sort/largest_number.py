# https://programmers.co.kr/learn/courses/30/lessons/42746?language=python3

from functools import cmp_to_key

def solution(numbers):
    cmp = lambda left, right: int(left + right) - int(right + left)
    num = ''.join(sorted(map(str, numbers), key=cmp_to_key(cmp), reverse=True))
    return '0' if num[0] == '0' else num
