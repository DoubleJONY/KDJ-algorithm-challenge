# https://programmers.co.kr/learn/courses/30/lessons/42861

def find_root(disjoint_set, node):
    while node != disjoint_set[node]:
        node = disjoint_set[node]
    return node

def solution(n, costs):
    disjoint_set = [m for m in range(n)]
    
    tot_cost = 0
    for a, b, cost in sorted(costs, key=lambda cost: cost[2]):
        root_a = find_root(disjoint_set, a)
        root_b = find_root(disjoint_set, b)
        if root_a != root_b:
            disjoint_set[root_b] = root_a
            tot_cost += cost
            
    return tot_cost
