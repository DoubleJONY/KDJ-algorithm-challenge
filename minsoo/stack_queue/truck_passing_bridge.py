# https://programmers.co.kr/learn/courses/30/lessons/42583

from collections import deque

def solution(bridge_length, weight, truck_weights):
    truck_weights = deque(truck_weights)
    q = deque([0] * bridge_length)
    t_passed, w_on_bridge = 0, 0

    while q:
        w_on_bridge -= q.popleft()
        t_passed += 1
        if truck_weights:
            if w_on_bridge + truck_weights[0] <= weight:
                q.append(truck_weights[0])
                w_on_bridge += truck_weights.popleft()
            else:
                q.append(0)

    return t_passed
