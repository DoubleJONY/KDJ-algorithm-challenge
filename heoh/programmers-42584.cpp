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

    static Stock stack[MAX_LEN + 1];
    Stock* top = stack;
    *(++top) = { -1, 0 };
    prices[nPrices - 1] = 0;

    for (int i = 0; i < nPrices; ++i) {
        int price = prices[i];
        while (top->price > price) {
            answer[top->index] = i - top->index;
            --top;
        }
        *(++top) = { i, price };
    }
    return answer;
}

/*
채점을 시작합니다.
정확성  테스트
테스트 1 〉	통과 (0.01ms, 3.76MB)
테스트 2 〉	통과 (0.03ms, 4.26MB)
테스트 3 〉	통과 (0.15ms, 4.29MB)
테스트 4 〉	통과 (0.17ms, 3.8MB)
테스트 5 〉	통과 (0.21ms, 3.8MB)
테스트 6 〉	통과 (0.02ms, 4.24MB)
테스트 7 〉	통과 (0.13ms, 4.18MB)
테스트 8 〉	통과 (0.12ms, 4.26MB)
테스트 9 〉	통과 (0.02ms, 4.25MB)
테스트 10 〉	통과 (0.20ms, 4.26MB)
효율성  테스트
테스트 1 〉	통과 (26.02ms, 24.2MB)
테스트 2 〉	통과 (18.94ms, 18.9MB)
테스트 3 〉	통과 (28.92ms, 26.8MB)
테스트 4 〉	통과 (21.87ms, 21.3MB)
테스트 5 〉	통과 (14.07ms, 16.1MB)
채점 결과
정확성: 66.7
효율성: 33.3
합계: 100.0 / 100.0
*/
