class Solution:
    def preimageSizeFZF(self, k: int) -> int:
        def Zeroes(x):
            cnt = 0

            while x > 0:
                cnt += x // 5
                x //= 5

            return cnt


        head = 0
        tail = 10**10
        while head < tail:
            pivot = (head + tail )//2
            if Zeroes(pivot) < k:
                head = pivot + 1
            else:
                tail = pivot
            
            
        if Zeroes(head) == k:
            return 5
        return 0
        

# 10^9 =  
# 1 * 2 * 3 * 4 * 5 * 6 * 7 * 8 * 9 * 10 * 11
# 1 * (2*1) * 3 * (2*2) * (5*1) * (2*3) * 7 * (2*4) * 9 * (2*5) * 11
# f(11) = 2 number is 11, 5 number is 2, 2*5 multiply is 2



# f(0) = 0
# f(1) = 0
# f(2) = 0
# f(3) = 0
# f(4) = 0
# f(5) = 1
# f(6) = 1
# f(7) = 1
# f(8) = 1
# f(9) = 1
# f(10) = 2
#1 * 2 * 3 * 4 * 5 * 6 * 7 * 8 * 9 * (5*2)