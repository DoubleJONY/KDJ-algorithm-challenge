class Solution:
    def maximumSubsequenceCount(self, text: str, pattern: str) -> int:
        prefix = pattern[0]
        suffix = pattern[1]

        return max(
            self.count_subsequence(prefix + text, pattern),
            self.count_subsequence(text + suffix, pattern)
        )

    def count_subsequence(self, text: str, pattern: str) -> int:
        prefix = pattern[0]
        suffix = pattern[1]

        pattern_set = set(pattern)
        s = ''.join(filter(lambda c: c in pattern_set, text))
        n = len(s)

        suffix_counts = [0] * n
        n_suffix = 0
        for i in reversed(range(n)):
            if s[i] == suffix:
                n_suffix += 1
            suffix_counts[i] = n_suffix

        count = 0
        for i in range(n-1):
            if s[i] == prefix:
                count += suffix_counts[i+1]

        return count
