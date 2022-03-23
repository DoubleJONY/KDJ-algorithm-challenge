# https://www.acmicpc.net/problem/14888


n = int(input())
nums = list(map(int, input().split()))
operators = list(map(int, input().split()))

_max, _min = float("-inf"), float("inf")


def calc_seq(num: int, idx: int, n_plus: int, n_min: int, n_mult: int, n_div: int):
    if idx == n:
        global _max, _min
        _max, _min = max(_max, num), min(_min, num)
        return

    _num = nums[idx]

    if n_plus:
        calc_seq(num + _num, idx + 1, n_plus - 1, n_min, n_mult, n_div)
    if n_min:
        calc_seq(num - _num, idx + 1, n_plus, n_min - 1, n_mult, n_div)
    if n_mult:
        calc_seq(num * _num, idx + 1, n_plus, n_min, n_mult - 1, n_div)
    if n_div:
        calc_seq(num // _num if num > 0 else -((-num) // _num), idx + 1, n_plus, n_min, n_mult, n_div - 1)


calc_seq(nums[0], 1, *operators)

print(_max, _min, sep="\n")
