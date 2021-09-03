# https://programmers.co.kr/learn/courses/30/lessons/42748

def solution(array, commands):
    return [sorted(array[i - 1:j])[k - 1] for i, j, k in commands]
