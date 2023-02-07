# https://leetcode.com/problems/race-car/description/
# reference: https://leetcode.com/problems/race-car/solutions/124326/summary-of-the-bfs-and-dp-solutions-with-intuitive-explanation/


class Solution:
    def racecar(self, target: int) -> int:
        """
                                                        Where we want to know
                                                                  |
                                                                  v
        distance:  0             pivot - subpivot     pivot      pos      2^(m + 1) - 1
        num_moves: 0                  m - n             m                     m + 1
                   |--------------------|-------------->|---------|-------------|
                   |--------------------|<--------------|---------|-------------|

        * Every dp component starts with speed 1
        """

        dp = [0] + [float("inf") for _ in range(target)]

        def pivots(ubound: int, *, begin: int = 1):
            n = begin
            while (pivot := 2 ** n - 1) < ubound:
                yield n, pivot
                n += 1

        for pos in range(1, target + 1):
            for m, pivot in pivots(pos, begin=1):
                for n, subpivot in pivots(pivot, begin=0):
                    dp[pos] = min(dp[pos], m + 1 + n + 1 + dp[pos - (pivot - subpivot)])

            try:
                pivot = 2 ** (m := m + 1) - 1
            except UnboundLocalError:
                m = pivot = 1

            dp[pos] = min(dp[pos], m + (pivot != pos) * (1 + dp[pivot - pos]))

        return dp[target]
