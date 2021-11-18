# https://programmers.co.kr/learn/courses/30/lessons/43236

def solution(distance, rocks, n):
    rocks = sorted(rocks) + [distance]
    
    d_lower, d_upper, d_minmax = 1, distance, 0
    while not d_lower > d_upper:
        d = (d_lower + d_upper) // 2
        
        p_curr, in_dist = 0, 0
        for p_rock in rocks:
            if p_rock - p_curr < d:
                in_dist += 1
            else:
                p_curr = p_rock
        
        if in_dist > n:
            d_upper = d - 1
        else:
            d_lower = d + 1
            d_minmax = max(d_minmax, d)
    
    return d_minmax
