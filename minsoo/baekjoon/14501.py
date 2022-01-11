# https://www.acmicpc.net/problem/14501

times, pays = zip(*[list(map(int, input().split())) for _ in range(int(input()))])


def max_pay(pay: int = 0, start: int = 0):
    if start >= len(times):
        return pay

    _max = pay
    for i, (t, p) in enumerate(zip(times, pays)):
        if i < start:
            continue
        if i + t > len(times):
            continue

        _max = max(_max, max_pay(pay + p, i + t))

    return _max


print(max_pay())
