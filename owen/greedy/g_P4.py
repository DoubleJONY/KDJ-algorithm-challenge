from collections import deque

def solution(people, limit):
    sorted_people = sorted(people)
    answer = 0
    front = 0
    rear = len(sorted_people) - 1
    while front <= rear:
        answer += 1
        if sorted_people[front] + sorted_people[rear] <= limit:
            front += 1
        rear -= 1

    return answer

print(solution([70, 50, 80, 50], 100))
print(solution([70, 80, 50], 100))


# 채점을 시작합니다.
# 정확성  테스트
# 테스트 1 〉	통과 (0.84ms, 10.3MB)
# 테스트 2 〉	통과 (0.66ms, 10.3MB)
# 테스트 3 〉	통과 (1.00ms, 10.3MB)
# 테스트 4 〉	통과 (0.57ms, 10.4MB)
# 테스트 5 〉	통과 (0.54ms, 10.3MB)
# 테스트 6 〉	통과 (0.34ms, 10.3MB)
# 테스트 7 〉	통과 (0.31ms, 10.2MB)
# 테스트 8 〉	통과 (0.05ms, 10.2MB)
# 테스트 9 〉	통과 (0.05ms, 10.2MB)
# 테스트 10 〉	통과 (0.60ms, 10.3MB)
# 테스트 11 〉	통과 (0.49ms, 10.3MB)
# 테스트 12 〉	통과 (0.45ms, 10.3MB)
# 테스트 13 〉	통과 (0.62ms, 10.3MB)
# 테스트 14 〉	통과 (0.72ms, 10.2MB)
# 테스트 15 〉	통과 (0.07ms, 10.2MB)
# 효율성  테스트
# 테스트 1 〉	통과 (9.04ms, 10.9MB)
# 테스트 2 〉	통과 (9.83ms, 10.9MB)
# 테스트 3 〉	통과 (8.73ms, 10.8MB)
# 테스트 4 〉	통과 (9.67ms, 10.8MB)
# 테스트 5 〉	통과 (8.56ms, 10.8MB)
# 채점 결과
# 정확성: 75.0
# 효율성: 25.0
# 합계: 100.0 / 100.0