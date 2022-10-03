def main():
    N, K = map(int, input().split())
    A = list(map(int, input().split()))
    answer = simulate(N, K, A)
    print(answer)


def simulate(N, K, A):
    IN_INDEX = 0
    OUT_INDEX = N - 1

    belt = Belt(A)
    robots = []
    t = 0
    while belt.count_zero() < K:
        t += 1

        belt.rotate()
        if out_robot := belt.pop(OUT_INDEX):
            robots.remove(out_robot)

        for robot in robots:
            curr_index = belt.index_of(robot)
            next_index = curr_index + 1
            if (belt.get_robot(next_index) is None) and (belt.get_hp(next_index) > 0):
                belt.remove(robot)
                belt.put(next_index, robot)

        if belt.get_hp(IN_INDEX) > 0:
            robot = Robot()
            robots.append(robot)
            belt.put(IN_INDEX, robot)

        if out_robot := belt.pop(OUT_INDEX):
            robots.remove(out_robot)

    return t


last_robot_id = 0
def Robot():
    global last_robot_id
    last_robot_id += 1
    return last_robot_id


class Belt:
    def __init__(self, A):
        self.size = len(A)
        self.hp = list(A)
        self.robots = [None] * self.size
        self.raw_index_of_robot = {}
        self.offset = 0
        self.n_zeros = sum([v == 0 for v in self.hp])

    def count_zero(self):
        return self.n_zeros

    def rotate(self):
        self.offset += 1

    def raw_index(self, index):
        return (index - self.offset) % self.size

    def index(self, raw_index):
        return (raw_index + self.offset) % self.size

    def get_robot(self, index):
        return self.robots[self.raw_index(index)]

    def get_hp(self, index):
        return self.hp[self.raw_index(index)]

    def pop(self, index):
        raw_index = self.raw_index(index)
        robot = self.robots[raw_index]
        if robot:
            self.robots[raw_index] = None
            self.raw_index_of_robot.pop(robot)
        return robot

    def index_of(self, robot):
        raw_index = self.raw_index_of_robot[robot]
        return self.index(raw_index)

    def remove(self, robot):
        raw_index = self.raw_index_of_robot[robot]
        self.robots[raw_index] = None
        self.raw_index_of_robot.pop(robot)

    def put(self, index, robot):
        raw_index = self.raw_index(index)
        self.robots[raw_index] = robot
        self.raw_index_of_robot[robot] = raw_index

        self.hp[raw_index] -= 1
        if self.hp[raw_index] == 0:
            self.n_zeros += 1


main()
