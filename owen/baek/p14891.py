#회전 리스트 이동으로 표현
#12시 방향과 맞닿는 부분의 차이 += 2
#중간에 물려있는 톱니바퀴 -2 0 +2

#점수 12시방향을 기준으로 n은 0점 s는 [1,2,4,8]점

#n극 0, s극 1

# 시계 1, 반시계 -1

Gear_list = [list(map(str, input())) for _ in range(4)]

rot = int(input())

rot_gear=[list(map(int, input().split())) for _ in range(rot)]

rp_List = [2 for _ in range(len(Gear_list))]

teeth_len = 8

rp_lp_interval = 4




def rotate_gear(gear_no, direct, rot_d): # direct -1:왼편, 1:오른편
    global rp_List, teeth_len, Gear_list, rp_lp_interval

    
    other_g = gear_no + direct
    if (other_g) < len(Gear_list) and (other_g) > 0:
        
        if direct == 1:
            # 타기어의 왼쪽을 봐야함
            other_lp= rp_List[other_g] - rp_lp_interval
            pre_gear_rp = rp_List[gear_no]
            print(other_g,other_lp,  gear_no, pre_gear_rp)
            if Gear_list[other_g][other_lp] != Gear_list[gear_no][pre_gear_rp]:
                rotate_gear(other_g, 1, rot_d*-1)

        else:
            # 타기어의 오른쪽을 봐야함, 현 기어의 왼쪽
            other_rp = rp_List[other_g]
            pre_gear_lp = rp_List[gear_no] - rp_lp_interval
            if Gear_list[other_g][other_rp] != Gear_list[gear_no][pre_gear_lp]:
                rotate_gear(other_g, -1, rot_d*-1)
                


    rp_List[gear_no] = (rp_List[gear_no] + (rot_d)) % (teeth_len)


if rot != 0:
    
    for g_no, rot_direct in rot_gear:
        g_no = g_no -1

        check_gear = [ i for i in [-1,1] if (g_no + i) >0 and (g_no + i)  < len(Gear_list) ]

        for direct in check_gear:
            rotate_gear(g_no, direct, rot_direct * -1)
 

# total_score
answer = 0
for i in range(len(Gear_list)):
    answer += int(Gear_list[i][rp_List[i] - int(rp_lp_interval / 2)]) * (2**i)


print(answer)
