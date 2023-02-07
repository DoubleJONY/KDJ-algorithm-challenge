from __future__ import annotations
import math
import sys
limit_number = 1500000
sys.setrecursionlimit(limit_number)

class Solution:
    def __init__(self):
        cache = [None] * (2 * 10**4)
        for i in range(1, 15):
            cache[(1 << i) - 1] = (i, 1 << i)

        self.cache = cache

    def move(self, target, speed) -> tuple[int, int]:
        if self.cache[target] is not None:
            return self.cache[target]
        # print(target, speed)

        best_moves = 987654321
        best_speed = None
        cases = list(range(1, target // 2 + 1))
        cases += [(1 << math.floor(math.log2(target)) + 1) - 1]
        for first_distance in reversed(cases):
            if first_distance == target:
                continue

            second_distance = target - first_distance

            cur_moves, cur_speed = 0, speed

            next_moves, next_speed = self.move(first_distance, cur_speed)
            if next_moves >= best_moves:
                continue
            cur_moves += next_moves
            cur_speed = next_speed

            if cur_speed == 0:
                cur_moves += 1
                cur_speed = 1
            else:
                next_is_opposite = second_distance * cur_speed < 0
                if next_is_opposite:
                    cur_moves += 1
                    cur_speed = 1 if cur_speed < 0 else -1
                else:
                    cur_moves += 2
                    cur_speed = 1 if cur_speed >= 0 else -1

            next_moves, next_speed = self.move(abs(second_distance), cur_speed)
            cur_moves += next_moves
            cur_speed = next_speed if second_distance > 0 else -next_speed

            # print(f"target: {target}, {first_distance} + {second_distance} ({cur_moves}, {cur_speed})")
            if cur_moves < best_moves:
                best_moves = cur_moves
                best_speed = cur_speed
            elif cur_moves == best_moves and cur_speed * best_speed < 0:
                best_speed = 0

        self.cache[target] = (best_moves, best_speed)
        return best_moves, best_speed

    def racecar(self, target: int) -> int:
        self.max_target = target * 2
        moves, speed = self.move(target, 1)
        return moves


def test():
    assert Solution().racecar(3) == 2
    assert Solution().racecar(6) == 5
    assert Solution().racecar(5) == 7
    assert Solution().racecar(11) == 10
    print(Solution().racecar(5617))
    print(Solution().racecar(5478))
    print(123)

if __name__ == '__main__':
    test()
