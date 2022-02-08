class Solution:
    def rotateRight(self, head: Optional[ListNode], k: int) -> Optional[ListNode]:
        listtmp = list()
        cur = head
        while cur:
            listtmp.append(cur.val)
            cur = cur.next
        try:
            rotate = k % len(listtmp)
            if rotate != 0:
                tailList = listtmp[:-rotate]
                headList = listtmp[-rotate:]
                rotateList = headList + tailList
            else:
                rotateList = listtmp

            
            returnNode = ListNode()
            for i, item in enumerate(rotateList):
                if i == 0:
                    returnNode.val = item
                else:
                    node = returnNode
                    while node.next != None:
                        node = node.next
                    node.next = ListNode(item)
        except:
            return None
                
        return returnNode