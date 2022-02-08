class Solution:
    def partition(self, head: Optional[ListNode], x: int) -> Optional[ListNode]:
        lessNode = ListNode()
        greaterNode = ListNode()
        cur = head
        cur_less = lessNode
        cur_greater = greaterNode

        while cur:
            if cur.val < x:
                node = ListNode(cur.val)
                cur_less.next = node
                cur_less = cur_less.next
            else:
                node = ListNode(cur.val)
                cur_greater.next = node
                cur_greater = cur_greater.next
                
            cur = cur.next
            
        cur_less.next = greaterNode.next
        lessNode = lessNode.next
        return lessNode     