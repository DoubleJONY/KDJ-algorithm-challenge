N = int(input())
T = [0] * N
P = [0] * N
for i in range(N):
    T[i], P[i] = list(map(int, input().split()))


cache = {}


def get_maximum_profit_from(i):
    if i in cache:
        return cache[i]
    if i >= N:
        cache[i] = 0
        return cache[i]

    remaining_days = N - i
    if T[i] <= remaining_days:
        profit_yes = P[i] + get_maximum_profit_from(i+T[i])
        profit_no = get_maximum_profit_from(i+1)
        maximum_profit = max(profit_yes, profit_no)
    else:
        profit_no = get_maximum_profit_from(i+1)
        maximum_profit = profit_no

    cache[i] = maximum_profit
    return cache[i]


print(get_maximum_profit_from(0))
