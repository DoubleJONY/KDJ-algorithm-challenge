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
        self.fishes = []
        for _ in range(self.M):
            f_x, f_y, d = map(int, input().split())
            fish = [f_y, f_x, d-1]
            self.fishes.append(fish)
        s_x, s_y = map(int, input().split())
        self.shark = [s_y, s_x]
        self.smell_time_map = Counter()
        self.duplicating_fishes = []
        self.t = 0

    def spell_duplication(self):
        self.duplicating_fishes = []
        for f in self.fishes:
            self.duplicating_fishes.append(f.copy())

    def move_all_fishes(self):
        for fish in self.fishes:
            self.move_fish(fish)

    def move_fish(self, fish):
        x, y, d = fish
        for i in range(len(DIR)):
            nd = (d - i) % len(DIR)
            dy, dx = DIR[nd]
            nx, ny = x+dx, y+dy
            if self.check_movable_fish(nx, ny):
                fish[0] = nx
                fish[1] = ny
                fish[2] = nd
                break

    def check_movable_fish(self, x, y):
        WIDTH = HEIGHT = 4
        if not (1 <= x <= WIDTH):
            return False
        if not (1 <= y <= HEIGHT):
            return False
        if [x, y] == self.shark:
            return False
        if self.smell_time_map[x, y] - 2 >= self.t:
            return False
        return True

    def move_shark(self):
        fish_map = Counter()
        for x, y, d in self.fishes:
            fish_map[x, y] += 1

        def dfs(x, y, dirs: list, visited: set, score):
            if len(dirs) == 3:
                return score, dirs

            best_dirs = None
            best_score = -1
            for d in [0,2,4,6]:
                dy, dx = DIR[d]
                nx, ny = x+dx, y+dy

                if not self.check_movable_shark(nx, ny):
                    continue
                if (nx, ny) in visited:
                    continue

                visited.add((nx, ny))
                next_score, next_dirs = dfs(nx, ny, dirs + [d], visited, score + fish_map[nx, ny])
                visited.remove((nx, ny))

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
        self.fishes = list(filter(lambda fish: fish[0] != x or fish[1] != y, self.fishes))
        self.smell_time_map[x, y] = self.t

    def update_smell(self):
        self.t += 1

    def finish_duplication(self):
        self.fishes += self.duplicating_fishes
        self.duplicating_fishes = []

    def count_all_fishes(self):
        return len(self.fishes)


if __name__ == '__main__':
    Solution().solve()
