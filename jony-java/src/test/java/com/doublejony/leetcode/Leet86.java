package com.doublejony.leetcode;

import com.doublejony.leetcode.entities.ListNode;
import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static com.doublejony.common.AssertResolveCustom.resolve;

@RunWith(DataProviderRunner.class)
public class Leet86 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new ListNode(2,
                        new ListNode(1
                        )),
                        2,
                        new ListNode(1,
                        new ListNode(2
                        ))
                },
                {
                        new ListNode(1,
                        new ListNode(4,
                        new ListNode(3,
                        new ListNode(2,
                        new ListNode(5,
                        new ListNode(2
                        )))))),
                        3,
                        new ListNode(1,
                        new ListNode(2,
                        new ListNode(2,
                        new ListNode(4,
                        new ListNode(3,
                        new ListNode(5
                        ))))))
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(ListNode head, int x, ListNode expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Leet86.Solution().partition(head, x), timer.stop());
    }

    /**
     * https://leetcode.com/problems/partition-list
     *
     * 168 / 168 test cases passed.
     * Status: Accepted
     * Runtime: 0 ms
     * Memory Usage: 41.8 MB
     */
    class Solution {
        public ListNode partition(ListNode head, int x) {
            ListNode headList = new ListNode(0);
            ListNode tailList = new ListNode(0);

            ListNode origin = head;
            ListNode headTemp = headList;
            ListNode tailTemp = tailList;
            while (origin != null) {
                if(origin.val < x) {
                    headTemp.next = new ListNode(origin.val);
                    headTemp = headTemp.next;
                } else {
                    tailTemp.next = new ListNode(origin.val);
                    tailTemp = tailTemp.next;
                }
                origin = origin.next;
            }
            headTemp.next = tailList.next;
            return headList.next;
        }
    }
}
