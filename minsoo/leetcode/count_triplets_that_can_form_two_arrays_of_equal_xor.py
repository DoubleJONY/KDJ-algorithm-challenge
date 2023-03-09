# https://leetcode.com/problems/count-triplets-that-can-form-two-arrays-of-equal-xor/description/

from collections import defaultdict


class Solution:
    def countTriplets(self, arr: list[int]) -> int:
        num_triplets = 0

        prefix_indices = defaultdict(list)
        prefix_indices[prefix:=0].append(0)

        for k, n in enumerate(arr, 1):
            prefix ^= n
            for i in prefix_indices[prefix]:
                num_triplets += k - i - 1

            prefix_indices[prefix].append(k)

        return num_triplets
