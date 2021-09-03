from typing import List
from collections import namedtuple

Truck = namedtuple('Truck', ['weight', 'start_time'])

def solution(bridge_length, max_weight, truck_weights):
    bridge_queue: List[Truck] = []
    bridge_weight = 0
    t = 0

    bridge_queue.append(Truck(truck_weights.pop(0), t))
    bridge_weight += bridge_queue[-1].weight
    t += 1

    def truck_is_arrived(truck: Truck, t):
        return (t - truck.start_time) >= bridge_length

    def get_free_weight():
        return max_weight - bridge_weight

    def when_truck_is_arrived(truck: Truck):
        return truck.start_time + bridge_length

    while bridge_queue:
        updated = False

        if truck_is_arrived(bridge_queue[0], t):
            bridge_weight -= bridge_queue.pop(0).weight
            updated = True

        if truck_weights and get_free_weight() >= truck_weights[0]:
            bridge_queue.append(Truck(truck_weights.pop(0), t))
            bridge_weight += bridge_queue[-1].weight
            updated = True

        if updated:
            t += 1
        else:
            t = when_truck_is_arrived(bridge_queue[0])

    return t
