# https://leetcode.com/problems/fancy-sequence/


class Fancy:
    def __init__(self):
        self.seq = []
        self.m = 1
        self.m_inv = 1
        self.inc = 0

        self.mod = int(1e9 + 7)
        self.invs = [None] + [pow(n, -1, self.mod) for n in range(1, 101)]

    def append(self, val: int) -> None:
        self.seq.append((val - self.inc) * self.m_inv)

    def addAll(self, inc: int) -> None:
        self.inc += inc

    def multAll(self, m: int) -> None:
        self.m = self.m * m % self.mod
        self.m_inv = self.m_inv * self.invs[m] % self.mod
        self.inc = self.inc * m % self.mod

    def getIndex(self, idx: int) -> int:
        if idx >= len(self.seq):
            return -1
        return (self.m * self.seq[idx] + self.inc) % self.mod
