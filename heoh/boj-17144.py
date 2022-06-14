R, C, T = map(int, input().split())
A = [list(map(int, input().split())) for _ in range(R)]

def find_all_dust_locs():
    locs = set()
    for r in range(R):
        for c in range(C):
            if A[r][c] > 0:
                locs.add((r, c))
    return locs

def find_air_purifier():
    for r in range(R):
        if A[r][0] == -1:
            return (r, 0)

def count_remaining_dusts(dust_locs):
    return sum(A[r][c] for r, c in dust_locs)

def propagate_all_dusts(dust_locs):
    org_A = [A[r].copy() for r in range(R)]
    # new_locs = set()
    for r, c in dust_locs:
        before_amount = org_A[r][c]
        diff = A[r][c] - before_amount
        child_amount = before_amount // 5
        n_children = 0
        for nr, nc in ((r-1, c), (r, c-1), (r+1, c), (r, c+1)):
            if 0 <= nr < R and 0 <= nc < C and A[nr][nc] != 0:
                A[nr][nc] += child_amount
                n_children += 1
        A[r][c] = before_amount - child_amount * n_children + diff

def run_air_purifier(air_purifier, dust_locs):
    ap_loc_up = air_purifier
    ap_loc_down = (air_purifier[0]+1, air_purifier[1])

    ar, ac = ap_loc_up
    for r in range(ar-1, 0, -1):
        A[r][0] = A[r-1][0]
    for c in range(0, C-1, 1):
        A[0][c] = A[0][c+1]
    for r in range(0, ar-1, 1):
        A[r][C-1] = A[r+1][C-1]
    for c in range(C-1, 0, -1):
        A[ar][c] = A[ar][c-1]
    A[ar][ac+1] = 0

    ar, ac = ap_loc_down
    for r in range(ar+1, R-1, 1):
        A[r][0] = A[r+1][0]
    for c in range(0, C-1, 1):
        A[R-1][c] = A[R-1][c+1]
    for r in range(R-1, ar+1, -1):
        A[r][C-1] = A[r-1][C-1]
    for c in range(C-1, 0, -1):
        A[ar][c] = A[ar][c-1]
    A[ar][ac+1] = 0


air_purifier = find_air_purifier()

for _ in range(T):
    dust_locs = find_all_dust_locs()
    propagate_all_dusts(dust_locs)
    run_air_purifier(air_purifier, dust_locs)

dust_locs = find_all_dust_locs()
print(count_remaining_dusts(dust_locs))
