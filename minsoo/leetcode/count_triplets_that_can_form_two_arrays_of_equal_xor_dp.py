# https://leetcode.com/problems/count-triplets-that-can-form-two-arrays-of-equal-xor/description/


class Solution:
    def countTriplets(self, arr: list[int]) -> int:
        dp = [[None for _ in arr] for _ in arr]

        num_triplets = 0

        for i in reversed(range(len(arr))):
            dp[i][i] = arr[i]
            for k in range(i + 1, len(arr)):
                dp[i][k] = dp[i + 1][k] ^ arr[i]

                num_triplets += sum(dp[i][j] ^ arr[j] == dp[j][k] for j in range(i + 1, k + 1))

        return num_triplets
