def main():
    graph = init_graph()
    dices = list(map(int, input().split()))
    answer = find_maximum_score(graph, dices)
    print(answer)

def init_graph():
    nodes = []
    def create_node(value):
        node = Node(len(nodes), value)
        nodes.append(node)
        return node

    def create_nodes(*values):
        seq = [create_node(v) for v in values]
        for prev, next in zip(seq[:-1], seq[1:]):
            prev.next = next
        return seq[0], seq[-1]

    start_node  = create_node(0)
    end_node    = create_node(0)
    west_node   = create_node(10)
    south_node  = create_node(20)
    east_node   = create_node(30)
    north_node  = create_node(40)
    center_node = create_node(25)

    begin, end = create_nodes(2, 4, 6, 8)
    start_node.next = begin
    end.next = west_node

    begin, end = create_nodes(12, 14, 16, 18)
    west_node.next = begin
    end.next = south_node

    begin, end = create_nodes(22, 24, 26, 28)
    south_node.next = begin
    end.next = east_node

    begin, end = create_nodes(32, 34, 36, 38)
    east_node.next = begin
    end.next = north_node

    begin, end = create_nodes(13, 16, 19)
    west_node.shortcut = begin
    end.next = center_node

    begin, end = create_nodes(22, 24)
    south_node.shortcut = begin
    end.next = center_node

    begin, end = create_nodes(28, 27, 26)
    east_node.shortcut = begin
    end.next = center_node

    begin, end = create_nodes(30, 35)
    center_node.next = begin
    end.next = north_node

    north_node.next = end_node

    return nodes

def find_maximum_score(graph, dices):
    def dfs(dice_queue, state):
        if not dice_queue:
            return 0

        best_score = 0
        for i in range(4):
            curr_node = graph[state[i]]
            next_node = curr_node.jump(dice_queue[0])
            if curr_node.is_end():
                continue
            if (not next_node.is_end()) and (next_node.id in state):
                continue
            next_state = state.copy()
            next_state[i] = next_node.id

            score = next_node.value + dfs(dice_queue[1:], next_state)
            best_score = max(best_score, score)

        return best_score

    return dfs(dices, [0, 0, 0, 0])


class Node:
    def __init__(self, id, value):
        self.id = id
        self.value = value
        self.next = None
        self.shortcut = None

    def is_end(self):
        return self.next is None

    def jump(self, n, shortcut=True):
        if self.is_end() or n == 0:
            return self
        if shortcut and self.shortcut:
            return self.shortcut.jump(n - 1, False)
        else:
            return self.next.jump(n - 1, False)


main()
