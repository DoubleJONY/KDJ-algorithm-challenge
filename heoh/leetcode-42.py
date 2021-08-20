from typing import List

RIGHT = +1
LEFT = -1

range_by_dir = {
    RIGHT: lambda n: range(0, n, 1),
    LEFT: lambda n: range(n-1, -1, -1),
}

class Solution:
    def trap(self, height: List[int]) -> int:
        max_height = max(height)
        water = [max_height] * len(height)

        self.sweep(water, height, RIGHT)
        self.sweep(water, height, LEFT)
        return self.count(water, height)

    def sweep(self, water: List[int], height: List[int], dir: int) -> None:
        last_height = 0
        for i in range_by_dir[dir](len(water)):
            last_height = max(last_height, height[i])
            if water[i] > last_height:
                water[i] = last_height

    def count(self, water: List[int], height: List[int]) -> int:
        cnt = 0
        for w, h in zip(water, height):
            cnt += w - h
        return cnt
