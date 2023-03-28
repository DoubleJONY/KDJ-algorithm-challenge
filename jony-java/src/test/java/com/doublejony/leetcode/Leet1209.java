package com.doublejony.leetcode;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Stack;

import static com.doublejony.common.AssertResolve.resolve;


@RunWith(DataProviderRunner.class)
public class Leet1209 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        "abcd",
                        2,
                        "abcd"
                },
                {
                        "deeedbbcccbdaa",
                        3,
                        "aa"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String s, int k, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Leet1209.Solution().removeDuplicates(s, k), timer.stop());
    }

    class Solution {
        public String removeDuplicates(String s, int k) {
            // create a stack to store pairs of characters and their counts
            Stack<Pair<Character, Integer>> stack = new Stack<>();

            // iterate through each character in the string
            for (char c : s.toCharArray()) {
                // if the stack is not empty and the top character is equal to the current character
                if (!stack.isEmpty() && stack.peek().getKey() == c) {
                    // increment the count of the top pair
                    stack.peek().setValue(stack.peek().getValue() + 1);

                    // if the count of the top pair is equal to k, remove it from the stack
                    if (stack.peek().getValue() == k) {
                        stack.pop();
                    }
                } else {
                    // push a new pair onto the stack containing the current character and a count of 1
                    stack.push(new Pair<>(c, 1));
                }
            }

            // create a string builder to build the final string
            StringBuilder sb = new StringBuilder();
            while(!stack.isEmpty()) {
                Pair<Character, Integer> p = stack.pop();
                for (int i = 0; i < p.getValue(); i++) {
                    sb.append(p.getKey());
                }
            }

            // iterate through each pair in the stack and append the
            return sb.reverse().toString();
        }

        private class Pair<T, T1> {
            Character key;
            Integer value;

            public Pair(Character key, Integer value) {
                this.key = key;
                this.value = value;
            }

            public Character getKey() {
                return key;
            }

            public Integer getValue() {
                return value;
            }

            public void setKey(Character key) {
                this.key = key;
            }

            public void setValue(Integer value) {
                this.value = value;
            }
        }
    }
}
