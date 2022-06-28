# https://www.acmicpc.net/problem/17140

from collections import Counter

MAXLEN = 100
T_LIMIT = 100


def r_sort(a: list[list[int]]):
    _a = []
    maxlen = 0
    for row in a:
        cntr = Counter(row)
        cntr.pop(0, None)

        _row = []
        for key in sorted(cntr.keys(), key=lambda key: (cntr[key], key)):
            _row.extend([key, cntr[key]])

        _a.append(_row)
        maxlen = max(maxlen, len(_row))

    if maxlen > MAXLEN:
        maxlen = MAXLEN

    for row in _a:
        if len(row) == maxlen:
            continue

        if len(row) > maxlen:
            row = row[:maxlen]
        else:
            row.extend([0 for _ in range(maxlen - len(row))])

    return _a


def c_sort(a: list[list[int]]):
    a = list(zip(*a))
    a = r_sort(a)
    return list(zip(*a))


def is_satisfied(a: list[list[int]], r: int, c: int, k: int):
    return r < len(a) and c < len(a[0]) and a[r][c] == k


def _lower_stdin_indices(r: int, c: int, k: int):
    return r - 1, c - 1, k


def _select_sort_type(a: list[list[int]]):
    return r_sort if len(a) >= len(a[0]) else c_sort


r, c, k = _lower_stdin_indices(*map(int, input().split()))
a = [list(map(int, input().split())) for _ in range(3)]

t = 0
while t < T_LIMIT:
    if is_satisfied(a, r, c, k):
        break

    sort_fn = _select_sort_type(a)
    a = sort_fn(a)

    t += 1

print(t if is_satisfied(a, r, c, k) else -1)
