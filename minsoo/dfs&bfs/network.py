# https://programmers.co.kr/learn/courses/30/lessons/43162

def solution(n, computers):
    cnt, visited = 0, 0
    for i in range(len(computers)):
        if visited & (1 << i):
            continue
        stk = [i]
        while stk:
            curr = stk.pop()
            visited |= (1 << curr)
            for neighbor, is_connected in enumerate(computers[curr]):
                if not is_connected or visited & (1 << neighbor):
                    continue
                stk.append(neighbor)
        cnt += 1
    return cnt
