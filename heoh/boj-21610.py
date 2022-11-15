DIRS = [(0, -1), (-1, -1), (-1, 0), (-1, 1), (0, 1), (1, 1), (1, 0), (1, -1)]
NEIGHBOR_DIRS = [(-1, -1), (-1, 1), (1, 1), (1, -1)]

N, M = map(int, input().split())
A = [list(map(int, input().split())) for _ in range(N)]
D, S = map(list, zip(*(map(int, input().split()) for _ in range(M))))


class Solution:
    def solve(self):
        self.buckets = A
        self.clouds = [[N-1, 0], [N-1, 1], [N-2, 0], [N-2, 1]]
        self.candidates = set()
        for r in range(N):
            for c in range(N):
                if self.buckets[r][c] >= 2:
                    self.candidates.add((r, c))

        for i in range(M):
            self.simulate_turn(D[i] - 1, S[i])

        return self.count_waters()

    def simulate_turn(self, d, s):
        self.move_all_clouds(d, s)
        added_locs = self.fall_rain_all()
        removed_locs = self.remove_clouds()
        self.copy_water(added_locs)
        self.add_candidates(added_locs)
        self.make_clouds(excepted=removed_locs)

    def move_all_clouds(self, d, s):
        for cloud in self.clouds:
            cloud[0] = (cloud[0] + DIRS[d][0] * s) % N
            cloud[1] = (cloud[1] + DIRS[d][1] * s) % N

    def fall_rain_all(self):
        for cloud in self.clouds:
            r, c = cloud
            self.buckets[r][c] += 1
        return self.clouds

    def remove_clouds(self):
        removed_clouds = self.clouds
        self.clouds = []
        return removed_clouds

    def copy_water(self, locs):
        def bucket_filter(loc):
            r, c = loc
            return self.buckets[r][c] > 0

        for r, c in locs:
            self.buckets[r][c] += len(list(filter(bucket_filter, self.iter_neighbors(r, c))))

    def iter_neighbors(self, r, c):
        for dr, dc in NEIGHBOR_DIRS:
            nr, nc = r + dr, c + dc
            if not 0 <= nr < N: continue
            if not 0 <= nc < N: continue
            yield nr, nc
        return

    def add_candidates(self, locs):
        for r, c in locs:
            if self.buckets[r][c] >= 2:
                self.candidates.add((r, c))

    def make_clouds(self, excepted):
        excepted = set([(r, c) for r, c in excepted])
        for loc in (self.candidates - excepted):
            r, c = loc
            self.buckets[r][c] -= 2
            if self.buckets[r][c] < 2:
                self.candidates.remove(loc)
            self.clouds.append([r, c])

    def count_waters(self):
        n = 0
        for r in range(N):
            for c in range(N):
                n += self.buckets[r][c]
        return n


answer = Solution().solve()
print(answer)
