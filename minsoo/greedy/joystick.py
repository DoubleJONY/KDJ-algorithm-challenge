# https://programmers.co.kr/learn/courses/30/lessons/42860

def get_char_cnt(letter):
    return min((dist := ord(letter) - ord('A')), -dist % 26)

def get_move_cnt(name, pos, visited, d):
    if visited == (1 << len(name)) - 1:
        return pos, 0

    cnt = 0
    while 1 << pos & visited:
        pos = (pos + d) % len(name)
        cnt += 1
    return pos, cnt

def find_minimum(name, visited, pos=0, cnt=0):
    if visited == (1 << len(name)) - 1:
        return cnt

    cnt += get_char_cnt(name[pos])
    visited |= 1 << pos
    left_pos, left_cnt = get_move_cnt(name, pos, visited, -1)
    right_pos, right_cnt = get_move_cnt(name, pos, visited, 1)
    
    return min(
        find_minimum(name, visited, left_pos, cnt + left_cnt),
        find_minimum(name, visited, right_pos, cnt + right_cnt)
    )

def solution(name):
    return find_minimum(name, sum(1 << n for n, c in enumerate(name) if c == 'A'))
