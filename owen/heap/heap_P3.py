import heapq


class solution_class:
    def __init__(self, operations):
        self.down_heap = []
        self.up_heap = []
        heapq.heapify(self.up_heap)
        heapq.heapify(self.down_heap)
        self.answer = [0,0]
        self.run(operations)

    def __repr__(self) :
        return f'{self.answer[0]} {self.answer[1]}'

    def operation_push(self, item):
        heapq.heappush(self.down_heap, item)
        heapq.heappush(self.up_heap, item * -1)

    def operation_del(self, order):
        if order == 1:
            heapq.heappop(self.up_heap)
        else:
            heapq.heappop(self.down_heap)

    def run(self, operations):
    

        for order in operations:
            order = order.split(" ")

            if order[0] == "I":
                self.operation_push(int(order[1]))
            else:
                try:
                    self.operation_del(int(order[1]))
                except IndexError:
                    pass
        final_list = list(set(self.down_heap) & set([ -i for i in self.up_heap]))
        
        if len(final_list) > 1:
            self.answer[1],self.answer[0] = min(final_list), max(final_list)
        

def solution(operations):
    return list(map(int, (str(solution_class(operations)).split(" "))))





# 정확성  테스트
# 테스트 1 〉	통과 (0.04ms, 10.4MB)
# 테스트 2 〉	통과 (0.04ms, 10.4MB)
# 테스트 3 〉	통과 (0.04ms, 10.3MB)
# 테스트 4 〉	통과 (0.04ms, 10.4MB)
# 테스트 5 〉	통과 (0.03ms, 10.4MB)
# 테스트 6 〉	통과 (0.04ms, 10.4MB)
# 채점 결과
# 정확성: 100.0
# 합계: 100.0 / 100.0