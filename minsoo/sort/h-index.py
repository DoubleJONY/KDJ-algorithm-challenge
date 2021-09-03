# https://programmers.co.kr/learn/courses/30/lessons/42747

def solution(citations):
    citations.sort(reverse=True)
    for i, citation in enumerate(citations, 1):
        if i >= citation:
            return i - (i > citation)
    return len(citations)