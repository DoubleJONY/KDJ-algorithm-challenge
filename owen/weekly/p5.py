def solution(word):
    fixed_w_len = 5
    w2n = [0] * 5
    alpha_dict={"A":1,"E":2,"I":3,"O":4,"U":5}
    word_list = [ alpha_dict[i] for i in word]
    for i in range(len(word)):
        w2n[i] = word_list[i]
        
    sequence = 0 
    for i, v in enumerate(w2n):
        if v != 0:
            if v-1 > 0:

                sequence += 1

                for k in range(v-1):
                    for m in range(fixed_w_len-(i+1)+1):
                        
                        sequence += fixed_w_len ** (m)
                
            elif v-1 ==0 :
                sequence += 1

    
    return sequence


print(solution("AAAAE"))
print("######################")
print(solution("AAAE"))
print("######################")
print(solution("I"))
print(solution("EIO"))

# 채점을 시작합니다.
# 정확성  테스트
# 테스트 1 〉	통과 (0.01ms, 10.2MB)
# 테스트 2 〉	통과 (0.01ms, 10.1MB)
# 테스트 3 〉	통과 (0.03ms, 10.1MB)
# 테스트 4 〉	통과 (0.02ms, 10.3MB)
# 테스트 5 〉	통과 (0.02ms, 10.2MB)
# 테스트 6 〉	통과 (0.02ms, 10.3MB)
# 테스트 7 〉	통과 (0.02ms, 10.3MB)
# 테스트 8 〉	통과 (0.01ms, 10.1MB)
# 테스트 9 〉	통과 (0.01ms, 10.2MB)
# 테스트 10 〉	통과 (0.01ms, 10.2MB)
# 테스트 11 〉	통과 (0.01ms, 10.2MB)
# 테스트 12 〉	통과 (0.01ms, 10.3MB)
# 테스트 13 〉	통과 (0.01ms, 10.2MB)
# 테스트 14 〉	통과 (0.01ms, 10.1MB)
# 테스트 15 〉	통과 (0.01ms, 10.3MB)
# 테스트 16 〉	통과 (0.01ms, 10.3MB)
# 테스트 17 〉	통과 (0.02ms, 10.1MB)
# 테스트 18 〉	통과 (0.01ms, 10.2MB)
# 테스트 19 〉	통과 (0.02ms, 10.3MB)
# 테스트 20 〉	통과 (0.02ms, 10.2MB)
# 테스트 21 〉	통과 (0.01ms, 10.1MB)
# 테스트 22 〉	통과 (0.02ms, 10.3MB)
# 테스트 23 〉	통과 (0.01ms, 10.3MB)
# 테스트 24 〉	통과 (0.01ms, 10.2MB)
# 테스트 25 〉	통과 (0.01ms, 10.3MB)
# 테스트 26 〉	통과 (0.01ms, 10.2MB)
# 테스트 27 〉	통과 (0.01ms, 10.2MB)
# 테스트 28 〉	통과 (0.02ms, 10.2MB)
# 테스트 29 〉	통과 (0.01ms, 10.1MB)
# 테스트 30 〉	통과 (0.01ms, 10.3MB)
# 테스트 31 〉	통과 (0.01ms, 10.2MB)
# 테스트 32 〉	통과 (0.01ms, 10.2MB)
# 테스트 33 〉	통과 (0.02ms, 10.2MB)
# 테스트 34 〉	통과 (0.01ms, 10.2MB)
# 테스트 35 〉	통과 (0.02ms, 10.3MB)
# 테스트 36 〉	통과 (0.01ms, 10.1MB)
# 테스트 37 〉	통과 (0.03ms, 10.2MB)
# 테스트 38 〉	통과 (0.01ms, 10.3MB)
# 테스트 39 〉	통과 (0.01ms, 10.1MB)
# 테스트 40 〉	통과 (0.01ms, 10.2MB)
# 채점 결과
# 정확성: 100.0
# 합계: 100.0 / 100.0