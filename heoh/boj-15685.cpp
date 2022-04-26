#include <cstdio>
#include <vector>

using namespace std;

class DragonCurveData {
public:
    int nEdges;
    int width;
    int height;
};

void initialize();
int initializeCurveData();
void initializeDirections(int maxGeneration);
void initializeMap();
void drawDragonCurve(int startX, int startY, int startDir, int maxGeneration);
int countSquares();
bool isSquare(int y, int x);


const int TURN_LEFT  = 1;
const int TURN_RIGHT = -1;

const int DIR_RIGHT = 0;
const int DIR_UP    = 1;
const int DIR_LEFT  = 2;
const int DIR_DOWN  = 3;

const int OFFSET_X[] = {  1,  0, -1,  0 };
const int OFFSET_Y[] = {  0, -1,  0,  1 };

DragonCurveData curveData[100];
int* turns;

bool map[101][101];


int main() {
    initialize();

    int N;
    scanf("%d", &N);

    for (int i = 0; i < N; i++) {
        int x, y, d, g;
        scanf("%d %d %d %d", &x, &y, &d, &g);
        drawDragonCurve(x, y, d, g);
    }

    int nSquares = countSquares();
    printf("%d\n", nSquares);

    return 0;
}

void initialize() {
    int maxGeneration = initializeCurveData();
    initializeDirections(maxGeneration);
    initializeMap();
}

int initializeCurveData() {
    int maxGeneration = 0;

    DragonCurveData& firstData = curveData[0];
    firstData.nEdges = 0;
    firstData.width  = 0;
    firstData.height = 1;

    for (int i = 1; i < 100; i++) {
        DragonCurveData& prevData = curveData[i - 1];
        DragonCurveData& currData = curveData[i];

        bool isOddIndex = (i & 0x1);
        if (isOddIndex) {
            currData.nEdges = prevData.nEdges * 2 + 1;
            currData.width = prevData.width + prevData.height;
            currData.height = prevData.height;
        }
        else {
            currData.nEdges = prevData.nEdges * 2 + 1;
            currData.width = prevData.width;
            currData.height = prevData.width + prevData.height;
        }

        maxGeneration++;

        if ((currData.width >= 100) || (currData.height >= 100)) {
            break;
        }
    }

    return maxGeneration - 1;
}

void initializeDirections(int maxGeneration) {
    int length = (1 << maxGeneration) - 1;
    turns = new int[length];

    int i = 0;
    for (int gen = 1; gen <= maxGeneration; gen++) {
        int genLength = i;
        turns[i++] = TURN_LEFT;

        for (int j = genLength - 1; j >= 0; j--) {
            turns[i++] = turns[j] * -1;
        }
    }

}

void initializeMap() {
    for (int i = 0; i <= 100; i++) {
        for (int j = 0; j <= 100; j++) {
            map[i][j] = false;
        }
    }
}

void drawDragonCurve(int startX, int startY, int startDir, int maxGeneration) {
    int x = startX;
    int y = startY;
    int dir = startDir;
    int nEdges = curveData[maxGeneration].nEdges;

    map[y][x] = true;
    x += OFFSET_X[dir];
    y += OFFSET_Y[dir];
    map[y][x] = true;

    for (int i = 0; i < nEdges; i++) {
        dir = ((dir + turns[i]) + 4) % 4;
        x += OFFSET_X[dir];
        y += OFFSET_Y[dir];
        map[y][x] = true;
    }
}

int countSquares() {
    int nSquares = 0;
    for (int i = 0; i <= 99; i++) {
        for (int j = 0; j <= 99; j++) {
            if (isSquare(i, j)) {
                nSquares++;
            }
        }
    }
    return nSquares;
}

bool isSquare(int y, int x) {
    if (map[y][x] == false) return false;
    if (map[y][x+1] == false) return false;
    if (map[y+1][x] == false) return false;
    if (map[y+1][x+1] == false) return false;
    return true;
}
