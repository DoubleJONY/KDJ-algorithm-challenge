from functools import cache

MOD = int(1e9 + 7)


cnt = 0

class Solution:
    @cache
    def rearrangeSticks(self, n: int, k: int) -> int:
        global cnt
        if n == k:
            return 1
        elif n < k:
            return 0
        elif k == 0:
            return 0
        n_cases = 0
        for i in range(k-1, n):
            # n_cases += self.rearrangeSticks(i, k-1) * self.combination(n-1, i) * self.factorial(n-i-1)
            n_cases += self.rearrangeSticks(i, k-1) * (self.factorial(n-1) * pow(self.factorial(i), MOD - 2, MOD)) % MOD
        n_cases %= MOD
        return int(n_cases)

    @cache
    def factorial(self, x):
        if x < 2:
            return 1
        return (self.factorial(x-1) * x) % MOD

    @cache
    def combination(self, n: int, k: int) -> int:
        num = self.factorial(n)
        den = (self.factorial(n - k) * self.factorial(k) % MOD)
        return (num * pow(den, MOD - 2, MOD)) % MOD


# print(Solution().rearrangeSticks(481, 314))
