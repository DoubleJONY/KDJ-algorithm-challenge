# https://programmers.co.kr/learn/courses/30/lessons/42895

def solution(N, number):
    dp = [set() for _ in range(9)]
    for n in range(1, 9):
        dp[n].add(int(str(N) * n))
        for m in range(1, n):
            for num1 in dp[m]:
                for num2 in dp[n - m]:
                    dp[n].add(num1 + num2)
                    dp[n].add(num1 - num2)
                    dp[n].add(num1 * num2)
                    if num2:
                        dp[n].add(num1 // num2)
        
        if number in dp[n]:
            return n
    return -1
