class Solution:
    def countTriplets(self, arr: List[int]) -> int:
        self.init_cache(arr)
        
        n_triplets = 0
        for i in range(len(arr)):
            for k in range(i+1, len(arr)):
                if self.xor_range(i, k) == 0:
                    n_triplets += k - i

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


def test():
    assert Solution().countTriplets([2,3,1,6,7]) == 4
    assert Solution().countTriplets([1,1,1,1,1]) == 10


if __name__ == '__main__':
    test()
