from typing import Any, Iterable, List
from queue import PriorityQueue


def solution(operations: List[str]) -> None:
    deque = PriorityDeque()

    for operation in operations:
        op, value = parse((str, int), operation.split())
        if op == 'I':
            deque.add(value)
        elif op == 'D':
            if deque.is_empty():
                continue
            if value == 1:
                deque.remove_max()
            elif value == -1:
                deque.remove_min()

    if deque.is_empty():
        return [0, 0]
    else:
        return [deque.max_value, deque.min_value]


class PriorityDeque:

    class Node:
        def __init__(self, value: int, alive: bool=True) -> None:
            self.value = value
            self.alive = alive

        def __lt__(self, o: object) -> bool:
            return self.value < o.value


    def __init__(self) -> None:
        self._len = 0
        self._min_pq = PriorityQueue()
        self._max_pq = PriorityQueue()

    def __len__(self) -> int:
        return self._len

    def is_empty(self) -> bool:
        return len(self) == 0

    @property
    def min_value(self) -> int:
        return self._min_node.value

    @property
    def max_value(self) -> int:
        return self._max_node.value

    def add(self, value: int) -> None:
        node = PriorityDeque.Node(value)
        self._min_pq.put((node.value, node))
        self._max_pq.put((-node.value, node))
        self._len += 1

    def remove_min(self) -> int:
        _, node = self._min_pq.get()
        node.alive = False
        self._len -= 1
        self._remove_dead_nodes()
        return node.value

    def remove_max(self) -> int:
        _, node = self._max_pq.get()
        node.alive = False
        self._len -= 1
        self._remove_dead_nodes()
        return node.value

    def _remove_dead_nodes(self) -> None:
        while (not self._min_pq.empty()) and (not self._min_node.alive):
            self._min_pq.get()
        while (not self._max_pq.empty()) and (not self._max_node.alive):
            self._max_pq.get()

    @property
    def _min_node(self) -> Node:
        return self._min_pq.queue[0][1]

    @property
    def _max_node(self) -> Node:
        return self._max_pq.queue[0][1]


def parse(types: Iterable[Any], args: Iterable[Any]) -> Iterable[Any]:
    return [f(arg) for f, arg in zip(types, args)]
