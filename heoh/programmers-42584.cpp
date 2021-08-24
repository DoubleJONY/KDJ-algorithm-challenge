#include <vector>
#include <stack>

using namespace std;

const int MAX_LEN = 100000;

struct Stock {
    int index;
    int price;
};

vector<int> solution(vector<int> prices) {
    int nPrices = prices.size();
    vector<int> answer(nPrices);

    static Stock stack[MAX_LEN];
    int top = -1;
    stack[++top] = { -1, 0 };
    prices[nPrices - 1] = 0;

    for (int i = 0; i < nPrices; i++) {
        int price = prices[i];
        while (stack[top].price > price) {
            Stock& s = stack[top--];
            answer[s.index] = i - s.index;
        }
        stack[++top] = { i, price };
    }
    return answer;
}

/*
채점을 시작합니다.
정확성  테스트
테스트 1 〉	통과 (0.01ms, 3.61MB)
테스트 2 〉	통과 (0.03ms, 4.24MB)
테스트 3 〉	통과 (0.14ms, 3.81MB)
테스트 4 〉	통과 (0.19ms, 4.25MB)
테스트 5 〉	통과 (0.19ms, 4.3MB)
테스트 6 〉	통과 (0.01ms, 4.31MB)
테스트 7 〉	통과 (0.10ms, 3.72MB)
테스트 8 〉	통과 (0.13ms, 4.3MB)
테스트 9 〉	통과 (0.02ms, 3.73MB)
테스트 10 〉	통과 (0.20ms, 3.89MB)
효율성  테스트
테스트 1 〉	통과 (25.70ms, 24.2MB)
테스트 2 〉	통과 (17.52ms, 18.8MB)
테스트 3 〉	통과 (27.11ms, 26.8MB)
테스트 4 〉	통과 (21.77ms, 21.2MB)
테스트 5 〉	통과 (15.48ms, 16.1MB)
채점 결과
정확성: 66.7
효율성: 33.3
합계: 100.0 / 100.0
*/
