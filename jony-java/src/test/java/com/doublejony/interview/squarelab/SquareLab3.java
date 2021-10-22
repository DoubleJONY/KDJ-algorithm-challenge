package com.doublejony.interview.squarelab;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * Have the function ArrayChallenge(arr) take the array of integers stored in arr, and determine if any two numbers (excluding the first element) in the array can sum up to the first element in the array. For example: if arr is [7, 3, 5, 2, -4, 8, 11], then there are actually two pairs that sum to the number 7: [5, 2] and [-4, 11]. Your program should return all pairs, with the numbers separated by a comma, in the order the first number appears in the array. Pairs should be separated by a space. So for the example above, your program would return: 5,2 -4,11
 *
 * If there are no two numbers that sum to the first element in the array, return -1
 * Examples
 * Input: new int[] {17, 4, 5, 6, 10, 11, 4, -3, -5, 3, 15, 2, 7}
 * Output: 6,11 10,7 15,2
 * Input: new int[] {7, 6, 4, 1, 7, -2, 3, 12}
 * Output: 6,1 4,3
 */
@RunWith(DataProviderRunner.class)
public class SquareLab3 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new int[] {17, 4, 5, 6, 10, 11, 4, -3, -5, 3, 15, 2, 7},
                        "6,11 10,7 15,2"
                },
                {
                        new int[] {7, 6, 4, 1, 7, -2, 3, 12},
                        "6,1 4,3"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(int[] arr, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(arr), timer.stop());
    }

    class Solution {
        public String solution(int[] arr) {
            List<Integer> pList = findMergePair(
                    arr[0],
                    Arrays.stream(arr).sorted().toArray()
            );

            if(pList.isEmpty()) {
                return "-1";
            }

            List<Integer> answerList = restoreIndex(arr, pList);
            String answer = buildString(answerList);

            return answer;
        }

        private List<Integer> findMergePair(int mergeValue, int[] array) {
            int leftIndex = 0;
            int rightIndex = array.length-1;

            List<Integer> list = new ArrayList<>();

            //정렬된 배열에서 양쪽 인덱스를 좁혀가며 조건에 맞는 값을 찾도록 한다.
            while(leftIndex < rightIndex) {
                if(array[leftIndex] + array[rightIndex] == mergeValue) {
                    list.add(array[leftIndex]);
                    list.add(array[rightIndex]);

                    leftIndex++;
                    rightIndex--;
                } else if(array[leftIndex] + array[rightIndex] > mergeValue){
                    rightIndex--;
                } else {
                    leftIndex++;
                }
            }

            return list;
        }

        private List<Integer> restoreIndex(int[] array, List<Integer> list) {

            List<Integer> origin = new ArrayList<>();
            for(int i = 0; i < array.length; i++) {
                if(list.contains(array[i])) {
                    origin.add(array[i]);
                    if(list.indexOf(array[i]) % 2 == 0) {
                        origin.add(list.indexOf(i+1));
                        list.remove(list.indexOf(i+1));
                    } else {
                        origin.add(list.indexOf(i-1));
                        list.remove(list.indexOf(i-1));
                    }
                    list.remove((Integer) array[i]);
                }
            }
            return origin;
        }

        private String buildString(List<Integer> list) {
            StringBuilder element = new StringBuilder();
            while(true) {
                element.append(list.get(0));
                list.remove(0);
                element.append(",");

                element.append(list.get(0));
                list.remove(0);
                if(!list.isEmpty()) {
                    element.append(" ");
                } else {
                    break;
                }
            }

            return element.toString();
        }
    }
}
