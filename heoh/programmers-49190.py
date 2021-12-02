DY = [-1, -1, 0, 1, 1, 1, 0, -1]
DX = [0, 1, 1, 1, 0, -1, -1, -1]

def solution(arrows):
    visited_pos = set()
    visited_edge = set()

    curr_pos = (0, 0)
    visited_pos.add(curr_pos)

    n_rooms = 0
    for d in arrows:
        for _ in range(2):
            prev_pos = curr_pos
            curr_pos = (prev_pos[0] + (DX[d] / 2), prev_pos[1] + (DY[d] / 2))
            curr_edge = (prev_pos, curr_pos)

            if curr_pos in visited_pos and curr_edge not in visited_edge:
                n_rooms += 1

            visited_pos.add(curr_pos)
            visited_edge.add((prev_pos, curr_pos))
            visited_edge.add((curr_pos, prev_pos))

    return n_rooms
