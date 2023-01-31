// https://leetcode.com/problems/maximize-number-of-subsequences-in-a-string/description/


class Solution {
    public long maximumSubsequenceCount(String text, String pattern) {
        char leftChar = pattern.charAt(0);
        char rightChar = pattern.charAt(1);

        long leftCnt = 0;
        long rightCnt = 0;
        long numSubsequences = 0;

        for (int i = 0; i < text.length(); i++) {
            char currChar = text.charAt(i);
            if (currChar == leftChar)
                leftCnt += 1;
            else if (currChar == rightChar) {
                numSubsequences += leftCnt;
                rightCnt += 1;
            }
        }

        if (leftChar == rightChar)
            return leftCnt * (leftCnt + 1) / 2;
        return numSubsequences + Math.max(leftCnt, rightCnt);
    }
}
