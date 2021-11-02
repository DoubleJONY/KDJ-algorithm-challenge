# https://programmers.co.kr/learn/courses/30/lessons/42897

def solution(money):
    dp0 = [0 for _ in money]
    dp0[1] = dp0[0] = money[0]
    for i in range(2, len(money) - 1):
        dp0[i] = max(dp0[i - 1], dp0[i - 2] + money[i])
    
    dp1 = [0 for _ in money]
    dp1[1] = money[1]
    for i in range(2, len(money)):
        dp1[i] = max(dp1[i - 1], dp1[i - 2] + money[i])

    return max(dp0[-2], dp1[-1])
