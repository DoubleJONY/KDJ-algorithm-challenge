# https://www.acmicpc.net/problem/13458

from math import ceil


class Exam:
    def __init__(self):
        self.n_rooms = int(input())
        self.examinees = list(map(int, input().split()))
        self.main_cap, self.vice_cap = map(int, input().split())

    @classmethod
    def num_supervisors(cls):
        exam = cls()
        return exam.n_rooms + sum(
            ceil(max(examinee - exam.main_cap, 0) / exam.vice_cap) for examinee in exam.examinees
        )


print(Exam.num_supervisors())
