Plist = []
sup = int(input())
for i in list(map(int, input().split())):
    Plist.append(i)

can_list = list(map(int, input().split()))

subsup = 0

for i in Plist:
    over = i - can_list[0]
    if over > 0:
        if (over / can_list[1]) - (over//can_list[1]) > 0: 
            subsup += over//can_list[1] + 1
        else:
            subsup += over//can_list[1]

print(sup+subsup)
