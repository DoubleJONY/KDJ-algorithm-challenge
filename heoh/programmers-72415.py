cards = {}

def solution(board, r, c):
    global cards
    cards = parse_cards(board)
    candidates = make_bitset(cards.keys())
    return find_min_cost(candidates, r, c)
