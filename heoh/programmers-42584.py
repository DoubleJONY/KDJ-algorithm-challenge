INDEX = 0
PRICE = 1

def solution(prices):
    answer = [0] * len(prices)

    stack = [(-1, 0)]
    prices.append(0)
    for i, price in enumerate(prices):
        while stack[-1][PRICE] > price:
            s = stack.pop()
            answer[s[INDEX]] = i - s[INDEX]

        stack.append((i, price))

    return answer
