# https://programmers.co.kr/learn/courses/30/lessons/42628?language=python3

from collections import deque

class DEPQ:
    def __init__(self):
        self._data = deque()

    def insert(self, number):
        data = self._data
        if data:
            if number < data[0]:
                data.appendleft(number)
            elif number >= data[-1]:
                data.append(number)
            else:
                half = (len(data) + 1) // 2
                shift = 0

                while True:
                    if number >= data[0]:
                        data.rotate(-half)
                        shift += half
                    else:
                        data.rotate(half)
                        shift -= half
                    
                    half = (half + 1) // 2

                    if data[0] > number >= data[-1]:
                        if shift > len(data) // 2:
                            shift -= len(data)
                            
                        if shift < 0:
                            data.append(number)
                        else:
                            data.appendleft(number)
                        data.rotate(shift)
                        break
        else:
            data.append(number)

    def pop_highest(self):
        return self._data.pop()

    def pop_lowest(self):
        return self._data.popleft()

    def highest(self):
        return self._data[-1]

    def lowest(self):
        return self._data[0]

    def __len__(self):
        return len(self._data)

def solution(operations):
    depq = DEPQ()
    for op in operations:
        if op[0] == 'I':
            item = int(op[2:])
            depq.insert(item)
        elif depq:
            if op[-2] == '-':
                depq.pop_lowest()
            else:
                depq.pop_highest()

    return [depq.highest(), depq.lowest()] if depq else [0, 0]
