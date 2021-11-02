def loop(target, numbers, idx, cnt):
    for i in range(idx, len(numbers)):
        tmp = target
        tmp -= numbers[i]
        
        if tmp == 0:
            cnt += 1
        elif tmp > 0:
            cnt += loop(tmp, numbers, i+1, 0)
        elif tmp < 0:
            continue

    return cnt


def solution(numbers, target):

    except_num = (sum(numbers) - target) // 2

    

    answer = loop(except_num, numbers, 0, 0 )
    return answer



print(solution([1, 1, 1, 1, 1], 3))


# 정확성  테스트
# 테스트 1 〉	통과 (144.66ms, 10.3MB)
# 테스트 2 〉	통과 (81.38ms, 10.3MB)
# 테스트 3 〉	통과 (0.08ms, 10.2MB)
# 테스트 4 〉	통과 (0.04ms, 10.4MB)
# 테스트 5 〉	통과 (5.17ms, 10.2MB)
# 테스트 6 〉	통과 (0.14ms, 10.3MB)
# 테스트 7 〉	통과 (0.26ms, 10.3MB)
# 테스트 8 〉	통과 (1.19ms, 10.4MB)
# 채점 결과
# 정확성: 100.0
# 합계: 100.0 / 100.0