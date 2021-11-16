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
                
    
        if del_rock > n:
            r = mid - 1
        else:
            answer = max_min
            l = mid + 1

    return answer
