# https://leetcode.com/problems/partition-list/

# Runtime: 36 ms, faster than 79.78% of Python3 online submissions for Partition List.
# Memory Usage: 14 MB, less than 94.14% of Python3 online submissions for Partition List.

from typing import Optional


class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next


class Solution:
    def partition(self, head: Optional[ListNode], x: int) -> Optional[ListNode]:
        if not head:
            return head

        lt_dummy = lt_node = ListNode()
        ge_dummy = ge_node = ListNode()

        while head:
            if head.val < x:
                lt_node.next = ListNode(head.val)
                lt_node = lt_node.next
            else:
                ge_node.next = ListNode(head.val)
                ge_node = ge_node.next

            head = head.next

        lt_node.next = ge_dummy.next
        return lt_dummy.next
