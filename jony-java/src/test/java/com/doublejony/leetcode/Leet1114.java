package com.doublejony.leetcode;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

import static com.doublejony.common.AssertResolve.resolve;

@RunWith(DataProviderRunner.class)
public class Leet1114 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new int[]{1, 2, 3},
                        "firstsecondthird"
                },
                {
                        new int[]{1, 3, 2},
                        "firstthirdsecond"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(int[] input, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, start(input), timer.stop());
    }

    class Foo {

        Semaphore first;
        Semaphore second;
        Semaphore third;
        public Foo() {
            first = new Semaphore(1);
            second = new Semaphore(0);
            third = new Semaphore(0);
        }

        public void first(Runnable printFirst) throws InterruptedException {
            first.acquire();
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            second.release();
        }

        public void second(Runnable printSecond) throws InterruptedException {
            second.acquire();
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            third.release();
        }

        public void third(Runnable printThird) throws InterruptedException {
            third.acquire();
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
            first.release();
        }

    }

    String buffer = "";

    private class PrintFirst implements Runnable {

        @Override
        public void run() {
            buffer += "first";
        }
    }

    private class PrintSecond implements Runnable {

        @Override
        public void run() {
            buffer += "second";
        }
    }

    private class PrintThird implements Runnable {

        @Override
        public void run() {
            buffer += "third";
        }
    }

    public String start(int[] input) {

        StringBuilder output = new StringBuilder();

        List<Integer> inputList = Arrays.stream(input).boxed().collect(Collectors.toList());
        Collections.shuffle(inputList);

        Foo foo = new Foo();

        try {
            for (Integer i : inputList) {
                switch (i) {
                    case 1:
                        foo.first(new PrintFirst());
                        break;
                    case 2:
                        foo.second(new PrintSecond());
                        break;
                    case 3:
                        foo.third(new PrintThird());
                        break;
                }
//                output.append(buffer);
            }
            output.append(buffer);
            buffer = "";
        } catch (Exception ignored) {

        }

        return output.toString();
    }

}
