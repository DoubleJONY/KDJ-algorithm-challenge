# https://programmers.co.kr/learn/courses/30/lessons/43163

def is_similar(word1, word2):
    for i in range(len(word1)):
        if word1[:i] == word2[:i] and word1[i + 1:] == word2[i + 1:]:
            return True
    return False

def solution(begin, target, words):
    if target not in words:
        return 0

    stk = [(begin, 0, 0)]
    while stk:
        curr, visited, cnt = stk.pop()
        if curr == target:
            return cnt

        for i, word in enumerate(words):
            if word != curr and not visited & (1 << i):
                if is_similar(word, curr):
                    stk.append((word, visited | (1 << i), cnt + 1))
    return 0
