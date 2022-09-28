from queue import deque

DIRS = [(-1, 0), (0, -1), (1, 0), (0, 1)]

def coord_to_index(x):
    return int(x) - 1


N, M, F = map(int, input().split())
board = [list(map(int, input().split())) for _ in range(N)]
sy, sx = map(coord_to_index, input().split())
orders = [list(map(coord_to_index, input().split())) for _ in range(M)]


def simulate(board, orders, y, x, fuel):
    remaining_order_ids = set(range(len(orders)))

    cy, cx = y, x

    while remaining_order_ids:
        target_id, dist = find_closest_order(board, orders, remaining_order_ids, cy, cx)
        if target_id is None:
            return -1
        remaining_order_ids.remove(target_id)

        sy, sx, ty, tx = orders[target_id]
        cy, cx = sy, sx
        fuel -= dist
        if fuel < 0:
            return -1

        dist = get_distance(board, cy, cx, ty, tx)
        if dist is None:
            return -1

        cy, cx = ty, tx
        fuel -= dist
        if fuel < 0:
            return -1

        fuel += dist * 2

    return fuel


def find_closest_order(board, orders, remaining_order_ids, y, x):
    order_dict = {}
    for order_id in remaining_order_ids:
        sy, sx, ty, tx = orders[order_id]
        order_dict[sy, sx] = order_id

    candidates = []
    queue = deque([(0, y, x)])
    visited = set([(y, x)])

    while queue:
        t, cy, cx = queue.popleft()

        if candidates and candidates[0][0] < t:
            break
        if (cy, cx) in order_dict:
            candidates.append((t, cy, cx))

        if not candidates:
            for (dy, dx) in DIRS:
                ny = cy + dy
                nx = cx + dx
                if ((ny, nx) in visited) or (not 0 <= ny < N) or (not 0 <= nx < N) or (board[ny][nx] == 1):
                    continue
                queue.append((t+1, ny, nx))
                visited.add((ny, nx))

    if candidates:
        candidates = sorted(candidates, key=lambda x: (x[1], x[2]))
        t, cy, cx = candidates[0]
        order_id = order_dict[cy, cx]

        return order_id, t
    else:
        return None, None


def get_distance(board, y, x, ty, tx):
    pos_queue = deque([(0, y, x)])
    visited = set([(y, x)])
    while pos_queue:
        t, cy, cx = pos_queue.popleft()
        if (cy, cx) == (ty, tx):
            return t
        for (dy, dx) in DIRS:
            ny = cy + dy
            nx = cx + dx
            if ((ny, nx) in visited) or (not 0 <= ny < N) or (not 0 <= nx < N) or (board[ny][nx] == 1):
                continue
            pos_queue.append((t+1, ny, nx))
            visited.add((ny, nx))
    return None


print(simulate(board, orders, sy, sx, F))
