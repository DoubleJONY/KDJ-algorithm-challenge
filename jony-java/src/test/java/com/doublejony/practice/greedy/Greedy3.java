package com.doublejony.practice.greedy;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Stack;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 큰 수 만들기
 * <p>
 * 어떤 숫자에서 k개의 수를 제거했을 때 얻을 수 있는 가장 큰 숫자를 구하려 합니다.
 *
 * 예를 들어, 숫자 1924에서 수 두 개를 제거하면 [19, 12, 14, 92, 94, 24] 를 만들 수 있습니다. 이 중 가장 큰 숫자는 94 입니다.
 *
 * 문자열 형식으로 숫자 number와 제거할 수의 개수 k가 solution 함수의 매개변수로 주어집니다. number에서 k 개의 수를 제거했을 때 만들 수 있는 수 중 가장 큰 숫자를 문자열 형태로 return 하도록 solution 함수를 완성하세요.
 *
 * 제한 조건
 * number는 1자리 이상, 1,000,000자리 이하인 숫자입니다.
 * k는 1 이상 number의 자릿수 미만인 자연수입니다.
 * 입출력 예
 * number	k	return
 * "1924"	2	"94"
 * "1231234"	3	"3234"
 * "4177252841"	4	"775841"
 */
@RunWith(DataProviderRunner.class)
public class Greedy3 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        "1924",
                        2,
                        "94"
                },
                {
                        "1231234",
                        3,
                        "3234"
                },
                {
                        "4177252841",
                        4,
                        "775841"
                },
                {
                        "417755555",
                        4,
                        "77555"
                },
                {
                        "0",
                        1,
                        "0"
                }
        };
        // @formatter:on
    }

    /*
    테스트 1 〉	통과 (1.41ms, 74.6MB)
    테스트 2 〉	통과 (1.70ms, 79.3MB)
    테스트 3 〉	통과 (2.77ms, 76MB)
    테스트 4 〉	통과 (3.91ms, 65.1MB)
    테스트 5 〉	통과 (7.49ms, 74.1MB)
    테스트 6 〉	통과 (480.08ms, 374MB)
    테스트 7 〉	통과 (822.05ms, 380MB)
    테스트 8 〉	통과 (7148.25ms, 374MB)
    테스트 9 〉	통과 (186.67ms, 323MB)
    테스트 10 〉	실패 (시간 초과)
    테스트 11 〉	통과 (1.77ms, 75.5MB)
    테스트 12 〉	통과 (0.06ms, 80.3MB)
     */
    @Test
    @UseDataProvider("testCase")
    @Ignore
    public void useFullScanLoop(String number, int k, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(number, k), timer.stop());
    }

    class Solution {
        public String solution(String number, int k) {
            for (int i = 0; i < k; i++) {
                boolean exit = false;
                for (int j = 0; j < number.length()-1; j++) {
                    int a = Integer.parseInt(number.substring(j, j+1));
                    int b = Integer.parseInt(number.substring(j+1, j+2));
                    if(a < b) {
                        number = number.substring(0, j) + number.substring(j+1);
                        break;
                    }
                    //다 돌았는데 없으면 남은 만큼 뒤에서 다 자르자
                    if(j == number.length()-2) {
                        number = number.substring(0, number.length() - (k - i));
                        exit = true;
                        break;
                    }
                }
                if(exit){
                    break;
                }
            }

            return number;
        }
    }

    /*
    테스트 1 〉	통과 (0.24ms, 77.2MB)
    테스트 2 〉	통과 (0.36ms, 72.6MB)
    테스트 3 〉	통과 (0.60ms, 72.3MB)
    테스트 4 〉	통과 (0.70ms, 75.8MB)
    테스트 5 〉	통과 (2.37ms, 77.7MB)
    테스트 6 〉	통과 (87.10ms, 132MB)
    테스트 7 〉	통과 (248.45ms, 379MB)
    테스트 8 〉	통과 (850.25ms, 378MB)
    테스트 9 〉	통과 (88.36ms, 180MB)
    테스트 10 〉	실패 (시간 초과)
    테스트 11 〉	통과 (0.20ms, 74.5MB)
    테스트 12 〉	통과 (0.34ms, 79MB)
     */
    @Test
    @UseDataProvider("testCase")
    @Ignore
    public void useStack(String number, int k, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution2().solution(number, k), timer.stop());
    }

    class Solution2 {
        public String solution(String number, int k) {
            Stack<Integer> s = new Stack<>();

            while (k > 0) {
                if(number.equals("")) {
                    for (int i = 0; i < k; i++) {
                        s.pop();
                    }
                    break;
                }
                int i = Integer.parseInt(number.substring(0,1));
                number = number.substring(1);
                if(!s.isEmpty() && s.peek() < i) {
                    for (int j = s.size()-1; j >= 0; j--) {
                        if(s.get(j) < i) {
                            s.pop();
                            k--;
                        } else {
                            break;
                        }
                        if(k <= 0) {
                            break;
                        }
                    }
                }
                s.add(i);
            }
            if(s.isEmpty()) {
                return "0";
            }

            StringBuilder answer = new StringBuilder();
            for (Integer integer : s) {
                answer.append(integer.toString());
            }
            answer.append(number);

            return answer.toString();
        }
    }
}
