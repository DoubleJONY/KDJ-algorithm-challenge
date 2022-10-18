# https://www.acmicpc.net/problem/20057

from itertools import count

DR = [0, 1, 0, -1]
DC = [-1, 0, 1, 0]


def stdin_bj20057() -> tuple[int, list[list[int]]]:
    """
    첫째 줄에 격자의 크기 N이 주어진다.
    둘째 줄부터 N개의 줄에는 격자의 각 칸에 있는 모래가 주어진다. r번째 줄에서 c번째 주어지는 정수는 A[r][c] 이다.
    """

    size = int(input())
    grid = [list(map(int, input().split())) for _ in range(size)]

    return size, grid


def spread(grid: list[list[int]], r: int, c: int, d: int) -> tuple[list[list[int]], int]:
    def is_valid(r: int, c: int):
        return 0 <= r < len(grid) and 0 <= c < len(grid[0])

    total_amount = amount = got_out = grid[r][c]
    grid[r][c] = 0

    tmp = int(total_amount * 0.05)
    amount -= tmp
    if is_valid(r + 2 * DR[d], c + 2 * DC[d]):
        grid[r + 2 * DR[d]][c + 2 * DC[d]] += tmp
        got_out -= tmp

    cw90d = (d - 1) % 4
    ccw90d = (d + 1) % 4

    tmp = int(total_amount * 0.1)
    amount -= 2 * tmp
    if is_valid(r + DR[d] + DR[cw90d], c + DC[d] + DC[cw90d]):
        grid[r + DR[d] + DR[cw90d]][c + DC[d] + DC[cw90d]] += tmp
        got_out -= tmp
    if is_valid(r + DR[d] + DR[ccw90d], c + DC[d] + DC[ccw90d]):
        grid[r + DR[d] + DR[ccw90d]][c + DC[d] + DC[ccw90d]] += tmp
        got_out -= tmp

    tmp = int(total_amount * 0.07)
    amount -= 2 * tmp
    if is_valid(r + DR[cw90d], c + DC[cw90d]):
        grid[r + DR[cw90d]][c + DC[cw90d]] += tmp
        got_out -= tmp
    if is_valid(r + DR[ccw90d], c + DC[ccw90d]):
        grid[r + DR[ccw90d]][c + DC[ccw90d]] += tmp
        got_out -= tmp

    tmp = int(total_amount * 0.02)
    amount -= 2 * tmp
    if is_valid(r + 2 * DR[cw90d], c + 2 * DC[cw90d]):
        grid[r + 2 * DR[cw90d]][c + 2 * DC[cw90d]] += tmp
        got_out -= tmp
    if is_valid(r + 2 * DR[ccw90d], c + 2 * DC[ccw90d]):
        grid[r + 2 * DR[ccw90d]][c + 2 * DC[ccw90d]] += tmp
        got_out -= tmp

    tmp = int(total_amount * 0.01)
    amount -= 2 * tmp
    if is_valid(r + DR[cw90d] - DR[d], c + DC[cw90d] - DC[d]):
        grid[r + DR[cw90d] - DR[d]][c + DC[cw90d] - DC[d]] += tmp
        got_out -= tmp
    if is_valid(r + DR[ccw90d] - DR[d], c + DC[ccw90d] - DC[d]):
        grid[r + DR[ccw90d] - DR[d]][c + DC[ccw90d] - DC[d]] += tmp
        got_out -= tmp

    if is_valid(r + DR[d], c + DC[d]):
        grid[r + DR[d]][c + DC[d]] += amount
        got_out -= amount

    return grid, got_out


def main():
    size, grid = stdin_bj20057()

    amount_out = 0

    r = c = size // 2

    for t in count():
        dist = t // 2 + 1
        d = t % 4

        for _ in range(dist):
            r += DR[d]
            c += DC[d]

            grid, got_out = spread(grid, r, c, d)

            amount_out += got_out

            if r == 0 and c == 0:
                print(amount_out)
                return


if __name__ == "__main__":
    main()
