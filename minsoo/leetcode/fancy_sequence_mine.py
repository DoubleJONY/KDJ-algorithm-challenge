# https://leetcode.com/problems/fancy-sequence/


class Fancy:
    def __init__(self):
        self.seq = []
        self.transforms = [(0, 1, 0)]
        self.mod = int(1e9 + 7)
        self.cache = {}
        self.op_cnt = 0

    def append(self, val: int) -> None:
        self.seq.append(val)
        self.transforms.append(self.transforms[-1])

    def compose(self, transform: tuple[int, int]):
        prev_div, prev_mod, prev_add = self.transforms[-1]
        mult, add = transform
        div, mod = divmod(prev_mod * mult, self.mod)
        self.transforms[-1] = (prev_div * mult + div, mod, (prev_add * mult + add) % self.mod)
        self.op_cnt += 1

    def addAll(self, inc: int) -> None:
        self.compose((1, inc))

    def multAll(self, m: int) -> None:
        self.compose((m, 0))

    def getIndex(self, idx: int) -> int:
        if idx >= len(self.seq):
            return -1

        try:
            return self.cache[(self.op_cnt, idx)]

        except:
            start_div, start_mod, start_add = self.transforms[idx]
            end_div, end_mod, end_add = self.transforms[-1]

            mult = (end_div * self.mod + end_mod) // (start_div * self.mod + start_mod)
            add = end_add - mult * start_add

            self.cache[(self.op_cnt, idx)] = (mult * self.seq[idx] + add) % self.mod
            return self.cache[(self.op_cnt, idx)]
