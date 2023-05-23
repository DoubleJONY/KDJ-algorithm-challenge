def sub_g(x, p):
    return x // (5**p)

def g(x):
    return sum(sub_g(x, i) for i in range(1, 15))

def bs(lo, hi, x):
    if hi >= lo:
        mid = (hi + lo) // 2
        if g(mid) == x:
            return mid
        elif g(mid) > x:
            return bs(lo, mid-1, x)
        else:
            return bs(mid+1, hi, x)
    else:
        return -1

class Solution:
    def preimageSizeFZF(self, k: int) -> int:
        result = bs(0, 10**10, k)
        if result == -1:
            return 0
        else:
            return 5
