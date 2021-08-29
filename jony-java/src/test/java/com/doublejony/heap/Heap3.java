package com.doublejony.heap;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 이중 우선순위 큐
 * <p>
 * 이중 우선순위 큐는 다음 연산을 할 수 있는 자료구조를 말합니다.
 * <p>
 * 명령어	    수신 탑(높이)
 * I 숫자	큐에 주어진 숫자를 삽입합니다.
 * D 1	    큐에서 최댓값을 삭제합니다.
 * D -1	    큐에서 최솟값을 삭제합니다.
 * 이중 우선순위 큐가 할 연산 operations가 매개변수로 주어질 때, 모든 연산을 처리한 후 큐가 비어있으면 [0,0] 비어있지 않으면 [최댓값, 최솟값]을 return 하도록 solution 함수를 구현해주세요.
 * <p>
 * 제한사항
 * operations는 길이가 1 이상 1,000,000 이하인 문자열 배열입니다.
 * operations의 원소는 큐가 수행할 연산을 나타냅니다.
 * 원소는 “명령어 데이터” 형식으로 주어집니다.- 최댓값/최솟값을 삭제하는 연산에서 최댓값/최솟값이 둘 이상인 경우, 하나만 삭제합니다.
 * 빈 큐에 데이터를 삭제하라는 연산이 주어질 경우, 해당 연산은 무시합니다.
 * <p>
 * 입출력 예
 * operations	                return
 * ["I 16","D 1"]	            [0,0]
 * ["I 7","I 5","I -5","D -1"]  [7,5]
 * <p>
 * 입출력 예 설명
 * 16을 삽입 후 최댓값을 삭제합니다. 비어있으므로 [0,0]을 반환합니다.
 * 7,5,-5를 삽입 후 최솟값을 삭제합니다. 최대값 7, 최소값 5를 반환합니다.
 */
@RunWith(DataProviderRunner.class)
public class Heap3 {

    @DataProvider
    public static Object[][] dataProviderAdd() {
        // @formatter:off
        return new Object[][] {
                {
                        new String[] { "I 16", "D 1" },
                        new int[] { 0, 0 }
                },
                {
                        new String[] { "I 7", "I 5", "I -5", "D -1" },
                        new int[] { 7, 5 }
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("dataProviderAdd")
    public void useList(String[] operations, int[] expected) {

        Stopwatch timer = Stopwatch.createStarted();

        int[] answer = {};

        List<Integer> l = new ArrayList<>();

        for (String operation : operations) {
            String o = operation.split(" ")[0];
            int num = Integer.parseInt(operation.split(" ")[1]);
            if (o.equals("I")) {
                l.add(num);
            } else if (o.equals("D")) {
                if (num == 1) {
                    l.remove((Integer) l.stream().mapToInt(x -> x).max().orElse(0));
                } else if (num == -1) {
                    l.remove((Integer) l.stream().mapToInt(x -> x).min().orElse(0));
                }
            }
        }

        answer = new int[] { l.stream().mapToInt(x -> x).max().orElse(0), l.stream().mapToInt(x -> x).min().orElse(0) };

        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, answer, timer.stop());
    }
}
