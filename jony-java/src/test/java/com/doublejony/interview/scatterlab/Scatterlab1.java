package com.doublejony.interview.scatterlab;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 긴 기간 동안 여자 친구가 없는 성구는, 드디어 소개팅 제의를 받았다.
 * 성구는 오랜만에 들어온 소개팅이라, 가장 소개팅 하기 좋은 날에 만나고 싶고 가장 소개팅을 하면 안되는 날을 피하고 싶다.
 *
 * 성구가 생각한 가장 소개팅 하기 좋은 날 계산 방법은 아래와 같다.
 *
 * 온도 (20점 만점)
 * 20점에서 현재 온도와 22도의 차이의 절댓값을 뺀다
 * 예) 16도라면 20 - Math.abs(22-16) = 14
 * 날씨 (20점 만점)
 * 맑음/구름조금: 20점
 * 구름 많음: 17점
 * 흐림: 10점
 * 비: 5점
 * 눈 14점
 * 온도, 날씨 점수 합산해서 점수가 가장 높은 날을 선정한다
 * 동점이면 토요일, 금요일, 일요일, 수요일, 목요일, 화요일, 월요일 순으로 우선권
 * 아래와 같은 경우에는 '가장 소개팅을 하면 안되는 날'이다
 *
 * 앞에서 구한 점수가 가장 작은 날 단 아래의 3가지 조건 중 하나 이상을 만족해야 함, 없으면 제외한다
 * 날씨가 '흐림' 혹은 '비' 일 경우
 * 온도 30도 이상
 * 온도 0도 이하
 * 비추천하는 날이 없으면 -1을 반환(return)합니다.
 *
 * 성구가 성공적인 소개팅을 할 수 있도록, 아래 3가지의 정보가 담긴 일주일 단위의 2중배열을 받아 가장 소개팅 하기 좋은 날과 가장 소개팅을 하면 안되는 날을 배열로 반환(return)하는 solution함수를 작성하시오.
 *
 * 배열에 담긴 정보
 * #	정보 이름	값
 * 0	하늘상태코드	[맑음(1), 구름조금(2), 구름많음(3), 흐림(4)]
 * 1	강수상태코드	[없음(0), 비(1), 눈(2)]
 * 2	온도	섭씨 온도: number
 * 예
 * 입력 (월,화,수,목,금,토,일 순서)
 * [[1,0,11],[3,1,15],[2,0,16],[4,0,17],[2,0,15],[2,1,14],[2,0,12]]
 * [[4,0,12],[1,0,16],[3,0,18],[3,0,17],[2,0,15],[3,2,22],[2,1,17]]
 * 결과 (추천,비추천)
 * [2, 5] // (추천: 수요일, 비추천: 토요일)
 * [5, 0] // (추천: 토요일, 비추천: 월요일)
 * 주의사항
 * 복잡한 조건이 있는 서비스 로직을 얼마나 유지보수하기 쉽게 구현하는 지를 평가하는 문제입니다. 구조화하여 최대한 이해하기 쉽고 단순하게 구현해주세요.
 */
@RunWith(DataProviderRunner.class)
public class Scatterlab1 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new int[][] {{1,0,11},{3,1,15},{2,0,16},{4,0,17},{2,0,15},{2,1,14},{2,0,12}},
                        new int[] {2, 5}
                },
                {
                        new int[][] {{4,0,12},{1,0,16},{3,0,18},{3,0,17},{2,0,15},{3,2,22},{2,1,17}},
                        new int[] {5, 0}
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void useFullScanLoop(int[][] data, int[] expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(data), timer.stop());
    }

    //TODO: use Lombok
//    class ForecastScore implements Comparable<ForecastScore> {
    class ForecastScore {

        //우선순위요일
//        final int[] priorityDays = new int[] {6, 5, 0, 3, 4, 2, 1};

        int day;
        int weatherStatus;
        int forcastStatus;
        int temperature;
        int temperatureScore;
        int weatherScore;

        public ForecastScore(int day, int weatherStatus, int forcastStatus, int temperature, int temperatureScore,
                int weatherScore) {

            this.day = day;
            this.weatherStatus = weatherStatus;
            this.forcastStatus = forcastStatus;
            this.temperature = temperature;
            this.temperatureScore = temperatureScore;
            this.weatherScore = weatherScore;
        }

        public int getDay() {

            return day;
        }

        public int getWeatherStatus() {

            return weatherStatus;
        }

        public int getForcastStatus() {

            return forcastStatus;
        }

        public int getTemperature() {

            return temperature;
        }

        public int getTemperatureScore() {

            return temperatureScore;
        }

        public int getWeatherScore() {

            return weatherScore;
        }

        public int getTotalScore() {

            return temperatureScore + weatherScore;
        }

//        @Override
//        public int compareTo(ForecastScore o) {
//
//            if(this.getTotalScore() > o.getTotalScore()) {
//                return 1;
//            } else if(this.getTotalScore() == o.getTotalScore()) {
//                for (int day : priorityDays) {
//                    if(this.getDay() == day) {
//                        return 1;
//                    } else if(o.getDay() == day) {
//                        return -1;
//                    }
//                }
//                return 0;
//            } else {
//                return -1;
//            }
//        }
    }

    class Solution {

        //우선순위요일
        final int[] priorityDays = new int[] {6, 5, 0, 3, 4, 2, 1};

        public int[] solution(int[][] data) {
            List<ForecastScore> weekForecastScore = setWeekForecastScore(data);

            return new int[] {getBestDay(weekForecastScore), getWorstDay(weekForecastScore)};
        }

        private List<ForecastScore> setWeekForecastScore(int[][] data) {

            List<ForecastScore> weekList = new ArrayList<>();

            for (int i = 0; i < data.length; i++) {
                int[] day = data[i];
                int temperatureScore = getTemperatureScore(day);
                int weatherScore = getWeatherScore(day);

                //TODO: refactor to setter/builder
                weekList.add(new ForecastScore(i, day[0], day[1], day[2], temperatureScore, weatherScore));
            }

            return weekList;
        }

        private int getWeatherScore(int[] day) {
            //강수상태코드
            switch (day[1]){
                case 1:
                    //비
                    return 5;
                case 2:
                    //눈
                    return 14;
                case 0:
                    //없음
                    //하늘상태코드
                    switch (day[0]) {
                        case 4:
                            //흐림
                            return 10;
                        case 3:
                            //구름많음
                            return 17;
                        default:
                            //맑음, 구름조금
                            return 20;
                    }
            }
            return 0;
        }

        private int getTemperatureScore(int[] day) {
            return 20 - Math.abs(22-day[2]);
        }

        private int getBestDay(List<ForecastScore> weekForecastScore) {

            ForecastScore best = weekForecastScore.get(0);

            for (int i = 1; i < weekForecastScore.size(); i++) {
                ForecastScore current = weekForecastScore.get(i);


                if(current.getTotalScore() > best.getTotalScore()) {
                    best = current;
                } else if(current.getTotalScore() == best.getTotalScore()) {
                    for (int day : priorityDays) {
                        if(current.getDay() == day) {
                            best = current;
                        } else if(best.getDay() == day) {
                            break;
                        }
                    }
                }
            }

            return best.getDay();
        }

        private int getWorstDay(List<ForecastScore> weekForecastScore) {
            int worstDay = -1;
            int worstScore = 9999;

            for (int i = 0; i < weekForecastScore.size(); i++) {
                ForecastScore current = weekForecastScore.get(i);
                if(current.temperature >= 30
                || current.temperature <= 0
                || current.weatherStatus == 4
                || current.forcastStatus == 1
                ) {
                    if(worstScore > current.getTotalScore()) {
                        worstDay = current.getDay();
                        worstScore = current.getTotalScore();
                    }
                }
            }

            return worstDay;
        }
    }
}
