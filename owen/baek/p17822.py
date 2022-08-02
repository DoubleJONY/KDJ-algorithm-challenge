# n개의 판 m 개의 원소, t번 회전
# n개의 줄에 원판의 원소

# xi의 배수 원판, d방향(0시계 1반시계), k칸 회전

# 회전시 인접원판에서 같은수 제거
# 같은수가 없다면 평균을 구하고 평균보다 큰 수에서 1을 빼고 작은수는 1을 더함

from collections import deque


n, m, t = map(int, input().split())
rd_map = [deque(map(int, input().split())) for _ in range(n)]
# x, d, k

xdk_commend = [list(map(int, input().split())) for _ in range(t)]


answer = 0


def check_elemnet(L):
    remove_list = []
    element_sum = 0
    elemnet_count = 0
    for i in range(n):
        for j in range(m):
            if L[i][j] < -10000:
                continue
            else:
                element_sum += L[i][j]
                elemnet_count += 1
                if i == 0:
                    if L[i+1][j] == L[i][j] or L[i][j-1] == L[i][j] or L[i][(j+1) % m] == L[i][j]:
                        remove_list.append([i, j])
                        continue
                elif i == (n-1):
                    if L[i-1][j] == L[i][j] or L[i][j-1] == L[i][j] or L[i][(j+1) % m] == L[i][j]:
                        remove_list.append([i, j])
                        continue

                else:
                    if L[i-1][j] == L[i][j] or L[i][j-1] == L[i][j] or L[i][(j+1) % m] == L[i][j] or L[i+1][j] == L[i][j]:
                        remove_list.append([i, j])
                        continue

    if not remove_list and elemnet_count != 0:
        mean_ = float(float(element_sum) / float(elemnet_count))
        for i in range(n):
            for j in range(m):
                if L[i][j] == 0:
                    continue
                elif L[i][j] > mean_:
                    L[i][j] += -1
                else:
                    L[i][j] += 1
    elif not remove_list and elemnet_count == 0:
        return 0
    else:
        for i, j in remove_list:

            L[i][j] = float("-inf")

    return L


for i in range(t):
    x, d, k = xdk_commend[i]
    for ii in range(len(rd_map) // x):
        rd_map[x * (ii+1) - 1].rotate((-2 * d + 1) * k)

    rd_map = check_elemnet(rd_map)
    if rd_map == 0:
        break


if rd_map != 0:
    for i in range(n):
        for j in range(m):
            if not rd_map[i][j] < -10000:
                answer += rd_map[i][j]


print(answer)
