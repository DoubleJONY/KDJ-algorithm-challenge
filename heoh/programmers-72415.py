from collections import defaultdict

MAX_COST = 0x7fffffff
cards = {}

def solution(board, r, c):
    global cards
    cards = parse_cards(board)
    candidates = make_bitset(cards.keys())
    cur = (r, c)
    return find_min_cost(candidates, cur)

def parse_cards(board):
    cards = defaultdict(list)
    for r, row in enumerate(board):
        for c, shape in enumerate(row):
            if shape != 0:
                cards[shape].append((r, c))
    return cards

def make_bitset(shapes):
    b = 0
    for s in shapes:
        b |= 1 << s
    return b

# params
# - candidates: 남은 모양 (bitset)
# - r, c: 현재 커서 위치
# returns
# - 현재 상황에서 최적으로 처리했을 때의 비용 (최소 이동 횟수)
def find_min_cost(candidates, cur):
    if not candidates:
        return 0

    min_cost = MAX_COST
    for s in range(1, 6+1):
        if not bitset_has(candidates, s):
            continue

        cost_0 = 0
        cost_0 += flip_card(candidates, cur, cards[s][0])
        cost_0 += flip_card(candidates, cards[s][0], cards[s][1])
        bitset_remove(candidates, s)
        cost_0 += find_min_cost(candidates, cards[s][1])
        bitset_add(candidates, s)

        cost_1 = 0
        cost_1 += flip_card(candidates, cur, cards[s][1])
        cost_1 += flip_card(candidates, cards[s][1], cards[s][0])
        bitset_remove(candidates, s)
        cost_1 += find_min_cost(candidates, cards[s][0])
        bitset_add(candidates, s)

        min_cost = min(cost_0, cost_1)

    return min_cost
