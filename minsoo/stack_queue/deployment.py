# https://programmers.co.kr/learn/courses/30/lessons/42586

import math

def solution(progresses, speeds):
    published, days_past = [], 0
    for status, per_day in zip(progresses, speeds):
        if status + per_day * days_past < 100:
            days_past = math.ceil((100 - status) / per_day)
            published.append(1)
        else:
            published[-1] += 1
    return published
