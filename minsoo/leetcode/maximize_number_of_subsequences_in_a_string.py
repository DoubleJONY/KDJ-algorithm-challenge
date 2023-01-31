# https://leetcode.com/problems/maximize-number-of-subsequences-in-a-string/description/


class Solution:
    def maximumSubsequenceCount(self, text: str, pattern: str) -> int:
        left, right = pattern
        left_cnt = right_cnt = 0

        num_subsequences = 0

        for c in text:
            if c == left:
                left_cnt += 1
            elif c == right:
                right_cnt += 1
                num_subsequences += left_cnt

        if left == right:
            return left_cnt * (left_cnt + 1) // 2
        return num_subsequences + max(left_cnt, right_cnt)


if __name__ == "__main__":
    texts = ["abdcdbc", "aabb"]
    patterns = ["ac", "ab"]

    solution = Solution()
    for text, pattern in zip(texts, patterns):
        num_subsequences = solution.maximumSubsequenceCount(text, pattern)
        print(num_subsequences)
