# https://programmers.co.kr/learn/courses/30/lessons/42584

def solution(prices):
    prices[-1] = -1
    stack, t_didnt_fall = [(-1, -1)], [0] * len(prices)

    for t, p in enumerate(prices):
        while p < stack[-1][1]:
            _t = stack.pop()[0]
            t_didnt_fall[_t] = t - _t
        stack.append((t, p))
        
    return t_didnt_fall
