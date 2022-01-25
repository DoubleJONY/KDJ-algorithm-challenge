# """
# This is Master's API interface.
# You should not implement it, or speculate about its implementation
# """
# class Master:
#     def guess(self, word: str) -> int:

from __future__ import annotations
from collections import Counter
from typing import List

WORD_LEN = 6


class Solution:
    def findSecretWord(self, wordlist: List[str], master: 'Master') -> None:
        while wordlist:
            wordlist = sort_by_popularity(wordlist)
            word = wordlist.pop(0)
            matches = master.guess(word)
            if matches == WORD_LEN:
                break

            for candidate in wordlist:
                if not check_word_is_answerable(candidate, word, matches):
                    wordlist.remove(candidate)

            print(len(wordlist), word, matches, wordlist)


def sort_by_popularity(wordlist: List[str]) -> None:
    char_counts = [Counter() for _ in range(WORD_LEN)]
    for word in wordlist:
        for i, c in enumerate(word):
            char_counts[i][c] += 1

    def get_popularity(word: str):
        populrarity = 0
        for i, c in enumerate(word):
            populrarity += char_counts[i][c] ** 2
        return populrarity

    return sorted(wordlist, key=get_popularity, reverse=True)


def check_word_is_answerable(candidate: str, word: str, answer_matches: int) -> bool:
    candidate_matches = match_words(word, candidate)
    return candidate_matches == answer_matches


def match_words(word_a: str, word_b: str) -> int:
    matches = 0
    for char_a, char_b in zip(word_a, word_b):
        if char_a == char_b:
            matches += 1
    return matches
