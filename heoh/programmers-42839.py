from itertools import permutations
from functools import lru_cache

@lru_cache
def prime(x):
    return x > 1 and all((x % i != 0 for i in range(2, x)))

def solution(numbers: str):
    cases = set()
    for n in range(1, len(numbers) + 1):
        cases |= set(map(int, map(''.join, permutations(numbers, n))))
    answer = len(list(filter(prime, cases)))
    return answer
