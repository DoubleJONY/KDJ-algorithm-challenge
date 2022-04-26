# 시작점, 시작방향, 세대수


#0 동, 1 북, 2 서, 3 남

map_list = [[0]*101 for _ in range(101)]

# print(len(map_list[0]))

n =  int(input())



dragons = [list(map(int, input().split())) for _ in range(n)]

print(dragons)
#x->y y->x 
dy = [1,0,-1,0]
dx = [0,-1,0,1]


    
    

for i in range(n):
    y,x,d,g = dragons[i]
    map_list[x][y] = 1
    
    curve = [d]
    for j in range(g):
        for k in range(len(curve) - 1, -1, -1):
            curve.append((curve[k] + 1) % 4)

    
    for j in range(len(curve)):
        x += dx[curve[j]]
        y += dy[curve[j]]
        if x < 0 or x >= 101 or y < 0 or y >= 101:
            continue

        map_list[x][y] = 1
        
        
answer = 0

for i in range(100):
    for j in range(100):
        if map_list[i][j] + map_list[i+1][j] \
            + map_list[i][j+1] + map_list[i+1][j+1] == 4:
            answer += 1 
            
            
print(answer)
    

    
    