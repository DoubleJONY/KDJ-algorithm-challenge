package com.doublejony.practice.stackNQueue;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 주식개발
 * <p>
 * 초 단위로 기록된 주식가격이 담긴 배열 prices가 매개변수로 주어질 때, 가격이 떨어지지 않은 기간은 몇 초인지를 return 하도록 solution 함수를 완성하세요.
 *
 * 제한사항
 * prices의 각 가격은 1 이상 10,000 이하인 자연수입니다.
 * prices의 길이는 2 이상 100,000 이하입니다.
 *
 * 입출력 예
 * prices	return
 * [1, 2, 3, 2, 3]	[4, 3, 1, 1, 0]
 *
 * 입출력 예 설명
 * 1초 시점의 ₩1은 끝까지 가격이 떨어지지 않았습니다.
 * 2초 시점의 ₩2은 끝까지 가격이 떨어지지 않았습니다.
 * 3초 시점의 ₩3은 1초뒤에 가격이 떨어집니다. 따라서 1초간 가격이 떨어지지 않은 것으로 봅니다.
 * 4초 시점의 ₩2은 1초간 가격이 떨어지지 않았습니다.
 * 5초 시점의 ₩3은 0초간 가격이 떨어지지 않았습니다.
 */
@RunWith(DataProviderRunner.class)
public class Stack4 {

    @DataProvider
    public static Object[][] dataProviderAdd() {
        // @formatter:off
        return new Object[][] {
                {
                        new int[] { 1, 2, 3, 2, 3 },
                        new int[] { 4, 3, 1, 1, 0 }
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("dataProviderAdd")
    public void useQueue(int[] prices, int[] expected) {

        Stopwatch timer = Stopwatch.createStarted();

        List<Integer> answer = new ArrayList<>();

        Queue<Integer> q = Arrays.stream(prices).boxed().collect(Collectors.toCollection(LinkedList::new));

        while (!q.isEmpty()) {
            int peek = q.poll();
            int dduckrakDay = 0;

            for (Integer a : q) {
                dduckrakDay++;
                if (peek > a) {
                    break;
                }
            }

            answer.add(dduckrakDay);
        }

        int[] answerArray = answer.stream().mapToInt(integer -> integer).toArray();

        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, answerArray, timer.stop());
    }
}
