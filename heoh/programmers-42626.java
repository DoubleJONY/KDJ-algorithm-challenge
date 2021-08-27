import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

class Solution {
    public int solution(int[] scoville, int K) {
        List<Integer> scovilles = listOf(scoville);
        return simulateMergingFoods(scovilles, K);
    }

    private int simulateMergingFoods(List<Integer> scovilles, int threshold) {
        int count = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>(scovilles);
        while ((pq.size() >= 2) & (pq.peek() < threshold)) {
            int scovilleA = pq.poll();
            int scovilleB = pq.poll();
            int newScoville = scovilleA + (scovilleB * 2);
            pq.add(newScoville);
            count++;
        }

        if (pq.peek() < threshold)
            return -1;

        return count;
    }

    private List<Integer> listOf(int[] scoville) {
        return Arrays.stream(scoville).boxed().collect(Collectors.toList());
    }
}
