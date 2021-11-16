def solution(n, times):
    times.sort()
    
    t_min, t_max = 1, times[-1] * n
    t = (t_min + t_max) // 2
    while t_min < t_max:
        done = sum(map(lambda takes: t // takes, times))
        if done < n:
            t_min = t + 1
        else:
            t_max = t
        t = (t_min + t_max) // 2

    return t
