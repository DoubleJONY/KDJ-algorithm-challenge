// https://www.acmicpc.net/problem/14500

#include <iostream>

constexpr int di[4]{-1, 1, 0, 0}, dj[4]{0, 0, -1, 1};
int M, N, mp[500][500], maxInMap = 0, maxSum = 0, tmp;

void dfs(int i, int j, int sum = 0, int blocked = -1, int cnt = 0) {
    if (i < 0 || i >= M || j < 0 || j >= N)
        return;
    sum += mp[i][j];
    if (maxSum > (3 - cnt) * maxInMap + sum)
        return;
    if (cnt == 3) {
        if (sum > maxSum)
            maxSum = sum;
        return;
    }
    if (cnt == 1) {
        for (int d_ = 0; d_ < 3; d_++) {
            int i_ = i + di[d_], j_ = j + dj[d_];
            if (d_ != blocked && i_ >= 0 && i_ < M && j_ >= 0 && j_ < N) {
                for (int d__ = d_ + 1; d__ < 4; d__++) {
                    int i__ = i + di[d__], j__ = j + dj[d__];
                    if (d__ != blocked && i__ >= 0 && i__ < M && j__ >= 0 && j__ < N) {
                        tmp = mp[i_][j_] + mp[i__][j__];
                        if (sum + tmp > maxSum)
                            maxSum = sum + tmp;
                    }
                }
            }
        }
    }
    for (int d = 0; d < 4; d++)
        if (d != blocked)
            dfs(i + di[d], j + dj[d], sum, d ^ 1, cnt + 1);
}

int main() {
    std::ios::sync_with_stdio(0);
    std::cin.tie(0);
    std::cin >> M >> N;
    for (int i = 0; i < M; i++) {
        for (int j = 0; j < N; j++) {
            std::cin >> mp[i][j];
            if (mp[i][j] > maxInMap)
                maxInMap = mp[i][j];
        }
    }
    for (int i = 0; i < M; i++)
        for (int j = 0; j < N; j++)
            dfs(i, j);
    std::cout << maxSum << std::endl;
}
