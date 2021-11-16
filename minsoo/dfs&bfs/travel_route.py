# https://programmers.co.kr/learn/courses/30/lessons/43164

def dfs(depart, tickets, used):
    if used == (1 << len(tickets)) - 1:
        return [depart]
    
    for n, (dep, arr) in enumerate(tickets):
        if not (1 << n) & used and dep == depart:
            if route := dfs(arr, tickets, used | (1 << n)):
                return [depart] + route

def solution(tickets):
    return dfs('ICN', sorted(tickets, key=lambda x: x[1]), 0)
