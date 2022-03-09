from collections import deque, namedtuple


State = namedtuple('State', ['pos', 'moves'])

class Solution:
    def snakesAndLadders(self, board: List[List[int]]) -> int:
        self.init(board)

        queue = deque()
        queue.append(State(1, 0))

        visited = set()
        while queue:
            curr_state = queue.popleft()
            curr_pos, curr_moves = curr_state

            if curr_pos in visited:
                continue

            visited.add(curr_pos)

            for dice in range(1, 7):
                next_pos = self.jump(curr_pos + dice)
                next_moves = curr_moves + 1
                
                if not self.is_valid(next_pos):
                    continue
                elif self.is_goal(next_pos):
                    return next_moves
                else:
                    queue.append(State(next_pos, next_moves))

        return -1

    def init(self, board: List[List[int]]) -> None:
        n = len(board)

        self.board = board
        self.n = n
        self.size = n * n
        self.jump_cache = {}

    def jump(self, index: int) -> int:
        if index in self.jump_cache:
            return self.jump_cache[index]

        row, col = self.index_to_position(index)
        cell_value = self.board[row][col]

        jumped_index = index
        if cell_value != -1:
            jumped_index = cell_value

        self.jump_cache[index] = jumped_index
        return self.jump_cache[index]

    def index_to_position(self, index: int) -> Tuple[int, int]:
        n = self.n
        i = index - 1
        row = (n - 1) - (i // n)
        if (n - row) % 2 == 1:
            col = i % n
        else:
            col = (n - 1) - (i % n)
        return row, col

    def is_valid(self, index: int) -> bool:
        return 1 <= index <= self.size

    def is_goal(self, index: int) -> bool:
        return index == self.size
