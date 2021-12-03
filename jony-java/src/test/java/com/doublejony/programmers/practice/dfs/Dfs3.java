package com.doublejony.programmers.practice.dfs;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 단어 변환
 * 두 개의 단어 begin, target과 단어의 집합 words가 있습니다. 아래와 같은 규칙을 이용하여 begin에서 target으로 변환하는 가장 짧은 변환 과정을 찾으려고 합니다.
 *
 * 1. 한 번에 한 개의 알파벳만 바꿀 수 있습니다.
 * 2. words에 있는 단어로만 변환할 수 있습니다.
 * 예를 들어 begin이 "hit", target가 "cog", words가 ["hot","dot","dog","lot","log","cog"]라면 "hit" -> "hot" -> "dot" -> "dog" -> "cog"와 같이 4단계를 거쳐 변환할 수 있습니다.
 *
 * 두 개의 단어 begin, target과 단어의 집합 words가 매개변수로 주어질 때, 최소 몇 단계의 과정을 거쳐 begin을 target으로 변환할 수 있는지 return 하도록 solution 함수를 작성해주세요.
 *
 * 제한사항
 * 각 단어는 알파벳 소문자로만 이루어져 있습니다.
 * 각 단어의 길이는 3 이상 10 이하이며 모든 단어의 길이는 같습니다.
 * words에는 3개 이상 50개 이하의 단어가 있으며 중복되는 단어는 없습니다.
 * begin과 target은 같지 않습니다.
 * 변환할 수 없는 경우에는 0를 return 합니다.
 * 입출력 예
 * begin	target	words	return
 * "hit"	"cog"	["hot", "dot", "dog", "lot", "log", "cog"]	4
 * "hit"	"cog"	["hot", "dot", "dog", "lot", "log"]	0
 */
@RunWith(DataProviderRunner.class)
public class Dfs3 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        "hit",
                        "cog",
                        new String[] {"hot", "dot", "dog", "lot", "log", "cog"},
                        4
                },
                {
                        "hit",
                        "cog",
                        new String[] {"hot", "dot", "dog", "lot", "log"},
                        0
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String begin, String target, String[] words, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(begin, target, words), timer.stop());
    }

    /**
     * 테스트 1 〉	통과 (0.09ms, 73.2MB)
     * 테스트 2 〉	통과 (0.16ms, 82.7MB)
     * 테스트 3 〉	통과 (0.43ms, 70.8MB)
     * 테스트 4 〉	통과 (0.10ms, 72.5MB)
     * 테스트 5 〉	통과 (0.03ms, 85.3MB)
     */
    class Solution {
        String target;
        int minDepth;

        public int solution(String begin, String target, String[] words) {
            this.target = target;
            this.minDepth = 2147483647;

            if(!List.of(words).contains(target)) {
                return 0;
            }

            List<String> list = new ArrayList<>(Arrays.asList(words));

            findSubTree(0, begin, list);

            return this.minDepth;
        }

        private void findSubTree(int depth, String current, List<String> words) {
            if(current.equals(this.target)) {
                if(this.minDepth > depth) {
                    this.minDepth = depth;
                }
                return;
            }
            if(words.isEmpty()) {
                return;
            }

            for (String word : words) {
                int c = 0;
                for (int j = 0; j < current.length(); j++) {
                    if (!current.substring(j, j + 1).equals(word.substring(j, j + 1))) {
                        c++;
                    }
                    if (c > 1) {
                        break;
                    }
                }
                if(c == 1) {
                    List<String> list = new ArrayList<>(words);
                    list.remove(word);
                    findSubTree(depth+1, word, list);
                }
            }
        }
    }
}
