# https://www.acmicpc.net/problem/15684


from dataclasses import dataclass, field


@dataclass
class LadderGame:
    r: int
    c: int
    connections: list[list[int, int]]
    ladder: list[bool] = field(init=False)

    def __post_init__(self):
        ladder = [False for _ in range(10 * (self.r + 1) + self.c + 1)]
        for r, c in self.connections:
            ladder[10 * r + c] = True
        self.ladder = ladder

    def ends_if_starts_from(self, c: int):
        for r in range(1, self.r + 1):
            if self.ladder[10 * r + c]:
                c += 1
            elif self.ladder[10 * r + c - 1]:
                c -= 1
        return c

    def is_identity(self):
        for c in range(1, self.c + 1):
            if self.ends_if_starts_from(c) != c:
                return False
        return True

    def can_be_identity(self, start: int = 11, num_connections_added: int = 0):
        if self.is_identity():
            return num_connections_added
        if start == 10 * self.r + self.c:
            return -1
        if num_connections_added == 3:
            return -1

        min_added = 4
        for connection in range(start, 10 * self.r + self.c):
            if (
                self.ladder[connection - 1]
                or self.ladder[connection]
                or self.ladder[connection + 1]
            ):
                continue

            self.ladder[connection] = True
            _num_connections_added = self.can_be_identity(
                start=connection + 1,
                num_connections_added=num_connections_added + 1,
            )
            self.ladder[connection] = False

            if _num_connections_added > -1:
                min_added = min(min_added, _num_connections_added)

        return -1 if min_added == 4 else min_added


c, n, r = map(int, input().split())
connections = [list(map(int, input().split())) for _ in range(n)]
ladder_game = LadderGame(r=r, c=c, connections=connections)

print(ladder_game.can_be_identity())
