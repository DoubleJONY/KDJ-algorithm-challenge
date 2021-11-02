# def solution(number, k):
#     target_len = (len(number) - k)
#     # number = list(number)
    
#     answer = ''
#     start_idx = 0
#     for i in range(target_len):
#         end_idx = len(number)-(target_len - (i+1))
        
#         max_tmp = max(number[start_idx:end_idx])
#         answer += str(max_tmp)
#         for k in range(len(number[start_idx:end_idx])):
#             if number[start_idx + k] == max_tmp:
#                 start_idx = start_idx + k + 1
#                 break
#         if len(answer) == target_len:
#             return answer


#     return answer



def solution(number, k):
    stac = []

    for i in number:
        while stac and stac[-1] < i and k > 0:
            k -= 1
            stac.pop()
        stac.append(i)
    return "".join(stac[:len(number) - k])



print(solution("1924", 2))

print(solution("1231234", 3))
print(solution("4177252841", 4))


# 실행 결과
# 채점을 시작합니다.
# 정확성  테스트
# 테스트 1 〉	통과 (0.01ms, 10.1MB)
# 테스트 2 〉	통과 (0.01ms, 10.2MB)
# 테스트 3 〉	통과 (0.02ms, 10.2MB)
# 테스트 4 〉	통과 (0.20ms, 10.3MB)
# 테스트 5 〉	통과 (0.16ms, 10.2MB)
# 테스트 6 〉	통과 (2.52ms, 10.3MB)
# 테스트 7 〉	통과 (7.09ms, 10.9MB)
# 테스트 8 〉	통과 (29.07ms, 11.1MB)
# 테스트 9 〉	통과 (34.57ms, 16MB)
# 테스트 10 〉	통과 (88.78ms, 15.1MB)
# 테스트 11 〉	통과 (0.01ms, 10.2MB)
# 테스트 12 〉	통과 (0.01ms, 10.1MB)
# 채점 결과
# 정확성: 100.0
# 합계: 100.0 / 100.0