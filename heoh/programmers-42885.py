def solution(people, limit):
    n_boats, n_remaining = rescue_pair_greedy(people, limit)
    n_boats += n_remaining
    return n_boats

def rescue_pair_greedy(people, limit):
    people = sorted(people)
    n_remaining = 0
    n_boats = 0

    left = 0
    right = len(people) - 1
    while left < right:
        if people[left] + people[right] > limit:
            n_remaining += 1
            right -= 1
        else:
            n_boats += 1
            left += 1
            right -= 1

    if left == right:
        n_remaining += 1

    return n_boats, n_remaining
