class Solution:
    def lenLongestFibSubseq(self, arr: List[int]) -> int:
        self.n = len(arr)
        self.value = arr
        self.index = {}
        for i, v in enumerate(arr):
            self.index[v] = i

        best = 0
        for first in range(self.n):
            for second in range(first+1, self.n):
                this_case_length = self.get_longest_fib_subseq_length_from(first, second)
                best = max(best, this_case_length)
        
        return best

    def get_longest_fib_subseq_length_from(self, first: int, second: int) -> int:
        length = 2
        prev_value = self.value[first]
        curr_value = self.value[second]
        next_value = prev_value + curr_value
        while next_value in self.index:
            length += 1
            prev_value = curr_value
            curr_value = next_value
            next_value = prev_value + curr_value

        if length == 2:
            return 0
        return length
