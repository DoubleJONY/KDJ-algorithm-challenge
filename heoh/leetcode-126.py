from typing import List, Set, Dict
from collections import deque, defaultdict
from itertools import combinations


class Solution:
    def __init__(self) -> None:
        self.cache = {}

    def findLadders(self, beginWord: str, endWord: str, wordList: List[str]) -> List[List[str]]:
        self.begin_word = beginWord
        self.end_word = endWord
        self.adjacent_map = self.init_adjacent_map(wordList + [beginWord])
        self.depth_of_word = self.bfs()
        sequences = self.make_sequences()
        return sequences

    def init_adjacent_map(self, words: List[str]) -> Dict[str, Set[str]]:
        adjacent_map: Dict[str, Set[str]] = defaultdict(set)
        for a, b in combinations(words, 2):
            if self.get_diff(a, b) == 1:
                adjacent_map[a].add(b)
                adjacent_map[b].add(a)
        return adjacent_map

    def get_diff(self, a: str, b: str) -> int:
        return sum(ca != cb for ca, cb in zip(a, b))

    def bfs(self) -> Dict[str, int]:
        depth_of_word: Dict[str, int] = {}
        queue: deque[str, int] = deque()

        queue.append((self.begin_word, 0))
        depth_of_word[self.begin_word] = 0

        while queue:
            word, depth = queue.popleft()
            if word == self.end_word:
                break

            for next_word in self.adjacent_map[word]:
                if next_word in depth_of_word:
                    continue

                queue.append((next_word, depth + 1))
                depth_of_word[next_word] = depth + 1

        return depth_of_word

    def make_sequences(self) -> List[List[str]]:
        return self.dfs(self.begin_word)

    def dfs(self, word: str) -> List[List[str]]:
        if word in self.cache:
            return self.cache[word]

        if word == self.end_word:
            sequences = [[self.end_word]]
        else:
            depth = self.depth_of_word[word]
            sequences = []
            for next_word in self.adjacent_map[word]:
                next_depth = self.depth_of_word.get(next_word)
                if next_depth != depth + 1:
                    continue
                subsequences = self.dfs(next_word)
                sequences += [[word] + seq for seq in subsequences]

        self.cache[word] = sequences
        return sequences
