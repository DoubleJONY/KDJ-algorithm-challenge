def solution(N, number):
     
  table = [[]]
  for i in range(1, 9):
    case = []
    for j in range(1, i):
        for k in table[j]: 
          for l in table[i - j]:
              case.append(k + l) 
              if k - l >= 0: 
                  case.append(k - l) 
              case.append(k * l)
              if l != 0 and k != 0:
                  case.append(k // l) 
    case.append(int(str(N) * i)) 
  
    if number in case:
        return i
    table.append(list(set(case)))
  return -1


print(solution(5,12))
print(solution(2,11))