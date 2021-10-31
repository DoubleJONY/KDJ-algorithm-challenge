class Solution {
    public int solution(int[] money) {
        int n = money.length;
        int[] cache = new int[n];

        // bestWithoutFirst: 첫 번째 집을 무조건 안 고르는 경우,
        //                   마지막 집 선택 가능 함
        int bestWithoutFirst;
        cache[0] = 0;
        cache[1] = money[1];
        for (int i = 2; i < n; i++) {
            int caseA = money[i] + cache[i - 2];
            int caseB = cache[i - 1];
            cache[i] = Math.max(caseA, caseB);
        }
        bestWithoutFirst = cache[n - 1];

        // bestWithoutLast: 마지막 집을 무조건 안 고르는 경우,
        //                  첫 번째 집 선택 가능 함
        int bestWithoutLast;
        cache[0] = money[0];
        cache[1] = Math.max(money[0], money[1]);
        for (int i = 2; i < n - 1; i++) {
            int caseA = money[i] + cache[i - 2];
            int caseB = cache[i - 1];
            cache[i] = Math.max(caseA, caseB);
        }
        cache[n - 1] = cache[n - 2];
        bestWithoutLast = cache[n - 1];

        // 두 경우 중 최선을 선택
        return Math.max(bestWithoutFirst, bestWithoutLast);
    }
}
