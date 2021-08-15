package com.doublejony.hash;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.HashMap;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 완주하지 못한 선수
 * <p>
 * 문제 설명
 * 수많은 마라톤 선수들이 마라톤에 참여하였습니다. 단 한 명의 선수를 제외하고는 모든 선수가 마라톤을 완주하였습니다.
 * 마라톤에 참여한 선수들의 이름이 담긴 배열 participant와 완주한 선수들의 이름이 담긴 배열 completion이 주어질 때, 완주하지 못한 선수의 이름을 return 하도록 solution 함수를 작성해주세요.
 * <p>
 * 제한사항
 * 마라톤 경기에 참여한 선수의 수는 1명 이상 100,000명 이하입니다.
 * completion의 길이는 participant의 길이보다 1 작습니다.
 * 참가자의 이름은 1개 이상 20개 이하의 알파벳 소문자로 이루어져 있습니다.
 * 참가자 중에는 동명이인이 있을 수 있습니다.
 * <p>
 * 입출력 예
 * participant	completion	return
 * ["leo", "kiki", "eden"]	["eden", "kiki"]	"leo"
 * ["marina", "josipa", "nikola", "vinko", "filipa"]	["josipa", "filipa", "marina", "nikola"]	"vinko"
 * ["mislav", "stanko", "mislav", "ana"]	["stanko", "ana", "mislav"]	"mislav"
 * <p>
 * 입출력 예 설명
 * 예제 #1
 * "leo"는 참여자 명단에는 있지만, 완주자 명단에는 없기 때문에 완주하지 못했습니다.
 * 예제 #2
 * "vinko"는 참여자 명단에는 있지만, 완주자 명단에는 없기 때문에 완주하지 못했습니다.
 * 예제 #3
 * "mislav"는 참여자 명단에는 두 명이 있지만, 완주자 명단에는 한 명밖에 없기 때문에 한명은 완주하지 못했습니다.
 */
@RunWith(DataProviderRunner.class)
public class Hash1 {

    @DataProvider
    public static Object[][] dataProviderAdd() {
        // @formatter:off
        return new Object[][] {
                {
                        new String[] { "leo", "kiki", "eden" },
                        new String[] { "eden", "kiki" },
                        "leo"
                },
                {
                        new String[] { "marina", "josipa", "nikola", "vinko", "filipa" },
                        new String[] { "josipa", "filipa", "marina", "nikola" },
                        "vinko"
                },
                {
                        new String[] { "mislav", "stanko", "mislav", "ana" },
                        new String[] { "stanko", "ana", "mislav" },
                        "mislav"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("dataProviderAdd")
    public void loopApi(String[] participant, String[] completion, String expected) {

        Stopwatch timer = Stopwatch.createStarted();

        String answer = "";

        HashMap<String, Integer> hashMap = new HashMap<>();
        for (String player : participant) {
            hashMap.put(player, hashMap.getOrDefault(player, 0) + 1);
        }
        for (String player : completion) {
            hashMap.put(player, hashMap.get(player) - 1);
        }

        for (String key : hashMap.keySet()) {
            if (hashMap.get(key) != 0) {
                answer = key;
            }
        }

        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, answer, timer.stop());
    }

    @Test
    @UseDataProvider("dataProviderAdd")
    public void lambda(String[] participant, String[] completion, String expected) {

        Stopwatch timer = Stopwatch.createStarted();

        String answer;

        HashMap<String, Integer> hashMap = new HashMap<>();
        Arrays.stream(participant).forEach(player -> hashMap.put(player, hashMap.getOrDefault(player, 0) + 1));
        Arrays.stream(completion).forEach(player -> hashMap.put(player, hashMap.get(player) - 1));

        answer = hashMap.keySet().stream().filter(key -> hashMap.get(key) != 0).findFirst().orElse("");

        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, answer, timer.stop());
    }
}
