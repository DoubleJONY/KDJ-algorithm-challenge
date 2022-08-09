# https://www.acmicpc.net/problem/17825


def initialize_game():
    node_values = [
        0,              # [0]
        2, 4, 6, 8,     # [1, 4]
        10,             # [5]
        12, 14, 16, 18, # [6, 9]
        13, 16, 19,     # [10, 12]
        20,             # [13]
        22, 24, 26, 28, # [14, 17]
        22, 24,         # [18, 19]
        30,             # [20]
        32, 34, 36, 38, # [21, 24]
        28, 27, 26,     # [25, 27]
        25,             # [28]
        30, 35,         # [29, 30]
        40              # [31]
    ]

    def process(connection):
        if isinstance(connection, list):
            return connection
        if isinstance(connection, int):
            return [connection, None]
        return [None, None]

    node_connections = [
        1,
        2, 3, 4, 5,
        [6, 10],
        7, 8, 9, 13,
        11, 12, 28,
        [14, 18],
        15, 16, 17, 20,
        19, 28,
        [21, 25],
        22, 23, 24, 31,
        26, 27, 28,
        29,
        30, 31,
        None
    ]
    node_connections = [
        process(connection) for connection in node_connections
    ]

    piece_locations = [0 for _ in range(4)]

    return node_values, node_connections, piece_locations


def dfs(
    node_values: list[int],
    node_connections: list,
    piece_locations: list[int],
    distances: list[int],
    idx: int = 0,
    max_sum: int = 0,
):
    if idx == len(distances):
        return max_sum

    sums = []
    for p_idx, piece_location in enumerate(piece_locations):
        if piece_location is None:
            continue

        red_next, blue_next = node_connections[piece_location]

        next_idx = red_next
        if blue_next is not None:
            next_idx = blue_next

        distance_to_move = distances[idx] - 1 
        while distance_to_move:
            if next_idx is None:
                break

            next_idx = node_connections[next_idx][0]
            distance_to_move -= 1

        if next_idx is not None and next_idx in piece_locations:
            continue

        piece_locations[p_idx] = next_idx
        _sum = dfs(
            node_values,
            node_connections,
            piece_locations,
            distances,
            idx + 1,
            max_sum + (
                node_values[next_idx] if next_idx is not None else 0
            ),
        )
        piece_locations[p_idx] = piece_location

        sums.append(_sum)

    return max(sums)


def play():
    node_values, node_connections, piece_locations = initialize_game()
    distances = list(map(int, input().split()))

    max_sum = dfs(
        node_values,
        node_connections,
        piece_locations, 
        distances
    )

    print(max_sum)


if __name__ == "__main__":
    play()
