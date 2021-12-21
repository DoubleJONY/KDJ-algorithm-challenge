package com.doublejony.programmers.practice.sort;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 가장 큰 수
 * <p>
 * 0 또는 양의 정수가 주어졌을 때, 정수를 이어 붙여 만들 수 있는 가장 큰 수를 알아내 주세요.
 * <p>
 * 예를 들어, 주어진 정수가 [6, 10, 2]라면 [6102, 6210, 1062, 1026, 2610, 2106]를 만들 수 있고, 이중 가장 큰 수는 6210입니다.
 * <p>
 * 0 또는 양의 정수가 담긴 배열 numbers가 매개변수로 주어질 때, 순서를 재배치하여 만들 수 있는 가장 큰 수를 문자열로 바꾸어 return 하도록 solution 함수를 작성해주세요.
 * <p>
 * 제한 사항
 * numbers의 길이는 1 이상 100,000 이하입니다.
 * numbers의 원소는 0 이상 1,000 이하입니다.
 * 정답이 너무 클 수 있으니 문자열로 바꾸어 return 합니다.
 * 입출력 예
 * numbers	            return
 * [6, 10, 2]	        "6210"
 * [3, 30, 34, 5, 9]	"9534330"
 */
@RunWith(DataProviderRunner.class)
public class Sort2 {

    @DataProvider
    public static Object[][] dataProviderAdd() {
        // @formatter:off
        return new Object[][]{
                {
                        new int[]{6, 10, 2},
                        "6210"
                },
                {
                        new int[]{3, 30, 34, 5, 9},
                        "9534330"
                },
                {
                        new int[]{6, 10, 6, 10},
                        "661010"
                },
                {
                        new int[]{6, 66, 6, 666, 66, 6, 66, 6667, 6660, 66680, 5, 7, 67},
                        "76766680666766666666666666605"
                },
                {
                        new int[]{3, 30, 300, 34, 33, 5, 9},
                        "953433330300"
                },
                {
                        new int[]{3, 3, 30, 300, 34, 33, 310, 5, 9, 90, 0, 34},
                        "990534343333310303000"
                },
                {
                        new int[]{0, 0},
                        "0"
                },
                {
                        new int[]{10, 101},
                        "10110"
                },
                {
                        new int[]{1, 11, 111, 1111},
                        "1111111111"
                },
                {
                        new int[]{10, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                        "987654321101000"
                },
                {
                        new int[]{90, 908, 89, 898, 10, 101, 1, 8, 9},
                        "990908898988110110"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("dataProviderAdd")
    public void useRecurrence(int[] numbers, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(numbers), timer.stop());
    }

    class Comp implements Comparable<Comp> {

        int n;
        int number;

        public Comp(int n, int number) {

            this.n = n;
            this.number = number;
        }

        public int getNumber() {

            return number;
        }

        @Override
        public int compareTo(Comp o) {

            if (this.number == o.number) {
                return 1;
            }

            int f = 0;
            int a;
            try {
                a = Integer.parseInt(String.valueOf(this.number).substring(this.n, this.n + 1));
            } catch (StringIndexOutOfBoundsException e) {
                a = Integer.parseInt(String.valueOf(this.number).substring(0, 1));
                f++;
            }

            int b;
            try {
                b = Integer.parseInt(String.valueOf(o.number).substring(this.n, this.n + 1));
            } catch (StringIndexOutOfBoundsException e) {
                b = Integer.parseInt(String.valueOf(o.number).substring(0, 1));
                f++;
            }

            if (a < b) {
                return 1;
            } else if (a > b) {
                return -1;
            } else {
                if (f == 2) {
                    if (String.valueOf(this.number).length() > String.valueOf(o.number).length()) {
                        return -1;
                    } else if (String.valueOf(this.number).length() < String.valueOf(o.number).length()) {
                        return 1;
                    } else {
                        return this.number > o.number ? 1 : -1;
                    }
                } else {
                    return new Comp(this.n + 1, this.number).compareTo(o);
                }
            }
        }
    }

    class Solution {
        public String solution(int[] numbers) {

            PriorityQueue<Comp> pq = Arrays.stream(numbers).mapToObj(number -> new Comp(0, number)).collect(Collectors.toCollection(PriorityQueue::new));
            return String.valueOf(pq.peek() != null ? pq.peek().number : 0).charAt(0) == '0' ? "0" : IntStream.range(0, pq.size()).mapToObj(i -> String.valueOf(pq.poll().getNumber())).collect(Collectors.joining());
        }
    }

    /*
    테스트 1 〉	통과 (712.66ms, 352MB)
    테스트 2 〉	통과 (251.36ms, 195MB)
    테스트 3 〉	통과 (767.04ms, 393MB)
    테스트 4 〉	통과 (73.39ms, 75.6MB)
    테스트 5 〉	통과 (512.86ms, 285MB)
    테스트 6 〉	통과 (322.34ms, 260MB)
    테스트 7 〉	통과 (5.74ms, 59.5MB)
    테스트 8 〉	통과 (6.55ms, 71MB)
    테스트 9 〉	통과 (7.85ms, 74.8MB)
    테스트 10 〉	통과 (10.25ms, 56.5MB)
    테스트 11 〉	통과 (3.56ms, 68MB)
     */
    @Test
    @UseDataProvider("dataProviderAdd")
    public void useStringCompare(int[] numbers, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution2().solution(numbers), timer.stop());
    }

    class Comp2 implements Comparable<Comp2> {

        int number;

        public Comp2(int number) {

            this.number = number;
        }

        public int getNumber() {

            return number;
        }

        @Override
        public int compareTo(Comp2 o) {

            int a = Integer.parseInt(String.valueOf(this.number) + o.number);
            int b = Integer.parseInt(String.valueOf(o.number) + this.number);
            return a <= b ? 1 : -1;
        }
    }

    class Solution2 {
        public String solution(int[] numbers) {

            PriorityQueue<Comp2> pq = Arrays.stream(numbers).mapToObj(number -> new Comp2(number)).collect(Collectors.toCollection(PriorityQueue::new));
            return String.valueOf(pq.peek() != null ? pq.peek().number : 0).charAt(0) == '0' ? "0" : IntStream.range(0, pq.size()).mapToObj(i -> String.valueOf(pq.poll().getNumber())).collect(Collectors.joining());
        }
    }
}
