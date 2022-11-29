# https://www.acmicpc.net/problem/21611


DR = [0, 1, 0, -1]
DC = [-1, 0, 1, 0]

TRANSLATION = {1: 3, 2: 1, 3: 0, 4: 2}


def convert_grid_to_queue(
    grid: list[list[int]], *, starting_direction: int = 0
):
    if not len(grid) % 2:
        raise ValueError("Grid size must be an odd number")

    r = c = len(grid) // 2

    d = starting_direction

    def valid(r: int, c: int):
        return 0 <= r < len(grid) and 0 <= c < len(grid[0])

    q = []
    for n in range(1, len(grid) + 1):
        for _ in range(2):
            for _ in range(n):
                if not valid(r, c):
                    return q

                q.append(grid[r][c])

                r += DR[d]
                c += DC[d]

            d = (d + 1) % 4

    return q


def destroy_beads_in_scope(q: list[int], *, d: int, s: int):
    begin = 2 * d + 1

    index_fn = lambda n: begin * (n + 1) + 4 * n * (n + 1)
    fragments = [q[index_fn(n) + 1 : index_fn(n + 1)] for n in range(s)]

    last_index = index_fn(s)
    if last_index < len(q):
        fragments.append(q[last_index:])

    _q = q[:begin]
    for fragment in fragments:
        _q.extend(fragment)

    return _q


def cancel_beads(q: list[int]):
    if not q:
        return q, 0

    _q = []
    canceled_sum = 0

    stk = []
    for bead in q + [0]:
        if stk and stk[-1] != bead:
            if len(stk) < 4:
                _q.extend(stk)
            else:
                canceled_sum += stk[-1] * len(stk)

            stk = [bead]

        else:
            stk.append(bead)

    if canceled_sum:
        __q, _canceled_sum = cancel_beads(_q)
        return __q, canceled_sum + _canceled_sum

    return _q, canceled_sum


def transform_beads(q: list[int], *, maxlen: int):
    _q = []
    stk = []
    for bead in q[1:] + [0]:
        if stk and stk[-1] != bead:
            _q.append(len(stk))
            _q.append(stk[-1])

            stk = [bead]

        else:
            stk.append(bead)

        if len(_q) >= maxlen:
            break

    return [0] + _q[: maxlen - 1]


size, num_instructions = map(int, input().split())
grid = [list(map(int, input().split())) for _ in range(size)]
instructions = [
    list(map(int, input().split())) for _ in range(num_instructions)
]

sum_of_canceled_beads = 0

q = convert_grid_to_queue(grid, starting_direction=0)
for direction, scope in instructions:
    q = destroy_beads_in_scope(q, d=TRANSLATION[direction], s=scope)
    q, canceled_sum = cancel_beads(q)
    q = transform_beads(q, maxlen=size ** 2)

    sum_of_canceled_beads += canceled_sum

print(sum_of_canceled_beads)
