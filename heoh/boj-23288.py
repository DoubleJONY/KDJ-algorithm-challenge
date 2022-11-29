UP = 0
DOWN = 1
LEFT = 2
RIGHT = 3
TOP = 4
BOTTOM = 5

DY = [-1, 1, 0, 0]
DX = [0, 0, -1, 1]

REVERSED = {
    UP: DOWN,
    DOWN: UP,
    LEFT: RIGHT,
    RIGHT: LEFT,
}
ROTATED_CW = {
    UP: RIGHT,
    RIGHT: DOWN,
    DOWN: LEFT,
    LEFT: UP,
}
ROTATED_CCW = {
    UP: LEFT,
    LEFT: DOWN,
    DOWN: RIGHT,
    RIGHT: UP,
}


class Solution:
    def read_input(self):        
        self.N, self.M, self.K = map(int, input().split())
        self.A = [list(map(int, input().split())) for _ in range(self.N)]

    def init(self):
        self.y, self.x = 0, 0
        self.dice = [2, 5, 4, 3, 1, 6]
        self.facing = RIGHT
        self.score = 0
        self.init_group_size()

    def solve(self):
        self.read_input()
        self.init()

        for _ in range(self.K):
            self.do_turn()

        print(self.score)

    def do_turn(self):
        N, M = self.N, self.M
        y, x = self.y, self.x

        # Step 1
        ny, nx = y + DY[self.facing], x + DX[self.facing]
        if not (0 <= ny < N and 0 <= nx < M):
            self.facing = REVERSED[self.facing]
            ny, nx = y + DY[self.facing], x + DX[self.facing]

        self.y, self.x = ny, nx
        self.roll_dice(self.facing)
        
        y, x = self.y, self.x

        # Step 2
        self.score += self.A[y][x] * self.group_size[y][x]

        # Step 3
        if self.dice[BOTTOM] > self.A[y][x]:
            self.facing = ROTATED_CW[self.facing]
        elif self.dice[BOTTOM] < self.A[y][x]:
            self.facing = ROTATED_CCW[self.facing]

    def roll_dice(self, dir):
        dice = self.dice
        if dir == UP:
            dice[UP], dice[TOP], dice[DOWN], dice[BOTTOM] = dice[TOP], dice[DOWN], dice[BOTTOM], dice[UP]
        elif dir == RIGHT:
            dice[TOP], dice[LEFT], dice[BOTTOM], dice[RIGHT] = dice[LEFT], dice[BOTTOM], dice[RIGHT], dice[TOP]
        elif dir == DOWN:
            dice[DOWN], dice[BOTTOM], dice[UP], dice[TOP] = dice[TOP], dice[DOWN], dice[BOTTOM], dice[UP]
        elif dir == LEFT:
            dice[TOP], dice[RIGHT], dice[BOTTOM], dice[LEFT] = dice[RIGHT], dice[BOTTOM], dice[LEFT], dice[TOP]

    def init_group_size(self):
        A = self.A
        N, M = self.N, self.M
        group_size = [[0] * M for _ in range(N)]

        def dfs(group, v, y, x):
            if (y, x) in group:
                return
            if A[y][x] != v:
                return
            group.add((y, x))
            for d in [UP, DOWN, LEFT, RIGHT]:
                ny, nx = y + DY[d], x + DX[d]
                if not (0 <= ny < N and 0 <= nx < M):
                    continue
                dfs(group, v, ny, nx)


        for y in range(N):
            for x in range(M):
                if group_size[y][x] != 0:
                    continue

                group = set()
                dfs(group, A[y][x], y, x)
                for (cy, cx) in group:
                    group_size[cy][cx] = len(group)

        self.group_size = group_size


Solution().solve()
