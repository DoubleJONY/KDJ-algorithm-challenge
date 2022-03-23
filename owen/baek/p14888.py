lenNum = int(input())

numList = list(map(int, input().split()))
add, sub, mul, div = map(int, input().split())
maxVal = -(1e8)
minVal = 1e8

def dfs(i, L):
    global add, sub, mul, div, maxVal, minVal

    if i == lenNum:
        maxVal = max(maxVal, L)
        minVal = min(minVal, L)
    else:
        if add>0:
            add -= 1
            dfs(i+1, L+numList[i])
            add += 1
        if sub>0:
            sub -= 1
            dfs(i+1, L-numList[i])
            sub += 1
        if mul>0:
            mul -= 1
            dfs(i+1, L*numList[i])
            mul += 1
        if div>0:
            div -= 1
            dfs(i+1, int(L/numList[i]))
            div += 1
    



dfs(1, numList[0])

print(maxVal)
print(minVal)
