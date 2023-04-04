# https://leetcode.com/problems/word-ladder-ii/

from collections import deque, defaultdict


class Solution:
    def findLadders(self, begin_word: str, end_word: str, word_list: list[str]) -> list[list[str]]:
        if end_word not in word_list:
            return []

        def build_pattern_family() -> dict[str, list]:
            pattern_family = defaultdict(list)

            for word in word_list:
                for i in range(len(word)):
                    pattern_family[word[:i] + "_" + word[i + 1 :]].append(word)

            return pattern_family

        pattern_family = build_pattern_family()

        def bfs() -> dict[str, list]:
            q = deque([begin_word])
            visited = {begin_word: []}

            found = False

            while q and not found:
                _visited = defaultdict(list)

                for _ in range(len(q)):
                    word = q.popleft()

                    for i in range(len(word)):
                        for next_word in pattern_family[word[:i] + "_" + word[i + 1 :]]:
                            if next_word in visited:
                                continue

                            if next_word not in _visited:
                                q.append(next_word)

                            if next_word == end_word:
                                found = True

                            _visited[next_word].append(word)

                visited.update(_visited)

            return visited

        word_tree = bfs()

        def dfs(word: str) -> list[list[str]]:
            if word == begin_word:
                return [[begin_word]]

            if word not in word_tree:
                return []

            paths = []

            for parent in word_tree[word]:
                paths.extend(dfs(parent))

            for path in paths:
                path.append(word)

            return paths

        return dfs(end_word)
