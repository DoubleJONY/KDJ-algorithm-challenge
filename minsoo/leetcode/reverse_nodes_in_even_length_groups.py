# https://leetcode.com/problems/reverse-nodes-in-even-length-groups/

from typing import Optional
from itertools import count

# Definition for singly-linked list.
class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next


class Solution:
    def reverseEvenLengthGroups(self, head: Optional[ListNode]) -> Optional[ListNode]:
        prev = node = head
        for group_num in count(1):
            nodes = []
            _prev = prev
            for n in range(group_num):
                if not node:
                    n -= 1
                    break
                nodes.append(node)
                prev, node = node, node.next

            if n % 2:
                nodes[0].next = node
                _prev.next = nodes[-1]
                for i in range(1, len(nodes)):
                    nodes[i].next = nodes[i - 1]

                prev, nodes[0].next = nodes[0], node

            if not node:
                return head
