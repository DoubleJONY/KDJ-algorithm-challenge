package com.doublejony.practice.hash;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 전화번호 목록
 * <p>
 * 문제 설명
 * <p>
 * 전화번호부에 적힌 전화번호 중, 한 번호가 다른 번호의 접두어인 경우가 있는지 확인하려 합니다.
 * 전화번호가 다음과 같을 경우, 구조대 전화번호는 영석이의 전화번호의 접두사입니다.
 * 구조대 : 119
 * 박준영 : 97 674 223
 * 지영석 : 11 9552 4421
 * 전화번호부에 적힌 전화번호를 담은 배열 phone_book 이 solution 함수의 매개변수로 주어질 때, 어떤 번호가 다른 번호의 접두어인 경우가 있으면 false를 그렇지 않으면 true를 return 하도록 solution 함수를 작성해주세요.
 * <p>
 * 제한 사항
 * phone_book의 길이는 1 이상 1,000,000 이하입니다.
 * 각 전화번호의 길이는 1 이상 20 이하입니다.
 * 같은 전화번호가 중복해서 들어있지 않습니다.
 * <p>
 * 입출력 예제
 * phone_book	return
 * ["119", "97674223", "1195524421"]	false
 * ["123","456","789"]	true
 * ["12","123","1235","567","88"]	false
 * <p>
 * 입출력 예 설명
 * 입출력 예 #1
 * 앞에서 설명한 예와 같습니다.
 * 입출력 예 #2
 * 한 번호가 다른 번호의 접두사인 경우가 없으므로, 답은 true입니다.
 * 입출력 예 #3
 * 첫 번째 전화번호, “12”가 두 번째 전화번호 “123”의 접두사입니다. 따라서 답은 false입니다.
 */
@RunWith(DataProviderRunner.class)
public class Hash2 {

    @DataProvider
    public static Object[][] dataProviderAdd() {
        // @formatter:off
        return new Object[][] {
                {
                        new String[] { "119", "97674223", "1195524421" },
                        false
                },
                {
                        new String[] { "123", "456", "789" },
                        true
                },
                {
                        new String[] { "12", "123", "1235", "567", "88" },
                        false
                }
        };
        // @formatter:on
    }

    /**
     * phone_book 이 모두 숫자라면 정렬만 완료한다면 앞서 정렬된 값을 문자열로부터 탐색해서 찾는게 더 구현이 용이하다고 판단
     */
    @Test
    @UseDataProvider("dataProviderAdd")
    public void loopApi(String[] phone_book, boolean expected) {

        Stopwatch timer = Stopwatch.createStarted();

        boolean answer = true;

        Arrays.sort(phone_book);

        for (int i = 0; i < phone_book.length - 1; i++) {
            if (phone_book[i + 1].startsWith(phone_book[i])) {
                answer = false;
                break;
            }
        }

        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, answer, timer.stop());
    }

    @Test
    @UseDataProvider("dataProviderAdd")
    public void lambda(String[] phone_book, boolean expected) {

        Stopwatch timer = Stopwatch.createStarted();

        Arrays.sort(phone_book);

        boolean answer = IntStream.range(0, phone_book.length - 1)
                .noneMatch(i -> phone_book[i + 1].startsWith(phone_book[i]));

        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, answer, timer.stop());
    }

    @Test
    @UseDataProvider("dataProviderAdd")
    public void useHashMap(String[] phone_book, boolean expected) {

        Stopwatch timer = Stopwatch.createStarted();

        boolean answer = true;

        Map<String, Integer> map = IntStream.range(0, phone_book.length).boxed()
                .collect(Collectors.toMap(i -> phone_book[i], i -> i, (a, b) -> b));

        for (String s : phone_book) {
            int bound = s.length();
            for (int j = 0; j < bound; j++) {
                if (map.containsKey(s.substring(0, j))) {
                    answer = false;
                    break;
                }
            }
        }

        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, answer, timer.stop());
    }

}
