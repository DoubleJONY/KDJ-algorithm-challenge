# https://programmers.co.kr/learn/courses/30/lessons/42898

def solution(m, n, puddles):
    board = [[0 for _ in range(m)] for _ in range(n)]
    board[0][0] = 1
    
    puddles = set(map(tuple, puddles))
    for row in range(n):
        for col in range(m):
            if (col + 1, row + 1) in puddles:
                continue
            if row + 1 < n:
                board[row + 1][col] += board[row][col]
            if col + 1 < m:
                board[row][col + 1] += board[row][col]
    
    return board[-1][-1] % int(1e+9 + 7)
