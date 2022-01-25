# https://leetcode.com/problems/guess-the-word/


class Solution:
    def findSecretWord(self, wordlist: list[str], master: "Master") -> None:
        wordlist = sorted(wordlist)
        direction = 1

        while True:
            candidate = wordlist[(direction == -1) * -1]
            cnt = master.guess(candidate)
            if cnt < 6:
                wordlist = [word for word in wordlist if self.cnt_overlap(candidate, word) == cnt]
            else:
                return

            direction *= -1

    @staticmethod
    def cnt_overlap(ref: str, compared: str):
        return sum(c_ref == c_comp for c_ref, c_comp in zip(ref, compared))
