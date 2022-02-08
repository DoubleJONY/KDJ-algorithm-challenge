package com.doublejony.common;

import com.doublejony.leetcode.entities.ListNode;
import com.google.common.base.Stopwatch;
import org.junit.Assert;

public class AssertResolveCustom {

    public static void resolve(String testType, ListNode expected, ListNode answer, Stopwatch timer) {

        ListNode e = expected;
        ListNode a = answer;

        do {
            Assert.assertEquals(e.val, a.val);
            e = e.next;
            a = a.next;

        } while (e != null && a != null);

        System.out.println(testType
                + " || expected : " + expected
                + " || answer : " + answer
                + " || " + timer
        );
    }
}
