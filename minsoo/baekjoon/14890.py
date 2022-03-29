# https://www.acmicpc.net/problem/14890

n, l = map(int, input().split())
_map = [list(map(int, input().split())) for _ in range(n)]


def is_valid_road(road):
    is_occupied = [False for _ in road]
    i = 1
    while i < n:
        diff = abs((raw_diff := road[i] - road[i - 1]))
        if diff == 0:
            i += 1
        if diff > 1:
            return False
        if diff == 1:
            if raw_diff < 0:
                if i + l > n or any(road[i] != road[j] for j in range(i + 1, i + l)):
                    return False
                is_occupied[i : i + l] = [True for _ in range(l)]
                i += l
            else:
                if i - l < 0 or any(is_occupied[i - l : i]):
                    return False
                is_occupied[i - l : i] = [True for _ in range(l)]
                i += 1

    return True


print(sum(is_valid_road(road) for road in _map) + sum(is_valid_road(road) for road in zip(*_map)))
