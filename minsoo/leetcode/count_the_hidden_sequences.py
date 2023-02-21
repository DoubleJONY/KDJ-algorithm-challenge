# https://leetcode.com/problems/count-the-hidden-sequences/description/


class Solution:
    def numberOfArrays(self, differences: list[int], lower: int, upper: int) -> int:
        diff_sum = max_diff_sum = min_diff_sum = 0
        for diff in differences:
            diff_sum += diff
            max_diff_sum = max(max_diff_sum, diff_sum)
            min_diff_sum = min(min_diff_sum, diff_sum)

        if (max_interval := upper - lower) < (req_interval := max_diff_sum - min_diff_sum):
            return 0
        return max_interval - req_interval + 1
