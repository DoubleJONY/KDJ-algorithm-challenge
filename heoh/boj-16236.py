from collections import deque


DIRS = [(-1, 0), (0, -1), (1, 0), (0, 1)]


def main():
    N = int(input())
    C = [list(map(int, input().split())) for _ in range(N)]

    r, c = find_baby_shark_position(C)
    C[r][c] = 0

    t = 0
    pos = (r, c)
    level = 2
    exp = 0
    while targets := find_targets(N, C, pos, level):
        target_dist, target_pos = get_nearest_target(targets)

        # Consume target
        r, c = target_pos
        C[r][c] = 0
        pos = target_pos
        t += target_dist

        exp += 1
        if exp == level:
            level += 1
            exp = 0

    print(t)


def find_baby_shark_position(C):
    for r, cols in enumerate(C):
        for c, value in enumerate(cols):
            if value == 9:
                return (r, c)


def find_targets(N, C, start_pos, level):
    targets = []
    min_dist = None

    start_state = (0, start_pos)
    queue = deque([start_state])
    visited = set([start_pos])
    while queue:
        dist, pos = queue.popleft()

        r, c = pos
        if C[r][c] > level:
            continue
        if 0 < C[r][c] < level:
            if (min_dist is not None) and (dist > min_dist):
                break
            targets.append((dist, pos))
            min_dist = dist

        for d in DIRS:
            next_pos = (pos[0] + d[0], pos[1] + d[1])
            next_dist = dist + 1
            next_state = (next_dist, next_pos)

            if not (0 <= next_pos[0] < N and 0 <= next_pos[1] < N):
                continue
            if next_pos in visited:
                continue

            queue.append(next_state)
            visited.add(next_pos)


    return targets


def get_nearest_target(targets):
    return sorted(targets)[0]


main()
