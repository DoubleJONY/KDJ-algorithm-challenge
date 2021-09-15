def solution(brown, yellow):
    for y in range(1, 2500):
        for x in range(y, 2500):
            yellow_area = x * y
            if yellow_area > yellow:
                break
            if yellow_area == yellow:
                brown_area = x*2 + y*2 + 4
                if brown_area == brown:
                    return [x+2, y+2]
