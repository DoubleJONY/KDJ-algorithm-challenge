# https://www.acmicpc.net/problem/19238

from collections import defaultdict, deque
from typing import Union


class Taxi:
    def __init__(self, r: int, c: int, fuel: int):
        self.r = r
        self.c = c
        self.fuel = fuel

    def __call__(self, r: int, c: int):
        self.r = r
        self.c = c
        return self

    def __iadd__(self, dist: int):
        self.fuel += dist
        return self

    def __isub__(self, dist: int):
        self.fuel -= dist
        return self

    def dists_to(
        self,
        grid: list[list[int]],
        idx_to_dest: Union[tuple[int, int], dict[int, tuple[int, int]]]
    ):
        if isinstance(idx_to_dest, tuple):
            idx_to_dest = {0: idx_to_dest}

        dest_to_idx = defaultdict(list)
        for k, v in idx_to_dest.items():
            dest_to_idx[v].append(k)

        q = deque([(self.r, self.c, 0)])
        visited = {(self.r, self.c)}

        dists_to = {}
        if (self.r, self.c) in dest_to_idx:
            for idx in dest_to_idx[(self.r, self.c)]:
                dists_to[idx] = (0, self.r, self.c, idx)

        while q:
            r, c, dist = q.popleft()

            if len(dists_to) == len(idx_to_dest):
                return dists_to

            for dr, dc in [(-1, 0), (0, 1), (1, 0), (0, -1)]:
                nr, nc = r + dr, c + dc

                def is_valid(r: int, c: int):
                    return 0 <= r < len(grid) and 0 <= c < len(grid[0])

                if not is_valid(nr, nc):
                    continue
                if grid[nr][nc] or (nr, nc) in visited:
                    continue

                q.append((nr, nc, dist + 1))
                visited.add((nr, nc))

                if (nr, nc) in dest_to_idx:
                    for idx in dest_to_idx[(nr, nc)]:
                        dists_to[idx] = (dist + 1, nr, nc, idx)

        return dists_to


def stdio_bj19238():
    """
    첫 줄에 N, M, 그리고 초기 연료의 양이 주어진다. (2 ≤ N ≤ 20, 1 ≤ M ≤ N2, 1 ≤ 초기 연료 ≤ 500,000)
    연료는 무한히 많이 담을 수 있기 때문에, 초기 연료의 양을 넘어서 충전될 수도 있다.

    다음 줄부터 N개의 줄에 걸쳐 백준이 활동할 영역의 지도가 주어진다.
    0은 빈칸, 1은 벽을 나타낸다.

    다음 줄에는 백준이 운전을 시작하는 칸의 행 번호와 열 번호가 주어진다.
    행과 열 번호는 1 이상 N 이하의 자연수이고, 운전을 시작하는 칸은 빈칸이다.

    그다음 줄부터 M개의 줄에 걸쳐 각 승객의 출발지의 행과 열 번호, 그리고 목적지의 행과 열 번호가 주어진다.
    모든 출발지와 목적지는 빈칸이고, 모든 출발지는 서로 다르며, 각 손님의 출발지와 목적지는 다르다.
    """

    grid_size, num_passengers, fuel = map(int, input().split())
    grid = [list(map(int, input().split())) for _ in range(grid_size)]

    def num_to_idx(num: str):
        return int(num) - 1

    r_taxi, c_taxi = map(num_to_idx, input().split())

    passenger_info = {
        i: tuple(map(num_to_idx, input().split()))
        for i in range(num_passengers)
    }

    return grid, Taxi(r_taxi, c_taxi, fuel=fuel), passenger_info


def main():
    grid, taxi, passenger_info = stdio_bj19238()

    while passenger_info:
        passenger_locs = {i: (r, c) for i, (r, c, *_) in passenger_info.items()}
        dists_to_passengers = taxi.dists_to(grid, passenger_locs)

        if len(dists_to_passengers) != len(passenger_locs):
            print(-1)
            return

        dist, r, c, i = sorted(dists_to_passengers.values())[0]

        if taxi.fuel < dist:
            print(-1)
            return

        taxi = taxi(r, c)
        taxi -= dist

        r, c = passenger_info[i][2:]

        dist_to = taxi.dists_to(grid, (r, c))

        if not dist_to:
            print(-1)
            return

        dist = dist_to[0][0]

        if taxi.fuel < dist:
            print(-1)
            return

        taxi = taxi(r, c)
        taxi += dist

        passenger_info.pop(i)

    print(taxi.fuel)


if __name__ == "__main__":
    main()
