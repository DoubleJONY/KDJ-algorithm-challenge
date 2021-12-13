import math

N = int(input())
A = list(map(int, input().split()))
B, C = list(map(int, input().split()))

total = N + sum(math.ceil(max(A[i] - B, 0) / C) for i in range(N))
print(total)
