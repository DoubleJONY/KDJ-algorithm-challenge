// https://www.acmicpc.net/problem/15684

#include <cstdio>

int COL, nBAR, ROW, lad[31][11]{}, minAdd = 4;
bool check() {
    for (int j = 1, j_ = 1; j <= COL; j++, j_ = j) {
        for (int i = 1; i <= ROW; i++)
            if (lad[i][j_])
                j_++;
            else if (lad[i][j_ - 1])
                j_--;
        if (j_ != j)
            return false;
    }
    return true;
}
void dfs(int nAdded, int nAdd) {
    if (minAdd < 4)
        return;
    if (nAdded == nAdd) {
        if (check())
            minAdd = nAdded;
        return;
    }
    for (int j = 1; j < COL; j++) {
        for (int i = 1; i <= ROW; i++) {
            if (lad[i][j] || lad[i][j - 1] || lad[i][j + 1])
                continue;
            lad[i][j] = 1;
            dfs(nAdded + 1, nAdd);
            lad[i][j] = 0;
            while (!lad[i][j - 1] && !lad[i][j + 1])
                i++;
        }
    }
}
int main() {
    scanf("%d %d %d", &COL, &nBAR, &ROW);
    for (int n = 0, i, j; n < nBAR; n++) {
        scanf("%d %d", &i, &j);
        lad[i][j] = 1;
    }
    for (int i = 0; i < 4; i++) {
        dfs(0, i);
        if (minAdd < 4)
            break;
    }
    printf("%d\n", minAdd < 4 ? minAdd : -1);
}
