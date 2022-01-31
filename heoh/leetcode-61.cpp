/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode() : val(0), next(nullptr) {}
 *     ListNode(int x) : val(x), next(nullptr) {}
 *     ListNode(int x, ListNode *next) : val(x), next(next) {}
 * };
 */
class Solution {
public:
    ListNode* rotateRight(ListNode* head, int k) {
        int size = countNodes(head);
        if (size == 0) {
            return head;
        }
        
        int rotation = k % size;
        
        ListNode* rotatedHead;
        if (rotation > 0) {
            ListNode* tail = jumpNodes(head, size - 1);
            tail->next = head;

            ListNode* rotatedTail = jumpNodes(head, size - rotation - 1);
            rotatedHead = rotatedTail->next;
            rotatedTail->next = NULL;
        }
        else {
            rotatedHead = head;
        }

        return rotatedHead;
    }
    
    int countNodes(ListNode* head) {
        int nNodes = 0;
        while (head) {
            nNodes++;
            head = head->next;
        }
        return nNodes;
    }
    
    ListNode* jumpNodes(ListNode* node, int n) {
        for (int i = 0; i < n; i++) {
            node = node->next;
        }
        return node;
    }
};
