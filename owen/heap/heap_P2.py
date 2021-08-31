import heapq

def solution(jobs):
    store_jobs = []
    for item in jobs:
        heapq.heappush(store_jobs,[item[0], item[1], item[0]])
    disk_time = 0
    ing_disk = []
    result_time = 0
    while store_jobs:
        tmp_disk = heapq.heappop(store_jobs)
        

        print(ing_disk,tmp_disk, disk_time)
        if tmp_disk[0] < disk_time  :
            tmp_disk[0] = disk_time 
            heapq.heappush(store_jobs,tmp_disk)

        elif tmp_disk[0] > disk_time  :
            disk_time += tmp_disk[0] - disk_time
            heapq.heappush(store_jobs,tmp_disk)
        else:
            disk_time +=  tmp_disk[1]
            result_time += disk_time - tmp_disk[2]
            ing_disk  = tmp_disk

    return result_time // len(jobs)

print(solution([[0, 3], [1, 9], [2, 6]]))
print("######################")
print(solution(	[[0, 10], [2, 3], [9, 3]]))
print("######################")
print(solution(	[[0, 3], [4, 3], [10, 3]]))
print("######################")
print(solution(	[[2, 3], [4, 3], [10, 3]]))


# 정확성  테스트
# 테스트 1 〉	통과 (74.04ms, 10.3MB)
# 테스트 2 〉	통과 (40.02ms, 10.3MB)
# 테스트 3 〉	통과 (29.38ms, 10.2MB)
# 테스트 4 〉	통과 (31.70ms, 10.2MB)
# 테스트 5 〉	통과 (52.39ms, 10.2MB)
# 테스트 6 〉	통과 (0.07ms, 10.2MB)
# 테스트 7 〉	통과 (22.38ms, 10.2MB)
# 테스트 8 〉	통과 (13.71ms, 10.2MB)
# 테스트 9 〉	통과 (1.88ms, 10.2MB)
# 테스트 10 〉	통과 (57.99ms, 10.2MB)
# 테스트 11 〉	통과 (0.02ms, 10.1MB)
# 테스트 12 〉	통과 (0.02ms, 10.2MB)
# 테스트 13 〉	통과 (0.02ms, 10.1MB)
# 테스트 14 〉	통과 (0.01ms, 10.2MB)
# 테스트 15 〉	통과 (0.01ms, 10.2MB)
# 테스트 16 〉	통과 (0.01ms, 10.1MB)
# 테스트 17 〉	통과 (0.01ms, 10.2MB)
# 테스트 18 〉	통과 (0.01ms, 10.3MB)
# 테스트 19 〉	통과 (0.01ms, 10.1MB)
# 테스트 20 〉	통과 (0.01ms, 10.1MB)
# 채점 결과
# 정확성: 100.0
# 합계: 100.0 / 100.0