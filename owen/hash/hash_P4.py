from collections import defaultdict

def solution(genres, plays):
    answer = []
    rank_dict = defaultdict(int)
    song_dict = defaultdict(list)

    for idx, (genre, play) in enumerate(zip(genres, plays)):
        rank_dict[genre] += play
        song_dict[genre].append([idx,play])
        
    sorted_rank = sorted(rank_dict.items(),key=lambda x:x[1], reverse=True)

    for key, _ in sorted_rank:
        sorted_song = sorted(song_dict[key], key = lambda x:x[1], reverse=True)[:2]
        for idx,_ in sorted_song:
            answer.append(idx)

    return answer


# 채점을 시작합니다.
# 정확성  테스트
# 테스트 1 〉	통과 (0.02ms, 10.4MB)
# 테스트 2 〉	통과 (0.02ms, 10.3MB)
# 테스트 3 〉	통과 (0.02ms, 10.3MB)
# 테스트 4 〉	통과 (0.01ms, 10.2MB)
# 테스트 5 〉	통과 (0.09ms, 10.3MB)
# 테스트 6 〉	통과 (0.11ms, 10.3MB)
# 테스트 7 〉	통과 (0.04ms, 10.2MB)
# 테스트 8 〉	통과 (0.03ms, 10.2MB)
# 테스트 9 〉	통과 (0.02ms, 10.3MB)
# 테스트 10 〉	통과 (0.06ms, 10.3MB)
# 테스트 11 〉	통과 (0.03ms, 10.3MB)
# 테스트 12 〉	통과 (0.06ms, 10.3MB)
# 테스트 13 〉	통과 (0.05ms, 10.3MB)
# 테스트 14 〉	통과 (0.08ms, 10.2MB)
# 테스트 15 〉	통과 (0.03ms, 10.3MB)
# 채점 결과
# 정확성: 100.0
# 합계: 100.0 / 100.0