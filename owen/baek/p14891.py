#회전 리스트 이동으로 표현
#12시 방향과 맞닿는 부분의 차이 += 2
#중간에 물려있는 톱니바퀴 -2 0 +2

#점수 12시방향을 기준으로 n은 0점 s는 [1,2,4,8]점

#n극 0, s극 1

# 시계 1, 반시계 -1

Gear_list = [list(map(str, input())) for _ in range(4)]
print(Gear_list)
rot = int(input())

rot_gear=[list(map(int, input().split())) for _ in range(rot)]

rp_List = [2 for _ in range(len(Gear_list))]

teeth_len = 8

rp_lp_interval = 4

#지금 기어의 상태로 양옆의 기어 상태 변경
for g_no, rot_direct in rot_gear:
    g_no = g_no -1
    rot_direct = rot_direct * -1

    check_gear = [ g_no + i for i in [-1,1] if (g_no + i) >0 and (g_no + i)  < len(Gear_list) ]

    for other_g in check_gear:
        
        if other_g > g_no:
            # 타기어의 왼쪽을 봐야함
            other_lp= rp_List[other_g] - rp_lp_interval
            pre_gear_rp = rp_List[g_no]
            if Gear_list[other_g][other_lp] != Gear_list[g_no][rp_List[g_no]]:
                rp_List[other_g] = (rp_List[other_g] + (rot_direct * -1)) % (teeth_len+1)

        else:
            # 타기어의 오른쪽을 봐야함, 현 기어의 왼쪽
            other_rp = rp_List[other_g]
            pre_gear_lp = rp_List[g_no] - rp_lp_interval
            print(other_g,other_rp,g_no, pre_gear_lp  )
            if Gear_list[other_g][other_rp] != Gear_list[g_no][pre_gear_lp]:
                rp_List[other_g] = (rp_List[other_g] + (rot_direct * -1)) % (teeth_len+1)

        
    
    rp_List[g_no] += rot_direct


# total_score
answer = 0
for i in range(len(Gear_list)):
    answer += int(Gear_list[i][rp_List[i] - int(rp_lp_interval / 2)]) * (i+1) * 2


print(answer)
