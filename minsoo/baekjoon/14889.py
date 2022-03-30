# https://www.acmicpc.net/problem/14889

from itertools import combinations

s = [list(map(int, input().split())) for _ in range(int(input()))]
row_sums, col_sums = [sum(row) for row in s], [sum(col) for col in zip(*s)]
tot_sum = sum(row_sums)

print(
    min(
        abs(tot_sum - sum(row_sums[idx] + col_sums[idx] for idx in comb))
        for comb in combinations(range(len(s)), r=len(s) // 2)
    )
)
