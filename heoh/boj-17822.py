from statistics import mean


def main():
    N, M, T = map(int, input().split())
    A = {i: Plate(list(map(int, input().split()))) for i in range(1, N+1)}

    def rotate(x, d, k):
        for i in range(1, N+1):
            if i % x == 0:
                A[i].rotate(d, k)

    def find_removal_targets():
        targets = []
        for i in range(1, N+1):
            for j in range(M):
                if not A[i].exists(j):
                    continue

                next_i = i+1 if i+1 <= N else 1
                prev_i = i-1 if i-1 >= 1 else N
                next_j = j+1 if j+1 < M else 0
                prev_j = j-1 if j-1 > 0 else M-1
                value = A[i].get(j)
                if ((value == A[next_i].get(j)) or
                    (value == A[prev_i].get(j)) or
                    (value == A[i].get(next_j)) or
                    (value == A[i].get(prev_j))
                ):
                    targets.append((i, j))

        return targets

    def remove_or_update():
        targets = find_removal_targets()
        if targets:
            for i, j in targets:
                A[i].remove_at(j)
        else:
            numbers = []
            for i in range(1, N+1):
                for j in range(M):
                    if A[i].exists(j):
                        numbers.append((i, j, A[i].get(j)))
            if numbers:
                avg = sum(x[2] for x in numbers) / len(numbers)
                for i, j, v in numbers:
                    if v > avg:
                        A[i].set(j, v-1)
                    elif v < avg:
                        A[i].set(j, v+1)

    for i in range(T):
        x, d, k = map(int, input().split())
        rotate(x, d, k)
        remove_or_update()

    answer = sum(sum(r) for r in A)
    print(answer)


class Plate:
    def __init__(self, values):
        self.values = values
        self.offset = 0

    def rotate(self, d, k):
        M = len(self.values)
        offset = self.offset
        offset += k * {0: -1, 1: 1}[d]
        self.offset = (offset + M) % M

    def get(self, i):
        M = len(self.values)
        index = (self.offset + i) % M
        return self.values[index]

    def exists(self, i):
        return self.get(i) == None

    def set(self, i, value):
        M = len(self.values)
        index = (self.offset + i) % M
        self.values[index] = value

    def remove_at(self, i):
        self.set(i, None)


main()
