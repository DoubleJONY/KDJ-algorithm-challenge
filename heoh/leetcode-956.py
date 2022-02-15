from functools import cache
from typing import List


class Solution:
    def tallestBillboard(self, rods: List[int]) -> int:
        rods = sorted(rods)
        n = len(rods)
        total = sum(rods)

        def get_first_bit_index(bits):
            for i in range(n):
                if bits & (1 << i) != 0:
                    return i
            return -1

        @cache
        def get_sum_in_all_cases(bits):
            if bits == 0:
                return set()
            j = get_first_bit_index(bits)
            value = rods[j]
            sub_cases = get_sum_in_all_cases(bits ^ (1 << j))
            cases = set([value + v for v in sub_cases]) | sub_cases | set([value])
            return cases

        def is_possible(sum, bits):
            sums = get_sum_in_all_cases(bits)
            return sum in sums

        def dfs(i, taken, sum):
            if sum > (total - sum):
                return 0
            if i == 0:
                if is_possible(sum, ((1 << n) - 1) & ~taken):
                    return sum
                else:
                    return 0

            return max(dfs(i-1, taken | (1 << i-1), sum + rods[i-1]), dfs(i-1, taken, sum))

        answer = dfs(n, 0, 0)

        return answer


# print(Solution().tallestBillboard([1,2,4,8,16,32,64,128,256,512,50,50,50,150,150,150,100,100,100,123]))
