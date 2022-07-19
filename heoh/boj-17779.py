import math

def main():
    N = int(input())
    A = [list(map(int, input().split())) for _ in range(N)]

    min_diff = math.inf

    for y in range(N):
        for x in range(N):
            for d1 in range(N):
                for d2 in range(N):
                    diff = calculate_diff_of_case(N, A, x, y, d1, d2)
                    if diff is not None:
                        min_diff = min(min_diff, diff)

    print(min_diff)


def calculate_diff_of_case(N, A, x0, y0, d1, d2):
    def is_valid():
        return (
            (d1 >= 1 and d2 >= 1) and
            (1 <= x0 < x0+d1+d2 <= N) and
            (1 <= y0-d1 < y0 < y0+d2 <= N)
        )

    def get_area_id(x, y):
        if ((y - (y0-d1) >= -x + (x0+d1)) and
            (y - (y0-d1) >= x - (x0+d1)) and
            (y - (y0+d2) <= x - (x0+d2)) and
            (y - (y0+d2) <= -x + (x0+d2))):
            return 4
        if ((x <= x0+d1) and (y <= y0-1)):
            return 0
        if ((x >= x0+d1+1) and (y <= y0-d1+d2)):
            return 1
        if ((x <= x0+d2-1) and (y >= y0)):
            return 2
        if ((x >= x0+d2) and (y >= y0-d1+d2+1)):
            return 3

    if is_valid():
        sum_of_area = [0, 0, 0, 0, 0]
        for y in range(1,N+1):
            for x in range(1,N+1):
                area_id = get_area_id(x, y)
                sum_of_area[area_id] += A[y-1][x-1]
        diff = max(sum_of_area) - min(sum_of_area)
        return diff
    else:
        return math.inf


main()
