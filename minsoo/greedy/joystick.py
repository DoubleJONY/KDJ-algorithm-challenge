# https://programmers.co.kr/learn/courses/30/lessons/42860

def cnt(curr, trgt):
    curr, trgt = ord(curr), ord(trgt)
    return min(trgt - curr, (curr - trgt) % 26)

def move_cnt(i, name, changed):
    left, right = (i - 1) % len(name), (i + 1) % len(name)
    left_cnt = right_cnt = 1
    
    while left_cnt < len(name) - 1 and left in changed or name[left] == 'A':
        left = (left - 1) % len(name)
        left_cnt += 1
        
    while right_cnt < len(name) - 1 and right in changed or name[right] == 'A':
        right = (right + 1) % len(name)
        right_cnt += 1
    
    return (left_cnt, left) if left_cnt < right_cnt else (right_cnt, right)

def solution(name):
    curr = ['A' for _ in name]
    changed = set()
    i, tot_cnt = 0, 0
    while i not in changed:
        tot_cnt += cnt(curr[i], name[i])
        changed.add(i)
        move, i = move_cnt(i, name, changed)
        tot_cnt += move
    
    return tot_cnt - move
