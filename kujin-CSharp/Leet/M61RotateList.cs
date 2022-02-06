

public class Solution
{
    //Definition for singly-linked list.

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
    
    public ListNode RotateRight(ListNode head, int k)
    {
        ListNode startNode = head;
        ListNode currentNode = head;
        ListNode resultNode = null;
        

        Stack<ListNode> stack = new Stack<ListNode>();
        Queue<ListNode> queue = new Queue<ListNode>();

        if (head == null) return null;
        
        while (currentNode != null)
        {
            stack.Push(currentNode);
            currentNode = currentNode.next;
        }

        ListNode tmpNode = null;
        int stackCount = stack.Count;
        for (int i = 0; i < stackCount; i++)
        {
            tmpNode = stack.Pop();
            tmpNode.next = null;
            queue.Enqueue(tmpNode);
        }
        
        tmpNode = null;
        currentNode = null;
        
        for (int i = 0; i < k % (queue.Count); i++)
        {
            tmpNode = queue.Dequeue();
            queue.Enqueue(tmpNode);
        }
        
        int queueCount = queue.Count;
        for (int i = 0; i < queueCount; i++)
        {
            stack.Push(queue.Dequeue());
        }

        if (stack.Count == 0)
        {
            return head;
        }
        else
        {
            resultNode = stack.Pop();
            currentNode = resultNode;
            stackCount = stack.Count;
            for (int i = 0; i < stackCount; i++)
            {
                currentNode.next = stack.Pop();
                currentNode = currentNode.next;
            }

            return resultNode;
        }
    }
}