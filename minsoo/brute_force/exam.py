# https://programmers.co.kr/learn/courses/30/lessons/42840

def solution(answers):
    patterns = [
        [1, 2, 3, 4, 5],
        [2, 1, 2, 3, 2, 4, 2, 5],
        [3, 3, 1, 1, 2, 2, 4, 4, 5, 5]
    ]
    
    scores = [
        sum(map(lambda enum: pattern[enum[0] % len(pattern)] == enum[1], enumerate(answers)))
        for pattern in patterns
    ]
    
    max_score = max(scores)
    return [n for n, score in enumerate(scores, 1) if score == max_score]
