import math

def solution(progresses, speeds):
    answer = [100-i for i in progresses]
    answer = [math.ceil(day/speed) for day, speed in zip(answer, speeds)]
    
    count_progress = []
    while len(answer) > 0:

        pro_cursor = answer[0]
        del answer[0]
        counter = 1
        for p in answer:
            if p <= pro_cursor:
                counter += 1
            else:
                break

        count_progress.append(counter)
        del answer[:counter-1]
        
    answer = count_progress
    return answer



print(solution([93, 30, 55],[1, 30, 5] ))