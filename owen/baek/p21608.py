

n = int(input())
favorite_friends = [list(map(int, input().split())) for _ in range(n**2)]
dict_ff = {}

for i in favorite_friends:

    dict_ff[i[0]] = i[1:]


# 상하좌우
dx = [-1,1,0,0]
dy = [0,0,-1,1]

class_map = [[0]*n for _ in range(n)]

def run(c_s):
    global class_map, dx, dy, dict_ff
    max_empty = -1
    max_f = -1
    return_x = -1
    return_y = -1
    tmp_nof_noe = []
    for i in range(n):
        for j in range(n):
            tempty = 0
            tfriend = 0
            #4방향 칸 조회
            if class_map[i][j] == 0:
            
                for k in range(4):
                    tx, ty = i + dx[k], j + dy[k]
                    if tx >= n or ty >= n or tx < 0 or ty < 0:
                        continue
                    if class_map[tx][ty] == 0:
                        tempty += 1
                    elif class_map[tx][ty] in dict_ff[c_s]:
                        tfriend += 1

                if max_f <= tfriend:
                    if max_f == tfriend:
                        #빈칸비교
                        if max_empty < tempty:
                            return_x = i
                            return_y = j
                            max_empty = tempty
                            max_f = tfriend
                    else: # tfriend 가 클때
                        return_x = i
                        return_y = j
                        max_empty = tempty
                        max_f = tfriend
                tmp_nof_noe.append([i,j])
              
    if return_x == -1:
        return_x = tmp_nof_noe[0][0]
        return_y = tmp_nof_noe[0][1]


    #결과 좌표
    return return_x, return_y


for i in favorite_friends:
    px, py = run(i[0]) # 좌표

    class_map[px][py] = i[0]

answer = 0
# print(class_map)
for i in range(n):
    for j in range(n):
        fcount =0
        for k in range(4):
            tx, ty = i + dx[k], j + dy[k]
            if tx < n and ty < n and tx >= 0 and ty >= 0:
                
                if class_map[tx][ty] in dict_ff[class_map[i][j]]:
                    fcount += 1

        
        answer += int(10 ** (fcount-1))

print(answer)


