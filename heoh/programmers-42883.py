def solution(number: str, k: int):
    stack = []
    for v in number:
        while k > 0 and stack and v > stack[-1]:
            stack.pop()
            k -= 1
        stack.append(v)
    if k > 0:
        stack = stack[:-k]
    return ''.join(stack)
