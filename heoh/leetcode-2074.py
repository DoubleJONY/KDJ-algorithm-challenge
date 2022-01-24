import itertools
from collections import deque
from typing import Optional


class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next


class Solution:
    def reverseEvenLengthGroups(self, head: Optional[ListNode]) -> Optional[ListNode]:
        result_dummy = ListNode()
        result_tail = result_dummy

        cursor = head
        for max_group_length in itertools.count(start=1):
            if cursor is None:
                break

            group = deque()
            for _ in range(max_group_length):
                if cursor is None:
                    break
                group.append(cursor.val)
                cursor = cursor.next

            group_length_is_even = len(group) % 2 == 0
            if group_length_is_even:
                get_next_value = group.pop
            else:
                get_next_value = group.popleft

            for _ in range(len(group)):
                result_tail.next = ListNode(get_next_value())
                result_tail = result_tail.next

            if cursor is None:
                break
        
        result_head = result_dummy.next
        return result_head
