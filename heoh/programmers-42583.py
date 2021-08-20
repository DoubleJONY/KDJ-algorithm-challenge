def solution(bridge_length, weight, truck_weights):
    simulator = BridgeSimulator(bridge_length, weight, truck_weights)
    return simulator.simulate()


class BridgeSimulator:
    def __init__(self, bridge_length, weight, truck_weights):
        self.bridge = Bridge(bridge_length, weight)
        self.trucks = [Truck(w) for w in truck_weights]

    def init_simulation(self):
        self.wating_trucks = list(self.trucks)
        self.arrived_trucks = []

    def simulate(self):
        self.init_simulation()
        bridge = self.bridge

        t = 0
        while not self.all_truck_is_arrived():
            updated = False

            try:
                head_truck = bridge.get_head_truck()
                if head_truck.is_arrived_end_of(bridge, t):
                    head_truck = bridge.pop_head_truck()
                    self.add_arrived_truck(head_truck)
                    updated = True
            except:
                pass

            try:
                next_truck = self.get_wating_truck()
                if next_truck.can_get_on(bridge):
                    next_truck = self.pop_wating_truck()
                    next_truck.get_on(bridge, t)
                    updated = True
            except:
                pass

            if not updated:
                t = head_truck.get_arriving_time(bridge)
            else:
                t += 1

        return t

    def all_truck_is_arrived(self):
        return set(self.arrived_trucks) == set(self.trucks)

    def add_arrived_truck(self, truck):
        self.arrived_trucks.append(truck)

    def get_wating_truck(self):
        return self.wating_trucks[0]

    def pop_wating_truck(self):
        return self.wating_trucks.pop(0)


class Bridge:
    def __init__(self, bridge_length, weight):
        self.length = bridge_length
        self.max_weight = weight
        self.total_weight = 0
        self.queue = []

    @property
    def free_weight(self):
        return self.max_weight - self.total_weight

    def get_head_truck(self):
        return self.queue[0]

    def pop_head_truck(self):
        truck = self.queue.pop(0)
        self.total_weight -= truck.weight
        return truck

    def add(self, truck):
        self.queue.append(truck)
        self.total_weight += truck.weight


class Truck:
    def __init__(self, weight):
        self.weight = weight
        self.start_time = None

    def can_get_on(self, bridge):
        return self.weight <= bridge.free_weight

    def is_arrived_end_of(self, bridge, t):
        moved_length = t - self.start_time
        return moved_length >= bridge.length

    def get_on(self, bridge, t):
        bridge.add(self)
        self.start_time = t

    def get_arriving_time(self, bridge):
        return self.start_time + bridge.length
