class Solution:
    def strangePrinter(self, s: str) -> int:
        return min(self.strangePrinter2(s), self.strangePrinter2(s[::-1]))

    def strangePrinter2(self, s: str) -> int:
        scope_right_stack = []

        print_cnt = 0
        paper = '_' * len(s)

        scope_left = 0
        scope_right = len(s)
        scope_right_stack.append(scope_right)

        for i in range(len(s)):
            if s[i] == paper[i]:
                continue

            left_char = s[i]
            scope_left = i
            while scope_right <= scope_left:
                scope_right_stack.pop()
                scope_right = scope_right_stack[-1]

            scope_right = s.rindex(left_char, scope_left, scope_right)
            scope_right_stack.append(scope_right)
            
            paper = self.print(paper, scope_left, scope_right, left_char)
            print_cnt += 1

        return print_cnt



    def print(self, paper, left, right, char):
        return paper[:left] + char * (right - left + 1) + paper[right+1:]


def main():
    assert Solution().strangePrinter('tbgtgb') == 4
    assert Solution().strangePrinter('abc') == 3
    assert Solution().strangePrinter('aaabbb') == 2
    assert Solution().strangePrinter('aba') == 2
    assert Solution().strangePrinter('aabbaabbaa') == 3
    assert Solution().strangePrinter('aabbaabbccaabbccbbaabbccaa') == 8


if __name__ == '__main__':
    main()
