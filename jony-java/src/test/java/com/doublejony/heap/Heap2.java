package com.doublejony.heap;

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
 * 디스크 컨트롤러
 * <p>
 * 하드디스크는 한 번에 하나의 작업만 수행할 수 있습니다. 디스크 컨트롤러를 구현하는 방법은 여러 가지가 있습니다. 가장 일반적인 방법은 요청이 들어온 순서대로 처리하는 것입니다.
 * <p>
 * 예를들어
 * <p>
 * - 0ms 시점에 3ms가 소요되는 A작업 요청
 * - 1ms 시점에 9ms가 소요되는 B작업 요청
 * - 2ms 시점에 6ms가 소요되는 C작업 요청
 * 와 같은 요청이 들어왔습니다.
 * <p>
 * 한 번에 하나의 요청만을 수행할 수 있기 때문에 각각의 작업을 요청받은 순서대로 처리하면 다음과 같이 처리 됩니다.
 * <p>
 * - A: 3ms 시점에 작업 완료 (요청에서 종료까지 : 3ms)
 * - B: 1ms부터 대기하다가, 3ms 시점에 작업을 시작해서 12ms 시점에 작업 완료(요청에서 종료까지 : 11ms)
 * - C: 2ms부터 대기하다가, 12ms 시점에 작업을 시작해서 18ms 시점에 작업 완료(요청에서 종료까지 : 16ms)
 * 이 때 각 작업의 요청부터 종료까지 걸린 시간의 평균은 10ms(= (3 + 11 + 16) / 3)가 됩니다.
 * <p>
 * 하지만 A → C → B 순서대로 처리하면
 * <p>
 * - A: 3ms 시점에 작업 완료(요청에서 종료까지 : 3ms)
 * - C: 2ms부터 대기하다가, 3ms 시점에 작업을 시작해서 9ms 시점에 작업 완료(요청에서 종료까지 : 7ms)
 * - B: 1ms부터 대기하다가, 9ms 시점에 작업을 시작해서 18ms 시점에 작업 완료(요청에서 종료까지 : 17ms)
 * 이렇게 A → C → B의 순서로 처리하면 각 작업의 요청부터 종료까지 걸린 시간의 평균은 9ms(= (3 + 7 + 17) / 3)가 됩니다.
 * <p>
 * 각 작업에 대해 [작업이 요청되는 시점, 작업의 소요시간]을 담은 2차원 배열 jobs가 매개변수로 주어질 때, 작업의 요청부터 종료까지 걸린 시간의 평균을 가장 줄이는 방법으로 처리하면 평균이 얼마가 되는지 return 하도록 solution 함수를 작성해주세요. (단, 소수점 이하의 수는 버립니다)
 * <p>
 * 제한 사항
 * jobs의 길이는 1 이상 500 이하입니다.
 * jobs의 각 행은 하나의 작업에 대한 [작업이 요청되는 시점, 작업의 소요시간] 입니다.
 * 각 작업에 대해 작업이 요청되는 시간은 0 이상 1,000 이하입니다.
 * 각 작업에 대해 작업의 소요시간은 1 이상 1,000 이하입니다.
 * 하드디스크가 작업을 수행하고 있지 않을 때에는 먼저 요청이 들어온 작업부터 처리합니다.
 * 입출력 예
 * jobs	return
 * [[0, 3], [1, 9], [2, 6]]	9
 * 입출력 예 설명
 * 문제에 주어진 예와 같습니다.
 * <p>
 * 0ms 시점에 3ms 걸리는 작업 요청이 들어옵니다.
 * 1ms 시점에 9ms 걸리는 작업 요청이 들어옵니다.
 * 2ms 시점에 6ms 걸리는 작업 요청이 들어옵니다.
 */
@RunWith(DataProviderRunner.class)
public class Heap2 {

    @DataProvider
    public static Object[][] dataProviderAdd() {
        // @formatter:off
        return new Object[][] {
                {
                        new int[][] { { 0, 3 }, { 1, 9 }, { 2, 6 } },
                        9
                }
        };
        // @formatter:on
    }

    public static class Jobs implements Comparable<Jobs> {

        int index;
        int progressTime;

        public Jobs(int index, int progressTime) {

            this.index = index;
            this.progressTime = progressTime;
        }

        public int getIndex() {

            return index;
        }

        public int getProgressTime() {

            return progressTime;
        }

        @Override
        public int compareTo(Jobs o) {

            return this.progressTime <= o.progressTime ? 1 : -1;
        }
    }

    private List<Jobs> sortByProgressTime(List<Jobs> l) {

        //꼬리에 들어온 값을 역순으로 스왑 정렬
        for (int i = l.size() - 1; i > 0; i--) {
            if (l.get(i).progressTime < l.get(i - 1).progressTime) {
                Jobs j1 = l.get(i);
                Jobs j2 = l.get(i - 1);
                l.set(i, j2);
                l.set(i - 1, j1);
            } else {
                break;
            }
        }

        return l;
    }

    @Test
    @UseDataProvider("dataProviderAdd")
    public void useArray(int[][] jobs, int expected) {

        Stopwatch timer = Stopwatch.createStarted();

        int answer = 0;

        //1차 : List를 빨리 걸리는 시간 순으로 정렬
        List<Jobs> l = new ArrayList<>();
        for (int[] job : jobs) {
            l.add(new Jobs(job[0], job[1]));
            l = sortByProgressTime(l);
        }

        //2차 : 시뮬 돌리면서 List에서 요청 시점에 부합하는 가장 앞에 있는 순으로 pop
        //pop 할 때 경과 시간을 작업 처리 시간 만큼 바로 스킵
        int time = 0;
        while (true) {
            if (l.size() == 0) {
                break;
            }
            for (int i = 0; i < l.size(); i++) {
                if (l.get(i).index <= time) {
                    answer += (time - l.get(i).index) + l.get(i).progressTime;
                    time += l.get(i).progressTime - 1;
                    l.remove(i);
                    break;
                }
            }

            time++;
        }

        answer /= jobs.length;

        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, answer, timer.stop());
    }

}
