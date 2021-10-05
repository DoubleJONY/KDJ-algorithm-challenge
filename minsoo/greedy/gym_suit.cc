// https://programmers.co.kr/learn/courses/30/lessons/42862?language=cpp

#include <string>
#include <vector>

using namespace std;

int solution(int n, vector<int> lost, vector<int> reserve) {
    vector<int> status(n + 1, 0);
    for (const auto &i : lost)
        status[i]--;
    for (const auto &i : reserve)
        status[i]++;
    for (int i = 1; i <= n; i++) {
        if (status[i] == -1) {
            if (status[i - 1] == 1)
                status[i] = status[i - 1] = 0;
            else if (status[i + 1] == 1)
                status[i] = status[i + 1] = 0;
        }
    }
    int attend = 0;
    for (int i = 1; i <= n; i++)
        if (status[i] != -1)
            attend++;
    return attend;
}
