# https://programmers.co.kr/learn/courses/30/lessons/43165

def solution(numbers, target):
    return target == 0 if not numbers else solution(numbers[:-1], target - numbers[-1]) + solution(numbers[:-1], target + numbers[-1])
