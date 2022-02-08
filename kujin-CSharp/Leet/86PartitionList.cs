namespace M86PartitionList
{
    // Definition for singly-linked list.
    public class ListNode
    {
        public int val;
        public ListNode next;

        public ListNode(int val = 0, ListNode next = null)
        {
            this.val = val;
            this.next = next;
        }
    }

    public class Solution
    {
        public ListNode Partition(ListNode head, int x)
        {
            ListNode currentNode = head;
            ListNode startNode = null;

            ListNode anotherListCurrentNode = null;

            ListNode anotherListNode = null;

            int seperator = x;

            while (currentNode != null && currentNode.val < seperator)
            {
                if (anotherListNode == null)
                {
                    anotherListNode = currentNode;
                    anotherListCurrentNode = currentNode;
                }
                else
                {
                    anotherListCurrentNode.next = currentNode;
                    anotherListCurrentNode = anotherListCurrentNode.next;
                }

                currentNode = currentNode.next;
            }

            //다른 리스트 끊어줌
            //다른 리스트 끊어줌
            if (anotherListCurrentNode != null) anotherListCurrentNode.next = null;
            if (currentNode != null) startNode = currentNode;

            while (currentNode != null && currentNode.next != null)
            {
                if (currentNode.next.val >= seperator)
                {
                    currentNode = currentNode.next;
                    continue;
                }

                if (anotherListNode == null)
                {
                    anotherListNode = currentNode.next;
                    anotherListCurrentNode = currentNode.next;
                }
                else
                {
                    anotherListCurrentNode.next = currentNode.next;
                    anotherListCurrentNode = anotherListCurrentNode.next;
                }

                currentNode.next = currentNode.next.next;
                anotherListCurrentNode.next = null;
            }

            if (currentNode != null && anotherListCurrentNode != null)
            {
                anotherListCurrentNode.next = startNode;
                return anotherListNode;
            }
            else
            {
                return head;
            }
        }
    }
}