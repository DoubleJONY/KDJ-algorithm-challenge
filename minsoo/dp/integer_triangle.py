# https://programmers.co.kr/learn/courses/30/lessons/43105

def solution(triangle):
    dp = [[n for n in floor] for floor in triangle]
    for depth, floor in enumerate(dp[:-1]):
        for idx, route_sum in enumerate(floor):
            dp[depth + 1][idx] = max(
                dp[depth + 1][idx],
                route_sum + triangle[depth + 1][idx]
            )
            dp[depth + 1][idx + 1] += route_sum
    return max(dp[-1])
