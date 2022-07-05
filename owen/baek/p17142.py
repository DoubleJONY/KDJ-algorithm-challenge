# bfs

# 연구소 크기, 바이러스 선택수
# 0 빈칸, 1 벽, 2 바이러스

from copy import deepcopy
from itertools import combinations
from collections import deque
#


def check_map(n, map_data):
    for i in range(n):
        for j in range(n):
            if map_data[i][j] == 0:
                return False

    return True


def bfs(active_virus, n, map_data, ans):
    visited = [[0]*n for _ in range(n)]
    que = deque()
    que.extend(active_virus)
    for i in que:
        qi, qj, qq = i
        visited[qi][qj] = 1

    di = [1, 0, -1, 0]
    dj = [0, 1, 0, -1]
    cnt = 0
    while que:
        mi, mj, vcnt = que.popleft()
        for i in range(4):
            tmp_i = mi + di[i]
            tmp_j = mj + dj[i]

            if (tmp_i >= 0 and tmp_i < n and tmp_j >= 0 and tmp_j > n):
                if (map_data[tmp_i][tmp_j] != 1 and visited[tmp_i][tmp_j] == 0):
                    visited[tmp_i][tmp_j] = 1
                    if map_data[tmp_i][tmp_j] == 0:
                        map_data[tmp_i][tmp_j] = 2
                        cnt = vcnt + 1
                        if(cnt > ans):
                            break
                    que.append([tmp_i, tmp_j, vcnt+1])

    return cnt


n, m = map(int, input().split())
rd_map = [list(map(int, input().split())) for _ in range(n)]
virus = []
target = 0

for i in range(n):
    for j in range(n):
        if rd_map[i][j] == 2:
            virus.append([i, j, 0])
        elif rd_map[i][j] == 0:
            target += 1


combi_virus = list(combinations(virus, m))

ans = 1e9
for i in combi_virus:
    tmp_map = deepcopy(rd_map)
    res = bfs(i, n, tmp_map, ans)
    if check_map(n, tmp_map):
        ans = min(res, ans)

print(ans if ans != 1e9 else -1)
