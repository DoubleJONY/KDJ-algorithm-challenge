import heapq


def solution(scoville, K):
    heapq.heapify(scoville)
    mix_count = 0
    if len(scoville) > 1 and scoville[len(scoville)//2] * (2**len(scoville)) < K:
        return -1

    while scoville[0] < K:
        try:
            tmp0 = heapq.heappop(scoville)
            tmp1 = heapq.heappop(scoville)
        except IndexError:
            return -1

        mix_sco = tmp0 + (tmp1*2)
        heapq.heappush(scoville, mix_sco)
        mix_count += 1

    return mix_count

print(solution([1, 2, 3, 9, 10, 12], 7))
print(2//2)

# if문전
# 정확성  테스트
# 테스트 1 〉	통과 (0.01ms, 10.3MB)
# 테스트 2 〉	통과 (0.00ms, 10.2MB)
# 테스트 3 〉	통과 (0.01ms, 10MB)
# 테스트 4 〉	통과 (0.01ms, 10.2MB)
# 테스트 5 〉	통과 (0.00ms, 10.2MB)
# 테스트 6 〉	통과 (0.53ms, 10.2MB)
# 테스트 7 〉	통과 (0.36ms, 10.1MB)
# 테스트 8 〉	통과 (0.05ms, 10.1MB)
# 테스트 9 〉	통과 (0.04ms, 10.2MB)
# 테스트 10 〉	통과 (0.29ms, 10.1MB)
# 테스트 11 〉	통과 (0.17ms, 10.3MB)
# 테스트 12 〉	통과 (0.63ms, 10.2MB)
# 테스트 13 〉	통과 (0.36ms, 10.1MB)
# 테스트 14 〉	통과 (0.01ms, 10.1MB)
# 테스트 15 〉	통과 (0.49ms, 10.2MB)
# 테스트 16 〉	통과 (0.00ms, 10.1MB)
# 효율성  테스트
# 테스트 1 〉	통과 (156.41ms, 16.2MB)
# 테스트 2 〉	통과 (380.36ms, 21.9MB)
# 테스트 3 〉	통과 (1737.96ms, 49.7MB)
# 테스트 4 〉	통과 (134.44ms, 15MB)
# 테스트 5 〉	통과 (1851.93ms, 51.8MB)


#if문후

# 정확성  테스트
# 테스트 1 〉	통과 (0.03ms, 10.2MB)
# 테스트 2 〉	통과 (0.00ms, 10.1MB)
# 테스트 3 〉	통과 (0.03ms, 10.2MB)
# 테스트 4 〉	통과 (0.01ms, 10.1MB)
# 테스트 5 〉	통과 (0.01ms, 10.2MB)
# 테스트 6 〉	통과 (0.40ms, 10.2MB)
# 테스트 7 〉	통과 (0.35ms, 10.1MB)
# 테스트 8 〉	통과 (0.05ms, 10.3MB)
# 테스트 9 〉	통과 (0.04ms, 10.2MB)
# 테스트 10 〉	통과 (0.29ms, 10.2MB)
# 테스트 11 〉	통과 (0.18ms, 10MB)
# 테스트 12 〉	통과 (0.67ms, 10.1MB)
# 테스트 13 〉	통과 (0.36ms, 10.2MB)
# 테스트 14 〉	통과 (0.01ms, 10.1MB)
# 테스트 15 〉	통과 (0.44ms, 10.1MB)
# 테스트 16 〉	통과 (0.00ms, 10.2MB)
# 효율성  테스트
# 테스트 1 〉	통과 (178.43ms, 16.1MB)
# 테스트 2 〉	통과 (388.48ms, 21.8MB)
# 테스트 3 〉	통과 (1599.40ms, 49.7MB)
# 테스트 4 〉	통과 (133.83ms, 14.9MB)
# 테스트 5 〉	통과 (1907.09ms, 51.8MB)