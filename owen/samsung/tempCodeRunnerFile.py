Plist = []
sup = int(input())
for _ in range(sup):
    Plist.append(int(input()))
Can_sup = int(input())
Can_subsup = int(input())

subsup = 0

for i in Plist:
    over = i - Can_sup
    if over > 0:
        if (over / Can_subsup) - (over//Can_subsup) > 0: 
            subsup += over//Can_subsup + 1
        else:
            subsup += over//Can_subsup

print(sup+subsup)
