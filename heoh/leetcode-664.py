class Solution:
    def strangePrinter(self, s: str) -> int:
        # aaaaabbbbbbccccccaaaaaa -> abca
        ss = s[0]
        for c in s[1:]:
            if c != ss[-1]:
                ss += c
        s = ss

        cost = {}
        for i in range(len(s)):
            for j in range(len(s) + 1):
                if i < j:
                    cost[i, j] = self.strangePrinter2(s[i:j])
        
        cache = {}
        def div_and_conq(i, j):
            if (i, j) in cache:
                return cache[i, j]
            
            best = cost[i, j]
            for m in range(i+1, j):
                cur_cost = div_and_conq(i, m) + div_and_conq(m, j)
                best = min(best, cur_cost)
            
            cache[i, j] = best
            return cache[i, j]

        return div_and_conq(0, len(s))

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
    assert Solution().strangePrinter('baacdddaaddaaaaccbddbcabdaabdbbcdcbbbacbddcabcaaa') == 19


if __name__ == '__main__':
    main()
