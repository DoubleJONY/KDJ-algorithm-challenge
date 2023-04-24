class Solution:
    def findTheLongestBalancedSubstring(self, s: str) -> int:
        state = '0'
        n_zeroes = 0
        n_ones = 0
        best = 0
        for c in s:
            if state == '0':
                if c == '0':
                    n_zeroes += 1
                else:
                    state = '1'
                    n_ones += 1
            else:
                if c == '0':
                    n_zeroes = 1
                    n_ones = 0
                    state = '0'
                else:
                    n_ones += 1
            seq_len = 2 * min(n_zeroes, n_ones)
            best = max(best, seq_len)
        return best
