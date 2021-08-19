class Printer:
    def __init__(self, priorities):
        self.q = [(p, i) for i, p in enumerate(priorities)]
        self.pq = sorted(priorities, reverse=True)

    def print(self):
        printed = []

        while self.q:
            p, j = self.q.pop(0)
            if p < self.pq[0]:
                self.q.append((p, j))
            else:
                printed.append(j)
                self.pq.pop(0)

        return printed


def solution(priorities, location):
    printed = Printer(priorities).print()
    answer = printed.index(location) + 1
    return answer
