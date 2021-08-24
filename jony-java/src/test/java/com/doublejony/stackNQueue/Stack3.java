package com.doublejony.stackNQueue;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 다리를 지나는 트럭
 * <p>
 * 트럭 여러 대가 강을 가로지르는 일차선 다리를 정해진 순으로 건너려 합니다. 모든 트럭이 다리를 건너려면 최소 몇 초가 걸리는지 알아내야 합니다. 다리에는 트럭이 최대 bridge_length대 올라갈 수 있으며, 다리는 weight 이하까지의 무게를 견딜 수 있습니다. 단, 다리에 완전히 오르지 않은 트럭의 무게는 무시합니다.
 * 예를 들어, 트럭 2대가 올라갈 수 있고 무게를 10kg까지 견디는 다리가 있습니다. 무게가 [7, 4, 5, 6]kg인 트럭이 순서대로 최단 시간 안에 다리를 건너려면 다음과 같이 건너야 합니다.
 *
 * 경과 시간	다리를 지난 트럭	다리를 건너는 트럭	대기 트럭
 * 0	    []	            []	            [7,4,5,6]
 * 1~2	    []	            [7]	            [4,5,6]
 * 3	    [7]	            [4]	            [5,6]
 * 4	    [7]	            [4,5]	        [6]
 * 5	    [7,4]	        [5]	            [6]
 * 6~7	    [7,4,5]	        [6]	            []
 * 8	    [7,4,5,6]	    []	            []
 *
 * 따라서, 모든 트럭이 다리를 지나려면 최소 8초가 걸립니다.
 * solution 함수의 매개변수로 다리에 올라갈 수 있는 트럭 수 bridge_length, 다리가 견딜 수 있는 무게 weight, 트럭 별 무게 truck_weights가 주어집니다. 이때 모든 트럭이 다리를 건너려면 최소 몇 초가 걸리는지 return 하도록 solution 함수를 완성하세요.
 *
 * 제한 조건
 * bridge_length는 1 이상 10,000 이하입니다.
 * weight는 1 이상 10,000 이하입니다.
 * truck_weights의 길이는 1 이상 10,000 이하입니다.
 * 모든 트럭의 무게는 1 이상 weight 이하입니다.
 *
 * 입출력 예
 * bridge_length	weight	truck_weights	return
 * 2	10	[7,4,5,6]	8
 * 100	100	[10]	101
 * 100	100	[10,10,10,10,10,10,10,10,10,10]	110
 */
@RunWith(DataProviderRunner.class)
public class Stack3 {

    @DataProvider
    public static Object[][] dataProviderAdd() {
        // @formatter:off
        return new Object[][] {
                {
                        2,
                        10,
                        new int[] { 7, 4, 5, 6 },
                        8
                },
                {
                        100,
                        100,
                        new int[] { 10 },
                        101
                },
                {
                        100,
                        100,
                        new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 },
                        110
                },
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("dataProviderAdd")
    public void useQueue(int bridge_length, int weight, int[] truck_weights, int expected) {

        Stopwatch timer = Stopwatch.createStarted();

        Queue<Integer> q = Arrays.stream(truck_weights).boxed().collect(Collectors.toCollection(LinkedList::new));

        int answer = 0;
        List<Integer> truckWeights = new ArrayList<>();
        List<Integer> truckLocations = new ArrayList<>();
        int totalWeight = 0;

        while (true) {
            answer++;

            for (int i = 0; i < truckLocations.size(); i++) {
                truckLocations.set(i, truckLocations.get(i) + 1);
                if (truckLocations.get(i) > bridge_length) {
                    totalWeight -= truckWeights.get(i);
                    truckLocations.remove(i);
                    truckWeights.remove(i);
                    i--;
                }
            }

            if (q.isEmpty() && totalWeight == 0) {
                break;
            } else if (!q.isEmpty() && totalWeight + q.peek() <= weight) {
                totalWeight += q.peek();
                truckWeights.add(q.peek());
                truckLocations.add(1);
                q.poll();
            }
        }

        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, answer, timer.stop());
    }
}
