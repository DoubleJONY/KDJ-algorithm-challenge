from collections import defaultdict

DIRS = [(-1, 0), (0, -1), (1, 0), (0, 1)]


class Solution:
    def read_input(self):
        self.N = int(input())
        self.A = [list(map(int, input().split())) for _ in range(self.N**2)]

    def init(self):
        N = self.N
        self.position_of = {}
        self.empty_count = {(r, c): 4 for c in range(N) for r in range(N)}
        self.empty_cells = set(self.empty_count.keys())
        for r in range(self.N):
            self.empty_count[r, 0] -= 1
            self.empty_count[r, N-1] -= 1
            self.empty_count[0, r] -= 1
            self.empty_count[N-1, r] -= 1

    def solve(self):
        self.init()

        N = self.N

        for i, *friends in self.A:
            best_cell = None
            candidates = self.get_score_dict(friends)
            if candidates:
                best_score = max(candidates.values())
                best_cells = [pos for pos, score in candidates.items() if score == best_score]
                if len(best_cells) > 1:
                    candidates = {pos: self.empty_count[pos] for pos in best_cells}
                    best_score = max(candidates.values())
                    best_cells = [pos for pos, score in candidates.items() if score == best_score]
                    if len(best_cells) > 1:
                        best_cells = sorted(best_cells)
                        best_cell = best_cells[0]
                    else:
                        best_cell = best_cells[0]
                else:
                    best_cell = best_cells[0]
            else:
                best = 0
                for pos in sorted(self.empty_cells):
                    if self.empty_count[pos] > best:
                        best = self.empty_count[pos]
                        best_cell = pos
                        if best == 4:
                            break
            
            print(best_cell)
            self.put_student(i, best_cell)

    def get_score_dict(self, friends):
        N = self.N

        scores = defaultdict(lambda: 0)
        for f in filter(self.check_registered_student, friends):
            fr, fc = self.position_of[f]
            for n_pos in ((fr+dr, fc+dc) for dr, dc in DIRS):
                if self.check_empty_cell(n_pos):
                    continue
                scores[n_pos] += 1
        return scores

    def check_registered_student(self, i):
        return i in self.position_of

    def check_empty_cell(self, pos):
        return pos in self.empty_cells

    def put_student(self, i, pos):
        N = self.N
        self.position_of[i] = pos
        r, c = pos
        for pos in ((r+dr, c+dc) for dr, dc in DIRS):
            nr, nc = pos
            if 0 <= nr < N and 0 <= nc < N:
                self.empty_count[pos] -= 1
        self.empty_cells.remove(pos)


def main():
    solver = Solution()
    solver.read_input()
    solver.solve()


main()
