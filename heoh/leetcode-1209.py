class Solution:
    def removeDuplicates(self, s: str, k: int) -> str:
        stack = [[None, 0]]
        for c in s:
            last_seq = stack[-1]
            if last_seq[0] == c:
                last_seq[1] += 1
            else:
                last_seq = [c, 1]
                stack.append(last_seq)
                continue

            if last_seq[1] == k:
                stack.pop()

        result = ''
        for seq in stack[1:]:
            result += seq[0] * seq[1]

        return result


def test_solution():
    assert Solution().removeDuplicates('abcd', 2) == 'abcd'
    assert Solution().removeDuplicates('deeedbbcccbdaa', 3) == 'aa'
    assert Solution().removeDuplicates('pbbcggttciiippooaais', 2) == 'ps'


if __name__ == '__main__':
    test_solution()
