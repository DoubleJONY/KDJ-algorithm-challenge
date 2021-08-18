#전화번호 목록

def solution(phone_book):

    phone_book_sorted = sorted(phone_book)
    for i in range(len(phone_book_sorted)-1):

        if(phone_book_sorted[i] == phone_book_sorted[i+1][:len(phone_book_sorted[i])]):
                    
            return False
            
    return True



# 정확성  테스트
# 테스트 1 〉	통과 (0.01ms, 10.2MB)
# 테스트 2 〉	통과 (0.01ms, 10.2MB)
# 테스트 3 〉	통과 (0.01ms, 10.1MB)
# 테스트 4 〉	통과 (0.01ms, 10.3MB)
# 테스트 5 〉	통과 (0.01ms, 10.2MB)
# 테스트 6 〉	통과 (0.01ms, 10.2MB)
# 테스트 7 〉	통과 (0.01ms, 10.2MB)
# 테스트 8 〉	통과 (0.01ms, 10.2MB)
# 테스트 9 〉	통과 (0.01ms, 10.2MB)
# 테스트 10 〉	통과 (0.01ms, 10.2MB)
# 테스트 11 〉	통과 (0.01ms, 10.2MB)
# 테스트 12 〉	통과 (0.01ms, 10.2MB)
# 테스트 13 〉	통과 (0.01ms, 10.1MB)
# 테스트 14 〉	통과 (0.41ms, 10.2MB)
# 테스트 15 〉	통과 (0.78ms, 10.3MB)
# 테스트 16 〉	통과 (0.60ms, 10.3MB)
# 테스트 17 〉	통과 (1.22ms, 10.4MB)
# 테스트 18 〉	통과 (0.99ms, 10.3MB)
# 테스트 19 〉	통과 (1.69ms, 10.3MB)
# 테스트 20 〉	통과 (2.24ms, 10.4MB)
# 효율성  테스트
# 테스트 1 〉	통과 (3.43ms, 10.7MB)
# 테스트 2 〉	통과 (2.75ms, 10.7MB)
# 테스트 3 〉	통과 (99.40ms, 30.8MB)
# 테스트 4 〉	통과 (94.77ms, 28MB)

# 채점 결과
# 정확성: 83.3
# 효율성: 16.7
# 합계: 100.0 / 100.0