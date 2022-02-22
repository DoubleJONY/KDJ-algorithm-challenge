# https://leetcode.com/problems/operations-on-tree/

# Runtime: 1488 ms, faster than 92.82% of Python3 online submissions for Operations on Tree.
# Memory Usage: 17.7 MB, less than 29.23% of Python3 online submissions for Operations on Tree.


from collections import defaultdict
from typing import List


class LockingTree:
    def __init__(self, parent: List[int]):
        self._parent = parent
        self._locked_by = [-1 for _ in parent]
        self._locked_descendents = [0 for _ in parent]

        descendents = defaultdict(list)
        for node, p in enumerate(parent):
            descendents[p].append(node)
        self._descendents = descendents

    def lock(self, num: int, user: int) -> bool:
        if self._locked_by[num] != -1:
            return False

        self._locked_by[num] = user

        while (num := self._parent[num]) != -1:
            self._locked_descendents[num] += 1
        return True

    def unlock(self, num: int, user: int) -> bool:
        if self._locked_by[num] != user:
            return False

        self._locked_by[num] = -1

        while (num := self._parent[num]) != -1:
            self._locked_descendents[num] -= 1
        return True

    def unlock_descendents_anyway(self, num: int) -> None:
        for _num in self._descendents[num]:
            self._locked_by[_num] = -1
            self.unlock_descendents_anyway(_num)

    def upgrade(self, num: int, user: int) -> bool:
        if self._locked_by[num] != -1:
            return False
        if not self._descendents[num]:
            return False
        if not self._locked_descendents[num]:
            return False

        _num = num
        while (_num := self._parent[_num]) != -1:
            if self._locked_by[_num] != -1:
                return False

        self._locked_by[num] = user
        self.unlock_descendents_anyway(num)
        return True
