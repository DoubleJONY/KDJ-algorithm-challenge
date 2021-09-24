package com.doublejony.bruteForce;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 소수 찾기
 * <p>
 * 한자리 숫자가 적힌 종이 조각이 흩어져있습니다. 흩어진 종이 조각을 붙여 소수를 몇 개 만들 수 있는지 알아내려 합니다.
 *
 * 각 종이 조각에 적힌 숫자가 적힌 문자열 numbers가 주어졌을 때, 종이 조각으로 만들 수 있는 소수가 몇 개인지 return 하도록 solution 함수를 완성해주세요.
 *
 * 제한사항
 * numbers는 길이 1 이상 7 이하인 문자열입니다.
 * numbers는 0~9까지 숫자만으로 이루어져 있습니다.
 * "013"은 0, 1, 3 숫자가 적힌 종이 조각이 흩어져있다는 의미입니다.
 * 입출력 예
 * numbers	return
 * "17"	3
 * "011"	2
 * 입출력 예 설명
 * 예제 #1
 * [1, 7]으로는 소수 [7, 17, 71]를 만들 수 있습니다.
 *
 * 예제 #2
 * [0, 1, 1]으로는 소수 [11, 101]를 만들 수 있습니다.
 *
 * 11과 011은 같은 숫자로 취급합니다.
 */
@RunWith(DataProviderRunner.class)
public class BruteForce2 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        "17",
                        3
                },
                {
                        "011",
                        2
                }
        };
        // @formatter:on
    }

    /*
    테스트 1 〉	통과 (2.27ms, 74.4MB)
    테스트 2 〉	통과 (9.30ms, 59.5MB)
    테스트 3 〉	통과 (1.42ms, 60.3MB)
    테스트 4 〉	통과 (8.89ms, 70.5MB)
    테스트 5 〉	통과 (29.41ms, 89.1MB)
    테스트 6 〉	통과 (1.33ms, 68.6MB)
    테스트 7 〉	통과 (2.77ms, 73.2MB)
    테스트 8 〉	통과 (30.48ms, 88.1MB)
    테스트 9 〉	통과 (1.58ms, 61.9MB)
    테스트 10 〉	통과 (15.23ms, 71.3MB)
    테스트 11 〉	통과 (4.27ms, 73.1MB)
    테스트 12 〉	통과 (4.64ms, 72.4MB)
     */
    @Test
    @UseDataProvider("testCase")
    public void useRecurrence(String numbers, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(numbers), timer.stop());
    }

    class Solution {
        public int solution(String numbers) {
            final List<String> parts = new ArrayList<>();
            for (int i1 = 0; i1 < numbers.length(); i1++) {
                parts.add(numbers.substring(i1, i1 + 1));
            }
            List<String> cards = new ArrayList<>();
            for (int i = 0; i < numbers.length(); i++) {
                cards.addAll(getAllCards(parts, i));
            }

            List<Integer> intCards = new ArrayList<>();
            for (String i : cards) {
                int j = Integer.parseInt(i);
                if (!intCards.contains(j) && isPrime(j)) {
                    intCards.add(j);
                }
            }

            return intCards.size();
        }

        public boolean isPrime(long num) {

            if(num < 2) {
                return false;
            }

            for (int i = 2; (long) i * i <= num; i++) {
                if(num % i == 0) {
                    return false;
                }
            }
            return true;
        }

        private List<String> getAllCards(List<String> numbers, int i) {

            if(i == 0){
                return numbers;
            }

            List<String> ans = new ArrayList<>();
            i--;
            for (String number : numbers) {
                List<String> a = new ArrayList<>();
                List<String> b = new ArrayList<>(numbers);
                b.remove(number);
                a.addAll(getAllCards(b, i));

                for (String num : a) {
                    String s = number + num;
                    ans.add(s);
                }
            }

            return ans;
        }
    }

    /*
    테스트 1 〉	통과 (8.84ms, 59.6MB)
    테스트 2 〉	통과 (20.86ms, 79.8MB)
    테스트 3 〉	통과 (6.87ms, 59.5MB)
    테스트 4 〉	통과 (19.47ms, 62.2MB)
    테스트 5 〉	통과 (98.28ms, 92.2MB)
    테스트 6 〉	통과 (14.25ms, 74.8MB)
    테스트 7 〉	통과 (5.48ms, 58.3MB)
    테스트 8 〉	통과 (65.81ms, 72.9MB)
    테스트 9 〉	통과 (6.89ms, 63.3MB)
    테스트 10 〉	통과 (63.59ms, 59.9MB)
    테스트 11 〉	통과 (25.46ms, 61.1MB)
    테스트 12 〉	통과 (30.02ms, 77.5MB)
     */
    @Test
    @UseDataProvider("testCase")
    public void useRecurrenceWithLambda(String numbers, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution2().solution(numbers), timer.stop());
    }

    class Solution2 {
        public int solution(String numbers) {
            final List<String> parts = IntStream.range(0, numbers.length()).mapToObj(i1 -> numbers.substring(i1, i1 + 1)).collect(Collectors.toList());
            List<String> cards = IntStream.range(0, numbers.length()).mapToObj(i -> getAllCards(parts, i)).flatMap(Collection::stream).collect(Collectors.toList());

            List<Integer> intCards = new ArrayList<>();
            cards.stream().mapToInt(Integer::parseInt).filter(j -> !intCards.contains(j) && isPrime(j)).forEach(intCards::add);

            return intCards.size();
        }

        public boolean isPrime(long num) {

            if(num < 2) {
                return false;
            }

            for (int i = 2; (long) i * i <= num; i++) {
                if(num % i == 0) {
                    return false;
                }
            }
            return true;
        }

        private List<String> getAllCards(List<String> numbers, int i) {

            if(i == 0){
                return numbers;
            }

            List<String> ans = new ArrayList<>();
            i--;
            for (String number : numbers) {
                List<String> a = new ArrayList<>();
                List<String> b = new ArrayList<>(numbers);
                b.remove(number);
                a.addAll(getAllCards(b, i));

                a.stream().map(num -> number + num).forEach(ans::add);
            }

            return ans;
        }
    }
}
