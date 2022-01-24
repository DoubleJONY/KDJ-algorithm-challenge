OP_ADD = 0
OP_MUL = 1
MOD = int(1e9 + 7)


class Fancy:
    def __init__(self):
        self.length = 0
        self.values = []
        self.op = (1, 0)
        self.prev_states = []

    def append(self, val: int) -> None:
        self.values.append(val)
        self.prev_states.append(self.op)
        self.length += 1

    def addAll(self, inc: int) -> None:
        op_mul, op_inc = self.op
        self.op = (op_mul, (op_inc + inc) % MOD)

    def multAll(self, m: int) -> None:
        op_mul, op_inc = self.op
        self.op = ((op_mul * m) % MOD, (op_inc * m) % MOD)

    def getIndex(self, idx: int) -> int:
        if idx >= self.length:
            return -1

        val = self.values[idx]
        op_mul, op_inc = self.op
        prev_mul, prev_inc = self.prev_states[idx]
        prev_mul_inv = fast_pow(prev_mul, MOD - 2, MOD)
        mul = ((op_mul % MOD) * (prev_mul_inv % MOD)) % MOD
        val = (((val - prev_inc) * mul) % MOD + op_inc) % MOD
        return int(val)


# https://ohgym.tistory.com/13
def fast_pow(base, exp, mod):
    result = 1
    while exp:
        if exp & 1:
            result = (result * base) % mod
        exp >>= 1
        base = (base * base) % mod
    return result
