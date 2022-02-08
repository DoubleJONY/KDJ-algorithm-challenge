package com.rhino.leetcode.medium;

public class Medium00086_Partition_List {

    public static void main(String[] args) {
        Solution s = new Solution();

        ListNode head = new ListNode();
        head.val = 1;
        ListNode last = head;
        ListNode next = new ListNode();
        next.val = 4;
        last.next = next;
        last = next;
        next = new ListNode();
        next.val = 3;
        last.next = next;
        last = next;
        next = new ListNode();
        next.val = 2;
        last.next = next;
        last = next;
        next = new ListNode();
        next.val = 5;
        last.next = next;
        last = next;
        next = new ListNode();
        next.val = 2;
        last.next = next;
        last = next;
        ListNode result = s.partition(head, 3);
        System.out.println("T");
    }

    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    // my solution
    static class Solution {
        public ListNode partition(ListNode head, int x) {
            ListNode left = null,right = null,current = head;
            ListNode leftTail = null,rightTail = null;

            while (current != null){
                if(current.val < x){
                    if(left == null){
                        left = current;
                        leftTail = left;
                    }else{
                        leftTail.next = current;
                        leftTail = leftTail.next;
                    }
                }else{  // val >= x
                    if(right == null){
                        right = current;
                        rightTail = right;
                    }else{
                        rightTail.next = current;
                        rightTail = rightTail.next;
                    }
                }
                current = current.next;
            }
            if(leftTail != null ){
                leftTail.next = null;
            }
            if(rightTail != null){
                rightTail.next = null;
            }
            ListNode result = null;
            if(left != null){
                leftTail.next = right;
                result = left;
            }else if(right != null){
                result = right;
            }else{
                return head;
            }
            return result;
        }
    }
}

