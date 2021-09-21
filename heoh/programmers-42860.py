from collections import deque

def solution(name: str):
    left_right_count = find_optimal_left_right_count(name)
    up_down_count = find_optimal_up_down_count(name)

    return left_right_count + up_down_count

def find_optimal_left_right_count(name: str):
    n = len(name)
    
    queue = deque()
    visited = set()
    
    cursor = 0
    targets = make_targets_bits(name)
    targets &= ~(1 << cursor)
    state = (targets, cursor)

    queue.append((0, state))
    visited.add(state)

    while queue:
        t, state = queue.popleft()
        targets, cursor = state

        if targets == 0:
            return t

        for direction in (-1, 1):
            next_cursor = ((cursor + direction) + n) % n
            next_targets = targets & ~(1 << next_cursor)
            next_state = (next_targets, next_cursor)
            if next_state not in visited:
                queue.append((t + 1, next_state))
                visited.add(next_state)

def make_targets_bits(name: str):
    bits = 0
    for i, c in enumerate(name):
        if c != 'A':
            bits |= 1 << i
    return bits

def find_optimal_up_down_count(name: str):
    count = 0
    for c in name:
        up_count = ord(c) - ord('A')
        down_count = 1 + ord('Z') - ord(c)
        count += min(up_count, down_count)
    return count
