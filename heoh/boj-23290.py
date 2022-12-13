from collections import Counter

DIR = [(0, -1), (-1, -1), (-1, 0), (-1, 1), (0, 1), (1, 1), (1, 0), (1, -1)]


class Solution:
    def solve(self):
        self.read_input()
        for _ in range(self.S):
            self.spell_duplication()
            self.move_all_fishes()
            self.move_shark()
            self.update_smell()
            self.finish_duplication()
        answer = self.count_all_fishes()
        print(answer)

    def read_input(self):
        self.M, self.S = map(int, input().split())
        self.fishes = Counter()
        for _ in range(self.M):
            f_x, f_y, d = map(int, input().split())
            self.fishes[f_y, f_x, d-1] += 1
        s_x, s_y = map(int, input().split())
        self.shark = [s_y, s_x]
        self.smell_time_map = Counter()
        self.duplicating_fishes = None
        self.t = 100

    def spell_duplication(self):
        self.duplicating_fishes = self.fishes.copy()

    def move_all_fishes(self):
        next_fishes = Counter()
        for fish in self.fishes:
            n_fishes = self.fishes[fish]
            fish = self.move_fish(fish)
            next_fishes[fish] += n_fishes
        self.fishes = next_fishes

    def move_fish(self, fish):
        x, y, d = fish
        for i in range(len(DIR)):
            nd = (d - i) % len(DIR)
            dy, dx = DIR[nd]
            nx, ny = x+dx, y+dy
            if self.check_movable_fish(nx, ny):
                return nx, ny, nd
        return x, y, d

    def check_movable_fish(self, x, y):
        WIDTH = HEIGHT = 4
        if not (1 <= x <= WIDTH):
            return False
        if not (1 <= y <= HEIGHT):
            return False
        if [x, y] == self.shark:
            return False
        if self.smell_time_map[x, y] >= self.t - 2:
            return False
        return True

    def move_shark(self):
        fish_map = Counter()
        for x, y, d in self.fishes:
            fish_map[x, y] += self.fishes[x, y, d]

        def dfs(x, y, dirs: list, visited: set, score):
            if len(dirs) == 3:
                return score, dirs

            best_dirs = None
            best_score = -1
            for d in [2,0,6,4]:
                dy, dx = DIR[d]
                nx, ny = x+dx, y+dy

                if not self.check_movable_shark(nx, ny):
                    continue

                n_fishes = fish_map[nx, ny] if (nx, ny) not in visited else 0
                next_visited = set(visited)
                next_visited.add((nx, ny))
                next_score, next_dirs = dfs(nx, ny, dirs + [d], next_visited, score + n_fishes)

                if next_score > best_score:
                    best_score = next_score
                    best_dirs = next_dirs

            return best_score, best_dirs

        x, y = self.shark
        best_score, best_dirs = dfs(x, y, [], set(), 0)

        for d in best_dirs:
            dy, dx = DIR[d]
            self.shark[0] += dx
            self.shark[1] += dy
            x, y = self.shark
            if fish_map[x, y] > 0:
                self.remove_fishes(x, y)

    def check_movable_shark(self, x, y):
        WIDTH = HEIGHT = 4
        if not (1 <= x <= WIDTH):
            return False
        if not (1 <= y <= HEIGHT):
            return False
        return True

    def remove_fishes(self, x, y):
        next_fishes = Counter()
        for fish in self.fishes:
            fx, fy, d = fish
            if (fx, fy) == (x, y):
                continue
            next_fishes[fish] = self.fishes[fish]
        self.fishes = next_fishes
        self.smell_time_map[x, y] = self.t

    def update_smell(self):
        self.t += 1

    def finish_duplication(self):
        self.fishes += self.duplicating_fishes
        self.duplicating_fishes = []

    def count_all_fishes(self):
        return sum(self.fishes.values())


if __name__ == '__main__':
    Solution().solve()
