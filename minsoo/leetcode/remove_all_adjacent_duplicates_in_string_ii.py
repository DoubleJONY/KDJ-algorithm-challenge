# https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/description/


class Solution:
    def removeDuplicates(self, s: str, k: int) -> str:
        stack = []

        for c in s:
            if not stack or stack[-1][0] != c:
                stack.append((c, 1))
            else:
                if (cnt := stack[-1][1]) == k - 1:
                    stack = stack[:-cnt]
                else:
                    stack.append((c, cnt + 1))

        return "".join(c for c, _ in stack)
