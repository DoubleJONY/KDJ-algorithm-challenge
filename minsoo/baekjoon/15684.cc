// https://www.acmicpc.net/problem/15684

#include <cstdio>

int ROW, COL, NUM_CONN, minAdd = 4;
int ladder[311]{};

bool isColIdentity(int cIn) {
    int cOut = cIn;
    for (int r = 1; r <= ROW; r++) {
        if (ladder[10 * r + cOut])
            cOut++;
        else if (ladder[10 * r + cOut - 1])
            cOut--;
    }
    return cOut == cIn;
}

bool isIdentity() {
    for (int cIn = 1; cIn <= COL; cIn++)
        if (!isColIdentity(cIn))
            return false;
    return true;
}

void dfs(int begin, int end, int nAdded, int nAdd) {
    if (minAdd < 4)
        return;
    if (nAdded == nAdd) {
        if (isIdentity())
            minAdd = nAdded;
        return;
    }
    if (begin == end)
        return;

    for (int i = begin; i < end; i++) {
        if (ladder[i - 1] || ladder[i] || ladder[i + 1])
            continue;

        ladder[i] = 1;
        dfs(i + 1, end, nAdded + 1, nAdd);
        ladder[i] = 0;
    }
}

int main() {
    scanf("%d %d %d", &COL, &NUM_CONN, &ROW);
    for (int n = 0, r, c; n < NUM_CONN; n++) {
        scanf("%d %d", &r, &c);
        ladder[10 * r + c] = 1;
    }
    for (int nAdd = 0; nAdd < 4; nAdd++) {
        dfs(11, 10 * ROW + COL, 0, nAdd);
        if (minAdd < 4)
            break;
    }
    printf("%d\n", minAdd < 4 ? minAdd : -1);
}
