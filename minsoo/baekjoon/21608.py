# https://www.acmicpc.net/problem/21608


from collections import namedtuple

Student = namedtuple("Student", ["id", "likes"])


def stdin_bj21608() -> tuple[int, list[Student]]:
    """
    첫째 줄에 N이 주어진다.
    둘째 줄부터 N2개의 줄에 학생의 번호와 그 학생이 좋아하는 학생 4명의 번호가 한 줄에 하나씩 선생님이 자리를 정할 순서대로 주어진다.

    학생의 번호는 중복되지 않으며, 어떤 학생이 좋아하는 학생 4명은 모두 다른 학생으로 이루어져 있다.
    입력으로 주어지는 학생의 번호, 좋아하는 학생의 번호는 N2보다 작거나 같은 자연수이다.
    어떤 학생이 자기 자신을 좋아하는 경우는 없다.
    """

    size = int(input())

    def make_student():
        student_id, *likes = map(int, input().split())
        student = Student(student_id, set(likes))

        return student

    students = [make_student() for _ in range(size ** 2)]

    return size, students


def main():
    size, students = stdin_bj21608()

    located = {}
    located_map = [[0 for _ in range(size)] for _ in range(size)]

    def valid(r: int, c: int) -> bool:
        return 0 <= r < size and 0 <= c < size

    def get_candidates(likes: list[int]) -> dict[tuple, tuple]:
        candidates = {}

        for like_id in likes:
            if like_id not in located:
                continue

            r, c = located[like_id]

            for dr, dc in [(-1, 0), (0, 1), (1, 0), (0, -1)]:
                nr, nc = r + dr, c + dc

                if not valid(nr, nc):
                    continue
                if located_map[nr][nc]:
                    continue

                num_surrounding_likes = 0
                num_surrounding_blanks = 0

                for _dr, _dc in [(-1, 0), (0, 1), (1, 0), (0, -1)]:
                    _nr, _nc = nr + _dr, nc + _dc

                    if not valid(_nr, _nc):
                        continue

                    if located_map[_nr][_nc] in likes:
                        num_surrounding_likes += 1
                    elif not located_map[_nr][_nc]:
                        num_surrounding_blanks += 1

                candidates[nr, nc] = (-num_surrounding_likes, -num_surrounding_blanks)

        if not candidates:
            for r, row in enumerate(located_map):
                for c, v in enumerate(row):
                    if v:
                        continue

                    num_surrounding_blanks = 0

                    for dr, dc in [(-1, 0), (0, 1), (1, 0), (0, -1)]:
                        nr, nc = r + dr, c + dc

                        if not valid(nr, nc):
                            continue
                        if located_map[nr][nc]:
                            continue

                        num_surrounding_blanks += 1

                    if num_surrounding_blanks == 4:
                        return {(r, c): (0, -4)}

                    candidates[r, c] = (0, -num_surrounding_blanks)

        return candidates

    for student in students:
        candidates = get_candidates(student.likes)

        r, c = sorted(candidates.keys(), key=lambda k: candidates[k] + k)[0]

        located[student.id] = (r, c)
        located_map[r][c] = student.id

    def get_satisfaction(student: Student) -> int:
        num_surrounding_likes = 0

        r, c = located[student.id]

        for dr, dc in [(-1, 0), (0, 1), (1, 0), (0, -1)]:
            nr, nc = r + dr, c + dc

            if not valid(nr, nc):
                continue
            if located_map[nr][nc] not in student.likes:
                continue

            num_surrounding_likes += 1

        return 10 ** (num_surrounding_likes - 1) if num_surrounding_likes else 0

    print(sum(get_satisfaction(student) for student in students))


if __name__ == "__main__":
    main()
