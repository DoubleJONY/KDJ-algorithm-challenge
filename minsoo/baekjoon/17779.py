# https://www.acmicpc.net/problem/17779


def search(r: int, c: int):
    for d1 in range(1, n):
        for d2 in range(1, n):
            if not r + d1 + d2 < n:
                continue
            if c - d1 < 0 or not c + d2 < n:
                continue
            yield d1, d2


def diff(r: int, c: int, d1: int, d2: int):
    nums = [0 for _ in range(5)]
    for _r, row in enumerate(num_map):
        for _c, num in enumerate(row):
            if _r < r + d1 and _c <= c and _r + _c < r + c:
                nums[0] += num
            elif _r <= r + d2 and c < _c and _r - _c < r - c:
                nums[1] += num
            elif r + d2 < _r and c - d1 + d2 <= _c and r + c + 2 * d2 < _r + _c:
                nums[2] += num
            elif r + d1 <= _r and _c < c - d1 + d2 and r - c + 2 * d1 < _r - _c:
                nums[3] += num
            else:
                nums[4] += num
    return max(nums) - min(nums)


n = int(input())
num_map = [list(map(int, input().split())) for _ in range(n)]

min_diff = float("inf")
for r in range(n - 1):
    for c in range(n - 1):
        for d1, d2 in search(r, c):
            min_diff = min(min_diff, diff(r, c, d1, d2))

print(min_diff)
