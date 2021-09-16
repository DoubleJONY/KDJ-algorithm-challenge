# https://programmers.co.kr/learn/courses/30/lessons/42839

from itertools import permutations

def is_prime(number):
    for n in range(2, int(number ** 0.5) + 1):
        if number % n == 0:
            return False
    return False if number < 2 else True

def solution(numbers):
    seen = set()
    cnt = 0
    for k in range(len(numbers)):
        for p in permutations(numbers, k + 1):
            number = int(''.join(p))
            if number not in seen:
                seen.add(number)
                cnt += is_prime(number)
    
    return cnt
