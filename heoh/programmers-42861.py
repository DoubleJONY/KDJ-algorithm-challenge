def solution(n, costs):
    costs = sorted(costs, key=lambda x: x[2])
    group_map = {v: set([v]) for v in range(n)}

    def is_same_group(v0, v1):
        return group_map[v0] == group_map[v1]

    def union(v0, v1):
        g = group_map[v0] | group_map[v1]
        for v in g:
            group_map[v] = g

    total_cost = 0
    for edge in costs:
        v0, v1, cost = edge
        if is_same_group(v0, v1):
            continue

        union(v0, v1)
        total_cost += cost

    return total_cost
