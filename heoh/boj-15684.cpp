#include <cstdio>

int solve(bool map[31][12], int n, int h);
bool hackLadder(bool map[31][12], int n, int h, int additive, int added, int row, int col);
bool isNiceLadder(bool map[31][12], int n, int h);
int findGoalIndex(bool map[31][12], int n, int h, int startIndex);

bool map[31][12];   // 1 ~ 10번 세로선 + 계산을 편하게하는 0, 11번 세로선
int N, M, H;

int main() {
    scanf("%d %d %d", &N, &M, &H);
    for (int i = 0; i < M; i++) {
        int a, b;
        scanf("%d %d", &a, &b);
        
        map[a][b] = true;
    }

    printf("%d\n", solve(map, N, H));

    return 0;
}

int solve(bool map[31][12], int n, int h) {
    static const int MAX_ADDITIVE = 3;

    // 추가된 가로선이 0개일 때
    if (isNiceLadder(map, n, h)) {
        return 0;
    }

    for (int additive = 1; additive <= MAX_ADDITIVE; additive++) {
        // 모든 곳에 가로선을 놓아본다.
        for (int i = 1; i <= h; i++) {
            for (int j = 1; j < n; j++) {
                if (hackLadder(map, n, h, additive, 0, i, j) == true) {
                    return additive;
                }
            }
        }
    }

    return -1;
}

bool hackLadder(bool map[31][12], int n, int h, int additive, int added, int row, int col) {
    bool isAlreadyExists = map[row][col];
    if (isAlreadyExists) return false;

    bool hasNeighbor = map[row][col - 1] || map[row][col + 1];
    if (hasNeighbor) return false;

    bool success = false;   // 가로선을 놓고 다시 떼야하므로,
                            // 결과를 즉시 반환하지 않고, success에 저장한다.

    // 현재 위치에 가로선을 놓아본다.
    map[row][col] = true;
    added++;
    
    if (added == additive) {
        if (isNiceLadder(map, n, h)) {
            success = true;
        }
    }
    else {
        bool isFirst = true;
        // 모든 곳에 가로선을 놓아본다.
        for (int i = 1; i <= h; i++) {
            for (int j = 1; j < n; j++) {
                if (isFirst) {
                    i = row;
                    j = col + 1;
                    isFirst = false;
                }

                if (hackLadder(map, n, h, additive, added, i, j) == true) {
                    success = true;
                    break;
                }
            }

            if (success) break;
        }
    }

    map[row][col] = false;
    added--;

    return success;
}

bool isNiceLadder(bool map[31][12], int n, int h) {
    for (int startIndex = 1; startIndex <= n; startIndex++) {
        int goalIndex = findGoalIndex(map, n, h, startIndex);
        if (startIndex != goalIndex) {
            return false;
        }
    }
    return true;
}

int findGoalIndex(bool map[31][12], int n, int h, int startIndex) {
    int currentIndex = startIndex;
    for (int currentHeight = 1; currentHeight <= h; currentHeight++) {
        if (map[currentHeight][currentIndex]) {
            currentIndex += 1;
        }
        else if (map[currentHeight][currentIndex - 1]) {
            currentIndex -= 1;
        }
    }
    return currentIndex;
}
