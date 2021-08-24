# def solution(prices):
#     answer = []

#     while len(prices):
#         check_price = prices.pop(0)
#         sec = 0
#         for i in prices:
#             if check_price <= i:
#                 sec += 1
#             else:
#                 sec += 1
#                 break
#         answer.append(sec)
#         sec = 0

#     return answer


# print(solution([1, 2, 3, 2, 3]))





def solution(prices):
    answer = [0]*len(prices)

    stack = []
    for i, price in enumerate(prices):
        print(stack)
        while stack and (price < prices[stack[-1]]):
            idx = stack.pop(-1)
            # if i-idx > 1:
            #     sec = i - idx -1 
            # else:
            #     sec = 1
            # answer[idx] = sec
            answer[idx] = i-idx
                
        stack.append(i) 

    while stack:
        j = stack.pop()
        answer[j] = len(prices) - 1 - j


    return answer


print(solution([5, 6, 7, 8, 9, 10, 2, 3]))


# 테스트2?
# print(solution([5, 6, 7, 8, 9, 10, 2, 3]))

# 정확성  테스트
# 테스트 1 〉	통과 (0.01ms, 10.2MB)
# 테스트 2 〉	통과 (0.03ms, 10.1MB)
# 테스트 3 〉	통과 (0.20ms, 10.1MB)
# 테스트 4 〉	통과 (0.23ms, 10.3MB)
# 테스트 5 〉	통과 (0.26ms, 10.3MB)
# 테스트 6 〉	통과 (0.02ms, 10.2MB)
# 테스트 7 〉	통과 (0.15ms, 10.2MB)
# 테스트 8 〉	통과 (0.19ms, 10.2MB)
# 테스트 9 〉	통과 (0.02ms, 10.2MB)
# 테스트 10 〉	통과 (0.28ms, 10.2MB)
# 효율성  테스트
# 테스트 1 〉	통과 (23.58ms, 18.7MB)
# 테스트 2 〉	통과 (17.41ms, 17.6MB)
# 테스트 3 〉	통과 (26.81ms, 19.4MB)
# 테스트 4 〉	통과 (20.27ms, 18.3MB)
# 테스트 5 〉	통과 (13.65ms, 16.9MB)
# 채점 결과
# 정확성: 66.7
# 효율성: 33.3
# 합계: 100.0 / 100.0