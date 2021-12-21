package com.doublejony.programmers.practice.hash;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 베스트앨범
 * <p>
 * 문제 설명
 * <p>
 * 스트리밍 사이트에서 장르 별로 가장 많이 재생된 노래를 두 개씩 모아 베스트 앨범을 출시하려 합니다. 노래는 고유 번호로 구분하며, 노래를 수록하는 기준은 다음과 같습니다.
 * 속한 노래가 많이 재생된 장르를 먼저 수록합니다.
 * 장르 내에서 많이 재생된 노래를 먼저 수록합니다.
 * 장르 내에서 재생 횟수가 같은 노래 중에서는 고유 번호가 낮은 노래를 먼저 수록합니다.
 * 노래의 장르를 나타내는 문자열 배열 genres와 노래별 재생 횟수를 나타내는 정수 배열 plays가 주어질 때, 베스트 앨범에 들어갈 노래의 고유 번호를 순서대로 return 하도록 solution 함수를 완성하세요.
 * <p>
 * 제한사항
 * genres[i]는 고유번호가 i인 노래의 장르입니다.
 * plays[i]는 고유번호가 i인 노래가 재생된 횟수입니다.
 * genres와 plays의 길이는 같으며, 이는 1 이상 10,000 이하입니다.
 * 장르 종류는 100개 미만입니다.
 * 장르에 속한 곡이 하나라면, 하나의 곡만 선택합니다.
 * 모든 장르는 재생된 횟수가 다릅니다.
 * <p>
 * 입출력 예
 * genres	plays	return
 * ["classic", "pop", "classic", "classic", "pop"]	[500, 600, 150, 800, 2500]	[4, 1, 3, 0]
 * <p>
 * 입출력 예 설명
 * classic 장르는 1,450회 재생되었으며, classic 노래는 다음과 같습니다.
 * 고유 번호 3: 800회 재생
 * 고유 번호 0: 500회 재생
 * 고유 번호 2: 150회 재생
 * pop 장르는 3,100회 재생되었으며, pop 노래는 다음과 같습니다.
 * 고유 번호 4: 2,500회 재생
 * 고유 번호 1: 600회 재생
 * 따라서 pop 장르의 [4, 1]번 노래를 먼저, classic 장르의 [3, 0]번 노래를 그다음에 수록합니다.
 */
@RunWith(DataProviderRunner.class)
public class Hash4 {

    @DataProvider
    public static Object[][] dataProviderAdd() {
        // @formatter:off
        return new Object[][] {
                {
                        new String[] { "classic", "pop", "classic", "classic", "pop" },
                        new int[] { 500, 600, 150, 800, 2500 },
                        new int[] { 4, 1, 3, 0 }
                }
        };
        // @formatter:on
    }

    /**
     * 장르 별 많은 곡의 역정렬하고 최대값을 캐싱하여 반환
     */
    @Test
    @UseDataProvider("dataProviderAdd")
    public void loopApi(String[] genres, int[] plays, int[] expected) {

        Stopwatch timer = Stopwatch.createStarted();

        HashMap<String, Integer> map = new HashMap<>();

        for (int i = 0; i < genres.length; i++) {
            map.put(genres[i], map.getOrDefault(genres[i], 0) + plays[i]);
        }

        List<String> keySetList = new ArrayList<>(map.keySet());

        keySetList.sort((o1, o2) -> (map.get(o2).compareTo(map.get(o1))));

        List<Integer> answerList = new ArrayList<>();

        for (String key : keySetList) {
            int maxIndex = 0;
            int maxValue = 0;
            int secondIndex = 0;
            int secondValue = 0;

            for (int i = 0; i < genres.length; i++) {
                if (genres[i].equals(key)) {
                    if (secondValue < plays[i]) {
                        secondIndex = i;
                        secondValue = plays[i];
                    }
                    if (maxValue < secondValue) {
                        int cacheIndex = maxIndex;
                        int cacheValue = maxValue;

                        maxIndex = secondIndex;
                        maxValue = secondValue;

                        secondIndex = cacheIndex;
                        secondValue = cacheValue;
                    }
                }
            }

            answerList.add(maxIndex);
            if (secondValue != 0) {
                answerList.add(secondIndex);
            }
        }

        int[] answer = answerList.stream().mapToInt(integer -> integer).toArray();

        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, answer, timer.stop());
    }

}
