class Solution:
    def removeDuplicates(self, s: str, k: int) -> str:
        stack = ''
        for c in s:
            stack += c
            if stack[-k:] == (c * k):
                stack = stack[:-k]
        return stack


def test_solution():
    assert Solution().removeDuplicates('abcd', 2) == 'abcd'
    assert Solution().removeDuplicates('deeedbbcccbdaa', 3) == 'aa'
    assert Solution().removeDuplicates('pbbcggttciiippooaais', 2) == 'ps'


if __name__ == '__main__':
    test_solution()
