#include <vector>
#include <stack>

using namespace std;

vector<int> solution(vector<int> prices) {
    vector<int> answer(prices.size());
    stack<pair<int, int>> stack;
    stack.push(make_pair(-1, 0));
    prices[prices.size() - 1] = 0;
    for (int i = 0; i < prices.size(); i++) {
        int price = prices[i];
        while (stack.top().second > price) {
            pair<int, int> s = stack.top();
            stack.pop();
            answer[s.first] = i - s.first;
        }
        stack.push(make_pair(i, price));
    }
    return answer;
}

/*
채점을 시작합니다.
정확성  테스트
테스트 1 〉	통과 (0.01ms, 4.33MB)
테스트 2 〉	통과 (0.03ms, 3.66MB)
테스트 3 〉	통과 (0.14ms, 4.3MB)
테스트 4 〉	통과 (0.25ms, 4.33MB)
테스트 5 〉	통과 (0.24ms, 4.34MB)
테스트 6 〉	통과 (0.02ms, 4.25MB)
테스트 7 〉	통과 (0.14ms, 4.17MB)
테스트 8 〉	통과 (0.13ms, 4.28MB)
테스트 9 〉	통과 (0.02ms, 4.27MB)
테스트 10 〉	통과 (0.20ms, 4.23MB)
효율성  테스트
테스트 1 〉	통과 (26.10ms, 24.3MB)
테스트 2 〉	통과 (18.74ms, 18.9MB)
테스트 3 〉	통과 (27.08ms, 26.8MB)
테스트 4 〉	통과 (22.16ms, 21.3MB)
테스트 5 〉	통과 (15.38ms, 16.2MB)
채점 결과
정확성: 66.7
효율성: 33.3
합계: 100.0 / 100.0
*/
