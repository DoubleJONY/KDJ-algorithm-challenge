n = int(input())
sand_map = [list(map(int, input().split())) for _ in range(n)]

#모래비율
left = [[-1,1,0.01],[-2,0,0.02],[-1,0,0.07],[-1,-1,0.1],[0,-2,0.05],[1,1,0.01],[2,0,0.02],[1,0,0.07],[1,-1,0.1],[0,-1,0]]
right = [[x,-y,z] for x,y,z in left]
up = [[y,x,z] for x,y,z in left]
down = [[-y,x,z] for x,y,z in left]

#좌,하,우,상
dx = [0,1,0,-1]
dy = [-1,0,1,0]

answer = 0
tx, ty = n//2, n//2
def calculate_sand(time, mx, my, windsand):
    global answer, tx, ty

    for _ in range(time):
        tx += mx
        ty += my
        if tx < 0 or ty < 0:
            break

        total = 0
        
        for x,y,z in windsand:
            nx = tx + x
            ny = ty + y
            if z == 0:
                tmp_sand = sand_map[tx][ty] - total
            else:
                tmp_sand = int(sand_map[tx][ty] * z)
                total += tmp_sand
            if 0<= nx < n and 0<= ny < n:
                sand_map[nx][ny] += tmp_sand

            else:
                answer += tmp_sand




for i in range(1,n+1):
    #left, down
    #right, up
    if i % 2:
        calculate_sand(i, dx[0], dy[0], left)
        calculate_sand(i, dx[1], dy[1], down)
    else:
        calculate_sand(i, dx[2], dy[2], right)
        calculate_sand(i, dx[3], dy[3], up)


print(answer)
