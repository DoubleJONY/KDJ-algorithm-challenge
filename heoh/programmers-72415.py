from collections import defaultdict
from functools import lru_cache
from queue import PriorityQueue

MAX_COST = 0x7fffffff
DY = [0, 1, 0, -1]
DX = [-1, 0, 1, 0]

cards = {}

def solution(board_, r, c):
    global cards
    board = board_
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

@lru_cache
def flip_card(candidates, start, end):
    visited = set()
    pq = PriorityQueue()
    pq.put((0, start))
    visited.add(start)

    while pq:
        t, cur = pq.get()
        if cur == end:
            return t
        
        for d in range(0, 4):
            next_ = move_dir(cur, d)
            if point_in_board(next_) and (not next_ in visited):
                pq.put((t+1, next_))
    
        for d in range(0, 4):
            next_ = move_dir_ctrl(cur, d, candidates)
            if point_in_board(next_) and (not next_ in visited):
                pq.put((t+1, next_))
    
    return MAX_COST

def move_dir(cur, d):
    y, x = cur
    return (y + DY[d], x + DX[d])

def bitset_mask(i):
    return 1 << i

def bitset_has(bitset, i):
    return (bitset & bitset_mask(i)) != 0

def bitset_add(bitset, i):
    return bitset | bitset_mask(i)

def bitset_remove(bitset, i):
    return bitset & ~bitset_mask(i)
