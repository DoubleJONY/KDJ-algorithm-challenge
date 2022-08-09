

graph = [[1], [2], [3], [4], [5], [6, 21], [7], [8], [9], [10], [11, 25], [12], [13], [14], [15], [16, 27], [
    17], [18], [19], [20], [32], [22], [23], [24], [30], [26], [24], [28], [29], [24], [31], [20], [32]]
score = [0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30,
         32, 34, 36, 38, 40, 13, 16, 19, 25, 22, 24, 28, 27, 26, 30, 35, 0]


dice = list(map(int, input().split()))


answer = 0


def tracking(turn, result, pieceList):
    global answer

    if turn >= 10:
        answer = max(result, answer)
        return

    for i in range(4):
        idx = pieceList[i]
        if len(graph[idx]) == 2:
            idx = graph[idx][1]
        else:
            idx = graph[idx][0]

        for _ in range(1, dice[turn]):
            idx = graph[idx][0]

        if idx == 32 or (idx < 32 and idx not in pieceList):
            tmp = pieceList[i]
            pieceList[i] = idx
            tracking(turn + 1, result+score[idx], pieceList)
            pieceList[i] = tmp


tracking(0, 0, [0, 0, 0, 0])
print(answer)
