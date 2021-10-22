package com.doublejony.interview.squarelab;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.HashMap;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * Have the function StringChallenge(str) take the str parameter being passed and return the first word with the greatest number of repeated letters. For example: "Today, is the greatest day ever!" should return greatest because it has 2 e's (and 2 t's) and it comes before ever which also has 2 e's. If there are no words with repeating letters return -1. Words will be separated by spaces.
 * Examples
 * Input: "Hello apple pie"
 * Output: Hello
 * Input: "No words"
 * Output: -1
 */
@RunWith(DataProviderRunner.class)
public class SquareLab2 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        "Hello apple pie",
                        "Hello"
                },
                {
                        "No words",
                        "-1"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String str, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new SquareLab2.Solution().stringChallenge(str), timer.stop());
    }

    class Solution {
        public String stringChallenge(String str) {
            int answerIndex = -1;
            int maxLength = 1;

            String[] words = str.split(" ");
            for(int i = 0; i < words.length; i++) {
                HashMap<String, Integer> dic = new HashMap<>();
                String word = words[i];
                for(int j = 0; j < word.length(); j++) {
                    String c = word.substring(j, j+1).toLowerCase();
                    if(dic.containsKey(c)) {
                        dic.put(c, dic.get(c)+1);
                    } else {
                        dic.put(c, 1);
                    }
                }

                int maxValue = Collections.max(dic.values());

                if (maxLength < maxValue){
                    answerIndex = i;
                    maxLength = maxValue;
                }
            }

            return answerIndex != -1 ? words[answerIndex] : "-1";
        }
    }
}
