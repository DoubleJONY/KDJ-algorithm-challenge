def solution(routes):
    routes = sorted(routes, key=lambda e: e[1])
    n_cams = 0

    while routes:
        x = routes[0][1]
        routes = list(filter(lambda e: x < e[0] or e[1] < x, routes))
        n_cams += 1

    return n_cams
