#include <cstring>
#include <iostream>

using namespace std;

constexpr int di[]{0, -1, 0, 1}, dj[]{1, 0, -1, 0};
int M, N, mp[8][8], nCCTV = 0, minArea = INT32_MAX;
pair<int, int> cctvs[8];

int area(int (&m)[8][8]) {
    int cnt = 0;
    for (int i = 0; i < M; i++)
        for (int j = 0; j < N; j++)
            if (!m[i][j])
                cnt++;
    return cnt;
}

void fillStraight(int (&m)[8][8], int i, int j, int d) {
    int curr_i = i + di[d], curr_j = j + dj[d];
    while (curr_i >= 0 && curr_i < M && curr_j >= 0 && curr_j < N && m[curr_i][curr_j] != 6) {
        m[curr_i][curr_j] = -1;
        curr_i += di[d], curr_j += dj[d];
    }
}

void fill(int (&m)[8][8], int idxCCTV, int d) {
    int i = cctvs[idxCCTV].first, j = cctvs[idxCCTV].second;
    switch (mp[i][j]) {
    case 1:
        fillStraight(m, i, j, d);
        return;
    case 2:
        fillStraight(m, i, j, d);
        fillStraight(m, i, j, d > 1 ? d - 2 : d + 2);
        return;
    case 3:
        fillStraight(m, i, j, d);
        fillStraight(m, i, j, d < 3 ? d + 1 : 0);
        return;
    case 4:
        fillStraight(m, i, j, d);
        fillStraight(m, i, j, d < 3 ? d + 1 : 0);
        fillStraight(m, i, j, d > 1 ? d - 2 : d + 2);
        return;
    case 5:
        for (int d_ = 0; d_ < 4; d_++)
            fillStraight(m, i, j, d_);
        return;
    }
}

void dfs(int (&m)[8][8], int idxCCTV = 0) {
    if (idxCCTV == nCCTV) {
        auto a = area(m);
        if (a < minArea)
            minArea = a;
        return;
    }
    int cpy[8][8];
    for (int d = 0; d < 4; d++) {
        memcpy(cpy, m, sizeof(m));
        fill(cpy, idxCCTV, d);
        dfs(cpy, idxCCTV + 1);
    }
}

int main() {
    scanf("%d %d", &M, &N);
    for (int i = 0; i < M; i++) {
        for (int j = 0; j < N; j++) {
            scanf("%d", &mp[i][j]);
            if (mp[i][j] && mp[i][j] != 6) {
                cctvs[nCCTV] = {i, j};
                nCCTV++;
            }
        }
    }
    dfs(mp);
    printf("%d\n", minArea);
}
