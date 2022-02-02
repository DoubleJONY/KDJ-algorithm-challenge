package com.rhino.leetcode.medium;

public class Medium00061_Rotate_list {

    public static void main(String[] args) {
        Solution solution = new Solution();

        ListNode head = new ListNode();
        head.val = 1;
        ListNode last = head;
        for (int i = 0; i < 4; i++) {
            ListNode next = new ListNode();
            next.val = i + 2;
            last.next = next;
            last = next;
        }
        solution.rotateRight(head, 2);
    }

}


class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

class Solution {

    public ListNode rotateRight(ListNode head, int k) {
        // move right n
        // basic solution = move end and remove connection and insert on first.
        // but, not effective.
        // remember count k node. and split once
        // if k > node; use k % node_count

        int total = 1;
        ListNode tail = head;
        ListNode newTail = head;
        while (tail.next != null) {
            total++;
            tail = tail.next;
        }
        // 5-2 = 3;
        int fromHead = k % total;
        if (fromHead == 0) {
            return head;
        }

        // 1,2,3  4,5  ->   4,5    1,2,3
        for (int i = 1; i < total-fromHead; i++) {
            newTail = newTail.next;
        }

        tail.next = head;
        head = newTail.next;
        newTail.next = null;

        return head;
    }
}