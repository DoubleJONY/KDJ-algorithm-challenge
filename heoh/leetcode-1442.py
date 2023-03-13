class Solution:
    def countTriplets(self, arr: List[int]) -> int:
        n_triplets = 0
        for i in range(len(arr)):
            value = arr[i]
            for k in range(i+1, len(arr)):
                value ^= arr[k]
                if value == 0:
                    n_triplets += k - i

        return n_triplets


def test():
    assert Solution().countTriplets([2,3,1,6,7]) == 4
    assert Solution().countTriplets([1,1,1,1,1]) == 10


if __name__ == '__main__':
    test()
