def check_possible(n, times, t):
    return sum([int(t / x) for x in times]) >= n

def solution(n, times):
    lo = 0  # lo 보다 작으면 무조건 불가능
    hi = 1000000000 * 1000000000 + 1 # hi 보다 크거나 같으면 무조건 가능

    while lo < hi:
        mid = (lo + hi) // 2
        if check_possible(n, times, mid):
            hi = mid
        else:
            lo = mid + 1

    return hi
