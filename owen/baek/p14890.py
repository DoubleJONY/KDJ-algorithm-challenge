
N, L  = map(int, input().split())
map_row = []
map_column = [[0]*N]*N

for i in range(N):
    map_row.append(list(map(int, input().split())))
    
map_column = list(map(list, zip(*map_row)))
line_list = map_row + map_column     
answer = 0
def check_line(l):
    
    hill = 0
    Vsum = 0
    down_list = [ele-min(l) for ele in l]
    mean_l = sum(l) / len(l)
    max_l = max(down_list)
    if mean_l == 0 :
        return 1

    pause_tmp = 0
    pre_element = down_list[0]
    for element in down_list:
        if pre_
        
        
        

for line in line_list:
    answer += check_line(line)
    
    
print(answer)




