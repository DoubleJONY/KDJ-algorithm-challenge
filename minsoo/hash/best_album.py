# https://programmers.co.kr/learn/courses/30/lessons/42579

def solution(genres, plays):
    s_lists = {g: [] for g in set(genres)}
    for g, s_info in zip(genres, enumerate(plays)):
        s_lists[g].append(s_info)

    play = lambda info: info[1]
    best_album = []
    for s_info in sorted(s_lists.values(), key=lambda s_list: sum(map(play, s_list)), reverse=True):
        best_album.extend(map(lambda info: info[0], sorted(s_info, key=play, reverse=True)[:2]))
    return best_album
