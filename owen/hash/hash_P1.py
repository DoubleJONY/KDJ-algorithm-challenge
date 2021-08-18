# 완주하지 못한 선수

import collections

def solution(participant, completion):

    answer = list(collections.Counter(participant) - collections.Counter(completion))[0]
    
    return answer



# 정확성  테스트
# 테스트 1 〉	통과 (0.06ms, 10.1MB)
# 테스트 2 〉	통과 (0.04ms, 10.2MB)
# 테스트 3 〉	통과 (0.24ms, 10.3MB)
# 테스트 4 〉	통과 (0.49ms, 10.3MB)
# 테스트 5 〉	통과 (0.45ms, 10.4MB)
# 효율성  테스트
# 테스트 1 〉	통과 (27.55ms, 24.2MB)
# 테스트 2 〉	통과 (51.17ms, 27.7MB)
# 테스트 3 〉	통과 (54.32ms, 30MB)
# 테스트 4 〉	통과 (71.58ms, 39MB)
# 테스트 5 〉	통과 (73.01ms, 39MB)


