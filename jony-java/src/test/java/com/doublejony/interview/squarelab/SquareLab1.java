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
 * String Challenge
 * Have the function StringChallenge(sen) take the sen parameter being passed and return the longest word in the string. If there are two or more words that are the same length, return the first word from the string with that length. Ignore punctuation and assume sen will not be empty. Words may also contain numbers, for example "Hello world123 567"
 * Examples
 * Input: "fun&!! time"
 * Output: time
 * Input: "I love dogs"
 * Output: love
 */
@RunWith(DataProviderRunner.class)
public class SquareLab1 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        "fun&!! time",
                        "time"
                },
                {
                        "I love dogs",
                        "love"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String sen, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().stringChallenge(sen), timer.stop());
    }

    class Solution {
        public String stringChallenge(String sen) {
            // code goes here
            int answerIndex = -1;
            int maxLength = 0;

            String[] words = sen.split(" ");
            for(int i = 0; i < words.length; i++) {
                String word = words[i].replaceAll("[^a-zA-Z]", "");
                if(maxLength < word.length()) {
                    answerIndex = i;
                    maxLength = word.length();
                }
            }

            return answerIndex != -1 ? words[answerIndex] : "";
        }
    }
}
