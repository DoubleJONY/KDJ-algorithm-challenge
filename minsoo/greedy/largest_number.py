# https://programmers.co.kr/learn/courses/30/lessons/42883

def solution(number, k):
    stack = []
    for n in number:
        while stack and k and n > stack[-1]:
            stack.pop()
            k -= 1
        stack.append(n)
    return ''.join(stack[:len(stack) - k])
