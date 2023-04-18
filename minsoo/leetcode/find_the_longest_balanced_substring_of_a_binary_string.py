# https://leetcode.com/problems/find-the-longest-balanced-substring-of-a-binary-string/description/


class Solution:
    def findTheLongestBalancedSubstring(self, s: str) -> int:
        counts = [[0, 0]]

        for b in s:
            if b == "0":
                if counts[-1][1]:
                    counts.append([1, 0])
                else:
                    counts[-1][0] += 1

            else:
                counts[-1][1] += 1

        return 2 * max(min(count) for count in counts)
