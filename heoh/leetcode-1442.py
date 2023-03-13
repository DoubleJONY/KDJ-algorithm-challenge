class Solution:
    def countTriplets(self, arr: List[int]) -> int:
        self.init_cache(arr)
        
        n_triplets = 0
        for i in range(len(arr)):
            for j in range(i+1, len(arr)):
                for k in range(j, len(arr)):
                    a = self.xor_range(i, j-1)
                    b = self.xor_range(j, k)
                    if a == b:
                        n_triplets += 1

        return n_triplets
            

    def init_cache(self, arr: List[int]):
        cache = {}
        for i in range(len(arr)):
            value = 0
            for j in range(i, len(arr)):
                value ^= arr[j]
                cache[i, j] = value
        self.cache = cache

    def xor_range(self, left, right):
        return self.cache[left, right]
