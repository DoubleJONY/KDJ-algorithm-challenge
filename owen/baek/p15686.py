from itertools import combinations #조합

n, m = map(int, input().split())
city = list(list(map(int, input().split())) for _ in range(n))
result = float("inf")
house, chick = [], []   


for i in range(n):
    for j in range(n):
        if city[i][j] == 1:
            house.append([i, j])
        elif city[i][j] == 2:
            chick.append([i, j])

for combination in combinations(chick,m):
    tmp = 0
    for h in house:
        h2c = float("inf")
        for j in range(m):
            h2c = min( h2c, abs(h[0]-combination[j][0])+ abs(h[1]-combination[j][1]) )
        tmp += h2c
    result = min(result, tmp)

print(result)


