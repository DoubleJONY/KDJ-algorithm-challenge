OP_ADD = 0
OP_MUL = 1
MOD = int(1e9 + 7)


class Fancy:
    def __init__(self):
        self.length = 0
        self.values = []
        self.op_lists = []

    def append(self, val: int) -> None:
        self.values.append(val)
        self.op_lists.append((1, 0))
        self.length += 1

    def addAll(self, inc: int) -> None:
        if self.length <= 0:
            return
        top = self.length - 1
        op_mul, op_inc = self.op_lists[top]
        self.op_lists[top] = (op_mul, op_inc + inc)

    def multAll(self, m: int) -> None:
        if self.length <= 0:
            return
        top = self.length - 1
        op_mul, op_inc = self.op_lists[top]
        self.op_lists[top] = (op_mul * m, op_inc * m)

    def getIndex(self, idx: int) -> int:
        if idx >= self.length:
            return -1

        val = self.values[idx]
        for i in range(idx, self.length):
            op_mul, op_inc = self.op_lists[i]
            val *= op_mul
            val += op_inc
            val %= MOD
        return val
