def solution(array, commands):
    sorted_array = sorted([(x, i) for i, x in enumerate(array)])
    
    def get_sorted_array(s, e):
        sub_array = filter(lambda t: s-1 <= t[1] <= e-1, sorted_array)
        return list(map(lambda t: t[0], sub_array))

    answer = []
    for command in commands:
        s, e, i = command
        sub_array = get_sorted_array(s, e)
        answer.append(sub_array[i - 1])
    
    return answer
