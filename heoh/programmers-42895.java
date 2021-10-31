import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Solution {
    final int MAX_COUNT = 8;

    int N;
    int number;
    Map<Integer, Set<Integer>> results;

    public int solution(int N, int number) {
        this.N = N;
        this.number = number;
        this.results = new HashMap<>();

        for (int i = 1; i <= MAX_COUNT; i++) {
            Set<Integer> cases = findAllCases(i);
            if (cases.contains(number)) {
                return i;
            }
        }
        return -1;
    }

    public Set<Integer> findAllCases(int cnt) {
        if (results.containsKey(cnt)) {
            return results.get(cnt);
        }

        Set<Integer> cases = new HashSet<>();
        cases.add(repeat(N, cnt));

        for (int i = 1; i < cnt; i++) {
            int j = cnt - i;
            Set<Integer> casesA = findAllCases(i);
            Set<Integer> casesB = findAllCases(j);
            for (int a : casesA) {
                for (int b : casesB) {
                    cases.add(a + b);
                    cases.add(a - b);
                    cases.add(a * b);
                    if (b != 0) cases.add(a / b);
                }
            }
        }

        results.put(cnt, cases);
        return cases;
    }

    private int repeat(int n, int cnt) {
        int number = 0;
        for (int i = 0; i < cnt; i++) {
            number *= 10;
            number += n;
        }
        return number;
    }
}
