package com.doublejony.common;

import com.doublejony.leetcode.Leet2074;
import com.google.common.base.Stopwatch;
import org.junit.Assert;

public class AssertResolveCustom {

    public static void resolve(String testType, Leet2074.ListNode expected, Leet2074.ListNode answer, Stopwatch timer) {

        Leet2074.ListNode e = expected;
        Leet2074.ListNode a = answer;

        do {
            Assert.assertEquals(e.getVal(), a.getVal());
            e = e.getNext();
            a = a.getNext();

        } while (e != null && a != null);

        System.out.println(testType
                + " || expected : " + expected
                + " || answer : " + answer
                + " || " + timer
        );
    }
}
