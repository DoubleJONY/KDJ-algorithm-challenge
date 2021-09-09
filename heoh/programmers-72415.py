from collections import defaultdict


cards = {}

def solution(board, r, c):
    global cards
    cards = parse_cards(board)
    candidates = make_bitset(cards.keys())
    return find_min_cost(candidates, r, c)

def parse_cards(board):
    cards = defaultdict(list)
    for r, row in enumerate(board):
        for c, shape in enumerate(row):
            if shape != 0:
                cards[shape].append((r, c))
    return cards
