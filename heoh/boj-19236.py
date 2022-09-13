DIRS = [
    None,
    [-1, 0],
    [-1, -1],
    [0, -1],
    [1, -1],
    [1, 0],
    [1, 1],
    [0, 1],
    [-1, 1],
]
NEXT_DIR = [None, 2, 3, 4, 5, 6, 7, 8, 1]


def main():
    fish_map = []
    for _ in range(4):
        fish_map += list(map(int, input().split()))

    score = fish_map[0]
    fish_map[0] = -1
    initial_state = (score, fish_map)

    answer = dfs(initial_state)
    print(answer)


def dfs(state):
    score, fish_map = state
    curr_pos = get_shark(fish_map)
    curr_idx, curr_dir = curr_pos

    best_score = score

    fish_map = move_fishes(fish_map)

    while (next_pos := get_next_pos(curr_pos)) is not None:
        next_idx, next_dir = next_pos
        if fish_map[next_idx] > 0:
            next_score = score + fish_map[next_idx]
            next_fish_map = fish_map.copy()
            next_fish_map[next_idx] = -1
            next_fish_map[curr_idx] = 0
            next_state = (next_score, next_fish_map)

            next_score = dfs(next_state)

            best_score = max(best_score, next_score)

        curr_pos = next_pos

    return best_score

def get_shark(fish_map):
    for r in range(4):
        for c in range(4):
            idx = ((r * 4) + c) * 2
            if fish_map[idx] == -1:
                return idx, fish_map[idx + 1]
    return None


def move_fishes(fish_map):
    fish_dict = {id: idx * 2 for idx, id in enumerate(fish_map[0::2]) if id > 0}
    for fish_id in sorted(fish_dict.keys()):
        fish_idx = fish_dict[fish_id]
        fish_dir = fish_map[fish_idx + 1]
        fish_pos = (fish_idx, fish_dir)
        for _ in range(8):
            next_pos = get_next_pos(fish_pos)
            if next_pos is not None:
                next_idx, next_dir = next_pos
                if fish_map[next_idx] >= 0:
                    fish_map[next_idx], fish_map[fish_idx] = fish_map[fish_idx], fish_map[next_idx]
                    fish_map[next_idx+1], fish_map[fish_idx+1] = fish_map[fish_idx+1], fish_map[next_idx+1]
                    if fish_map[next_idx] > 0:
                        fish_dict[fish_map[next_idx]] = next_idx
                    if fish_map[fish_idx] > 0:
                        fish_dict[fish_map[fish_idx]] = fish_idx
                    break
            fish_dir = NEXT_DIR[fish_dir]
            fish_pos = (fish_idx, fish_dir)
            fish_map[fish_idx + 1] = fish_dir
    return fish_map


def get_next_pos(pos):
    idx, dir = pos
    dr, dc = DIRS[dir]
    r = (idx // 2) // 4
    c = (idx // 2) % 4
    nr = r + dr
    nc = c + dc
    nidx = (nr * 4 + nc) * 2
    return (nidx, dir) if 0 <= nr < 4 and 0 <= nc < 4 else None


main()
