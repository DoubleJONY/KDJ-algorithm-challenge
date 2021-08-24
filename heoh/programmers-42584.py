INDEX = 0
PRICE = 1

def solution(prices):
    answer = [0] * len(prices)

    stack = [(-1, 0)]  # key error 방지
    prices[-1] = 0  # 마지막 날 무조건 팔도록
    for i, price in enumerate(prices):
        while stack[-1][PRICE] > price:
            s = stack.pop()
            answer[s[INDEX]] = i - s[INDEX]

        stack.append((i, price))

    return answer
