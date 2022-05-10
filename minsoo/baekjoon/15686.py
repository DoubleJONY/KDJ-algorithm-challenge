# https://www.acmicpc.net/problem/15686

from itertools import combinations

EMPTY = 0
HOUSE = 1

m, n = map(int, input().split())
chiken_map = [list(map(int, input().split())) for _ in range(m)]

houses = []
chickens = []
for r, row in enumerate(chiken_map):
    for c, cls in enumerate(row):
        if cls == EMPTY:
            continue
        if cls == HOUSE:
            houses.append((r, c))
        else:
            chickens.append((r, c))

dists_to_chickens = [[] for _ in houses]
for i, (r, c) in enumerate(houses):
    for r_chicken, c_chicken in chickens:
        dists_to_chickens[i].append((r_chicken, c_chicken))


min_chicken_dist = float("inf")
for selected in combinations(chickens, r=n):
    chicken_dists = []
    for r, c in houses:
        chicken_dists.append(
            min(
                abs(r_chicken - r) + abs(c_chicken - c)
                for r_chicken, c_chicken in selected
            )
        )
    min_chicken_dist = min(min_chicken_dist, sum(chicken_dists))

print(min_chicken_dist)
