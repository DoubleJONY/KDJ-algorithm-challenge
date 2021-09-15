from typing import List

def pattern_generator(pattern: List[int]):
    while True:
        for x in pattern:
            yield x

def generate_answer(pattern: List[int], n: int):
    generator = pattern_generator(pattern)
    return [next(generator) for _ in range(n)]

def get_score(submission: List[int], answers: List[int]):
    corrects = [s == a for s, a in zip(submission, answers)]
    return corrects.count(True)

def solution(answers: List[int]):
    patterns = {
        1: [1, 2, 3, 4, 5],
        2: [2, 1, 2, 3, 2, 4, 2, 5],
        3: [3, 3, 1, 1, 2, 2, 4, 4, 5, 5],
    }

    submissions = {i: generate_answer(p, len(answers)) for i, p in patterns.items()}
    scores = {i: get_score(s, answers) for i, s in submissions.items()}

    max_score = max(scores.values())
    answer = [i for i, v in scores.items() if v == max_score]

    return answer
