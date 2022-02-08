package com.doublejony.leetcode;

import com.doublejony.leetcode.entities.ListNode;
import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

import static com.doublejony.common.AssertResolveCustom.resolve;

@RunWith(DataProviderRunner.class)
public class Leet61 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new ListNode(1,
                        new ListNode(2,
                        new ListNode(3,
                        new ListNode(4,
                        new ListNode(5
                        ))))),
                        2,
                        new ListNode(4,
                        new ListNode(5,
                        new ListNode(1,
                        new ListNode(2,
                        new ListNode(3
                        )))))
                },{
                        new ListNode(0,
                        new ListNode(1,
                        new ListNode(2
                        ))),
                        4,
                        new ListNode(2,
                        new ListNode(0,
                        new ListNode(1
                        )))
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(ListNode head, int k, ListNode expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Leet61.Solution().rotateRight(head, k), timer.stop());
    }

    class Solution {
        public ListNode rotateRight(ListNode head, int k) {

            int size = getSize(head, k);
            int realK = size != 0 ? k % size : k;
            return swap(head, size, realK);
        }

        private ListNode swap(ListNode origin, int size, int realK) {
            List<Integer> head = new ArrayList<>();
            List<Integer> tail = new ArrayList<>();

            ListNode temp = origin;
            for (int i = 0; i < size - realK; i++) {
                try {
                    tail.add(temp.val);
                    temp = temp.next;
                } catch (NullPointerException ignored) {
                    break;
                }
            }
            for (int i = 0; i < realK; i++) {
                try {
                    head.add(temp.val);
                    temp = temp.next;
                } catch (NullPointerException ignored) {
                    break;
                }
            }

            ListNode temp2 = new ListNode();
            ListNode swapList = temp2;
            if (head.size() > 0) {
                for (int i = 0; i < realK; i++) {
                    temp2.next = new ListNode(head.get(i));
                    temp2 = temp2.next;
                }
            }
            if (tail.size() > 0) {
                for (int i = 0; i < size - realK; i++) {
                    temp2.next = new ListNode(tail.get(i));
                    temp2 = temp2.next;
                }
            }

            return swapList.next;
        }

        private int getSize(ListNode head, int k) {

            ListNode a = head;
            int i = 0;
            while (true) {
                try {
                    a = a.next;
                    i++;
                } catch (NullPointerException ignored){
                    break;
                }
            }

            return i;
        }
    }
}
