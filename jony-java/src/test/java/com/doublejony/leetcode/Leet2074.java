package com.doublejony.leetcode;

import com.doublejony.leetcode.entities.ListNode;
import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import com.tngtech.junit.dataprovider.convert.StringConverter;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static com.doublejony.common.AssertResolveCustom.resolve;


@RunWith(DataProviderRunner.class)
public class Leet2074 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new ListNode(5,
                        new ListNode(2,
                        new ListNode(6,
                        new ListNode(3,
                        new ListNode(9,
                        new ListNode(1,
                        new ListNode(7,
                        new ListNode(3,
                        new ListNode(8,
                        new ListNode(4
                        ))))))))))
                        ,
                        new ListNode(5,
                        new ListNode(6,
                        new ListNode(2,
                        new ListNode(3,
                        new ListNode(9,
                        new ListNode(1,
                        new ListNode(4,
                        new ListNode(8,
                        new ListNode(3,
                        new ListNode(7
                        ))))))))))
                },
                {
                        new ListNode(1,
                        new ListNode(1,
                        new ListNode(0,
                        new ListNode(6
                        ))))
                        ,
                        new ListNode(1,
                        new ListNode(0,
                        new ListNode(1,
                        new ListNode(6
                        ))))
                },
                {
                        new ListNode(1,
                        new ListNode(1,
                        new ListNode(0,
                        new ListNode(6,
                        new ListNode(5
                        )))))
                        ,
                        new ListNode(1,
                        new ListNode(0,
                        new ListNode(1,
                        new ListNode(5,
                        new ListNode(6
                        )))))
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(ListNode head, ListNode expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Leet2074.Solution().reverseEvenLengthGroups(head), timer.stop());
    }

    /**
     * https://leetcode.com/submissions/detail/619470224/
     *
     * 165 / 165 test cases passed.
     * Status: Accepted
     * Runtime: 100 ms
     * Memory Usage: 317.7 MB
     */
    class Solution {
        public ListNode reverseEvenLengthGroups(ListNode head) {

            List<Integer> answerList = new ArrayList<>();

            int groupCount = 1;

            ListNode h = head;

            while (true) {
                List<Integer> cache = new ArrayList<>();

                h = getGroupNode(groupCount, h, cache);

                if (cache.size() % 2 == 0) {
                    addAllWithReverse(answerList, cache);
                } else {
                    answerList.addAll(cache);
                }

                if (isLast(h)) {
                    break;
                }

                groupCount++;
            }

            return getAnswerNode(answerList);
        }

        private boolean isLast(ListNode h) {
            return h == null;
        }

        private ListNode getGroupNode(int groupCount, ListNode h, List<Integer> cache) {
            for (int i = 0; i < groupCount; i++) {
                cache.add(h.val);
                h = h.next;
                if (isLast(h)) {
                    break;
                }
            }
            return h;
        }

        private void addAllWithReverse(List<Integer> answerList, List<Integer> cache) {
            for (int i = cache.size() - 1; i >= 0; i--) {
                answerList.add(cache.get(i));
            }
        }

        private ListNode getAnswerNode(List<Integer> answerList) {
            ListNode temp = new ListNode(answerList.get(answerList.size()-1));
            for (int i = answerList.size() - 2; i >= 0; i--) {
                temp = new ListNode(answerList.get(i), temp);
            }
            return temp;
        }
    }
}
