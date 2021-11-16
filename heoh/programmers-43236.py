def check_possible(intervals, n, interval):
    cur_sum = 0
    for i in intervals:
        if n < 0:
            break

        cur_sum += i
        if cur_sum < interval:
            n -= 1
        else:
            cur_sum = 0

    return n >= 0

def solution(distance, rocks, n):
    rocks = [0, *sorted(rocks), distance]
    intervals = [b - a for a, b in zip(rocks[:-1], rocks[1:])]

    lo = 1  # lo 보다 작으면 가능
    hi = 1000000001  # hi 보다 크거나 같으면 불가능
    
    while lo < hi:
        mid = (lo + hi) // 2
        if check_possible(intervals, n, mid):
            lo = mid + 1
        else:
            hi = mid

    return lo - 1
