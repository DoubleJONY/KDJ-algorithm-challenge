# https://leetcode.com/problems/trapping-rain-water/

# time complexity: O(n)

class Solution:
    def trap(self, height: list[int]) -> int:
        volume = 0
        stk = []
        for i, h in enumerate(height):
            while stk and h > height[stk[-1]]:
                between = stk.pop()
                if not len(stk):
                    break
                dist = i - (stk[-1] + 1)
                volume += (min(height[stk[-1]], h) - height[between]) * dist
            stk.append(i)
        return volume