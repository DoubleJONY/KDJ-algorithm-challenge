vertices = [None]
total_ancestors = {}
total_descendants = {}

def solution(n, results):
    for i in range(1, n+1):
        vertices.append(Vertex(i))
    
    for a, b in results:
        Vertex.connect(vertices[a], vertices[b])

    n_fixables = 0
    for i in range(1, n+1):
        v = vertices[i]
        if is_fixable(v, n):
            n_fixables += 1

    answer = n_fixables
    return answer


def is_fixable(v, n):
    return len(get_total_ancestors(v)) + len(get_total_descendants(v)) == n - 1


def get_total_ancestors(v):
    if v in total_ancestors:
        return total_ancestors[v]
    
    g = set(v.ancestors)
    for a in v.ancestors:
        g |= get_total_ancestors(a)

    total_ancestors[v] = g
    return total_ancestors[v]


def get_total_descendants(v):
    if v in total_descendants:
        return total_descendants[v]
    
    g = set(v.descendants)
    for d in v.descendants:
        g |= get_total_descendants(d)

    total_descendants[v] = g
    return total_descendants[v]


class Vertex:
    def __init__(self, id):
        self.id = id
        self.ancestors = []
        self.descendants = []

    @classmethod
    def connect(cls, ancestor, descendant):
        ancestor.descendants.append(descendant)
        descendant.ancestors.append(ancestor)
