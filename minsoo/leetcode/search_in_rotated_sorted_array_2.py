# https://leetcode.com/problems/search-in-rotated-sorted-array-ii/

# Runtime: 54 ms, faster than 88.18% of Python3 online submissions for Search in Rotated Sorted Array II.
# Memory Usage: 14.5 MB, less than 50.51% of Python3 online submissions for Search in Rotated Sorted Array II.

from typing import List


class Solution:
    def search(self, nums: List[int], target: int) -> bool:
        left, right = 0, len(nums) - 1

        while left <= right:
            mid = left + (right - left) // 2
            if nums[mid] == target:
                return True

            if nums[mid] < nums[right]:
                if nums[mid] < target <= nums[right]:
                    left = mid + 1
                else:
                    right = mid - 1
            elif nums[mid] > nums[right]:
                if nums[left] <= target < nums[mid]:
                    right = mid - 1
                else:
                    left = mid + 1
            else:
                right -= 1

        return False
