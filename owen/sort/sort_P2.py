
def solution(numbers):
    tmp_numbers = list(map(str, numbers))
    tmp_numbers = str(int("".join(sorted(tmp_numbers,key = lambda item : item*3, reverse=True))))
    return tmp_numbers

print(solution([6, 10, 2]))
print(solution([3, 30, 34, 5, 9]))
print(solution([0,0,0,0])) 