# https://programmers.co.kr/learn/courses/30/lessons/42884

def solution(routes):
    routes.sort()
    start, end = routes[0]
    cnt = 1
    for s, e in routes[1:]:
        start = s
        if s > end:
            end = e
            cnt += 1
        else:
            end = min(end, e)
    return cnt
