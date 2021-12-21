package com.doublejony.playground;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

import static com.doublejony.common.AssertResolve.resolve;


@RunWith(DataProviderRunner.class)
public class Median {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new int[] {1, 2, 3},
                        new int[] {4, 5},
                        3
                },
                {
                        new int[] {2, 3, 4, 5},
                        new int[] {7, 6},
                        4
                },
                {
                        new int[] {5, 3, 2, 1, 4, 6},
                        new int[] {7},
                        4
                },
                {
                        new int[] {5, 3, 2, 1, 4, 6, 7},
                        new int[] {9, 8},
                        5
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(int[] input, int[] input2, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Median.Main().solution(input, input2), timer.stop());
    }

    public class Main {

        public int solution(int[] input, int[] input2) {

            int[] array = new int[input.length+input2.length];

            System.arraycopy(input, 0, array, 0, input.length);
            System.arraycopy(input2, 0, array, input.length, input2.length);

            Arrays.sort(array);
            return array.length % 2 == 0
                    ? ((array[(array.length/2)-1] + array[array.length/2]) / 2)
                    : (array[(array.length-1)/2]);

        }
    }

    @Test
    @UseDataProvider("testCase")
    public void solution2(int[] input, int[] input2, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Median.Main2().solution(input, input2), timer.stop());
    }

    public class Main2 {

        public int solution(int[] input, int[] input2) {

            PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
            for (int a : input) {
                queue.add(a);
            }
            for (int b : input2) {
                queue.add(b);
            }

            int count;
            if (queue.size() % 2 == 0) {
                count = (queue.size()/2)-1;
                dropQueue(queue, count);
                return (queue.poll() + queue.poll()) / 2;
            } else {
                count = (queue.size()-1)/2;
                dropQueue(queue, count);
                return queue.poll();
            }
        }

        private void dropQueue(PriorityQueue<Integer> queue, int count) {
            for (int i = 0; i < count; i++) {
                queue.poll();
            }
        }
    }
}
