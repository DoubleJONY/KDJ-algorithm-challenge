from collections import Counter


class Solution:
    def customSortString(self, order: str, s: str) -> str:
        char_counts = Counter(s)
        
        sorted_s = ""
        
        for c in order:
            if c in char_counts:
                sorted_s += c * char_counts[c]
                char_counts[c] = 0
        
        for c in char_counts:
            sorted_s += c * char_counts[c]

        return sorted_s
