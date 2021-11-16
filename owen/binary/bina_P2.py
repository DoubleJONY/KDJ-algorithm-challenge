def solution(distance, rocks, n):
    l, r = 1, distance
    
    rocks.sort()
    
    while l <= r:
        mid = (l + r) // 2
        del_rock = 0
        front_rock = 0
        max_min = 1000000001
        
        for rock in rocks:
            if rock - front_rock < mid:
                del_rock += 1
            else:
                max_min = min(max_min, rock - front_rock)
                front_rock = rock
                
        if del_rock == n:
            answer = max_min
            break
        elif del_rock > n:
            r = mid - 1
        else:
            l = mid + 1

    return answer







print(solution(25, [2, 14, 11, 21, 17], 2))



# 정확성  테스트
# 테스트 1 〉	통과 (0.17ms, 10.3MB)
# 테스트 2 〉	실패 (0.22ms, 10.3MB)
# 테스트 3 〉	통과 (0.10ms, 10.3MB)
# 테스트 4 〉	통과 (15.60ms, 10.4MB)
# 테스트 5 〉	실패 (런타임 에러)
# 테스트 6 〉	실패 (런타임 에러)
# 테스트 7 〉	실패 (런타임 에러)
# 테스트 8 〉	통과 (181.65ms, 12.3MB)
# 테스트 9 〉	통과 (0.01ms, 10.4MB)
# 채점 결과
# 정확성: 55.6
# 합계: 55.6 / 100.0