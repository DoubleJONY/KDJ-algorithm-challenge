def solution(citations):
    citations = sorted(citations, reverse=True)
    n = len(citations)
    for h in range(n, -1, -1):
        if not citations[h-1] >= h:
            continue
        if h >= n or citations[h] <= h:
            return h
    return 0
