import math

def solution(answers):
    students = [[1,2,3,4,5], [2,1,2,3,2,4,2,5], [3,3,1,1,2,2,4,4,5,5]]
    scores = []
    for i in students:
        times = math.ceil(len(answers) /len(i) )
        tmp_answer = i * times
        tmp_answer = tmp_answer[:len(answers)]
        tmp_score = 0
        for k in range(len(tmp_answer)):
            if tmp_answer[k] == answers[k]:
                tmp_score +=1
        scores.append(tmp_score)
    max_score = max(scores)
    answer=[]
    for s in range(len(scores)):
        if scores[s] == max_score:
            answer.append(s+1)
    return sorted(answer)

print(solution([1,2,3,4,5]))

print(solution([1,3,2,4,2]))



# 채점을 시작합니다.
# 정확성  테스트
# 테스트 1 〉	통과 (0.01ms, 10.2MB)
# 테스트 2 〉	통과 (0.02ms, 10.3MB)
# 테스트 3 〉	통과 (0.02ms, 10.2MB)
# 테스트 4 〉	통과 (0.01ms, 10.3MB)
# 테스트 5 〉	통과 (0.02ms, 10.2MB)
# 테스트 6 〉	통과 (0.03ms, 10.4MB)
# 테스트 7 〉	통과 (1.14ms, 10.4MB)
# 테스트 8 〉	통과 (0.38ms, 10.4MB)
# 테스트 9 〉	통과 (1.96ms, 10.5MB)
# 테스트 10 〉	통과 (1.77ms, 10.2MB)
# 테스트 11 〉	통과 (2.18ms, 10.5MB)
# 테스트 12 〉	통과 (1.92ms, 10.4MB)
# 테스트 13 〉	통과 (0.14ms, 10.2MB)
# 테스트 14 〉	통과 (2.17ms, 10.5MB)
# 채점 결과
# 정확성: 100.0
# 합계: 100.0 / 100.0