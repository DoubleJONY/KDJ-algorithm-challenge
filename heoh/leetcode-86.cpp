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
    ListNode* partition(ListNode* head, int x) {
        ListNode* ltDummy = new ListNode();  // Dummy node of 'less than'
        ListNode* geDummy = new ListNode();  // Dummy node of 'greater than or equal'
        ListNode* ltTail = ltDummy;
        ListNode* geTail = geDummy;
        
        for (ListNode* curr = head; curr; curr = curr->next) {
            if (curr->val < x) {
                ltTail->next = curr;
                ltTail = curr;
            }
            else {
                geTail->next = curr;
                geTail = curr;
            }
        }
        
        ListNode* ltHead = ltDummy->next;
        ListNode* geHead = geDummy->next;
        
        ltTail->next = geHead;
        geTail->next = NULL;
        
        return ltHead ? ltHead : geHead;
    }
};
