# https://leetcode.com/problems/rotate-list/

# Runtime: 36 ms, faster than 84.99% of Python3 online submissions for Rotate List.
# Memory Usage: 13.9 MB, less than 98.94% of Python3 online submissions for Rotate List.

from typing import Optional
from itertools import count


class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next


class Solution:
    def rotateRight(self, head: Optional[ListNode], k: int) -> Optional[ListNode]:
        if not head:
            return head

        prev = node = head
        for n in count(1):
            prev, node = node, node.next
            if not node:
                break
        k %= n
        if not k:
            return head

        end, prev, node = prev, head, head
        for _ in range(n - k):
            prev, node = node, node.next

        prev.next = None
        end.next = head
        return node
