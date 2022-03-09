from typing import List

import heapq
from bisect import bisect_left


class Solution:
    def search(self, nums: List[int], target: int) -> bool:
        first_pos = self.find_first_pos(nums, 0, len(nums)-1)

        n = len(nums)
        if nums[first_pos] <= target <= nums[n-1]:
            return self.check_exist(nums, target, first_pos, n-1)
        else:
            return self.check_exist(nums, target, 0, first_pos-1)

    def find_first_pos(self, nums: List[int], left: int, right: int) -> int:
        block_pq = []
        heapq.heappush(block_pq, (0, left, right))
        while block_pq:
            block_priority, block_left, block_right = heapq.heappop(block_pq)
            block_length = block_right - block_left + 1

            if block_length == 1:
                continue
            if block_length == 2:
                if nums[block_left] > nums[block_right]:
                    return block_right
                else:
                    continue

            mid = (block_left + block_right) // 2
            if nums[block_left] > nums[mid]:
                heapq.heappush(block_pq, (block_priority-1, block_left, mid))
            else:
                heapq.heappush(block_pq, (block_priority, block_left, mid))

            if nums[mid] > nums[block_right]:
                heapq.heappush(block_pq, (block_priority-1, mid, block_right))
            else:
                heapq.heappush(block_pq, (block_priority, mid, block_right))

        return left

    def check_exist(self, nums: List[int], target: int, left: int, right: int) -> bool:
        if left > right:
            return False
        index = bisect_left(nums, target, left, right+1)
        if not left <= index <= right:
            return False
        return nums[index] == target
