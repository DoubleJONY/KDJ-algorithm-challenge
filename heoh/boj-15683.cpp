#include <cstdio>
#include <climits>
#include <algorithm>

using namespace std;

const int DIR_UP    = 1 << 0;
const int DIR_RIGHT = 1 << 1;
const int DIR_DOWN  = 1 << 2;
const int DIR_LEFT  = 1 << 3;

const int CELL_BLOCK = -1;

const int TYPE_DIR[] = {
    0,
    DIR_RIGHT,
    DIR_LEFT | DIR_RIGHT,
    DIR_UP | DIR_RIGHT,
    DIR_LEFT | DIR_UP | DIR_RIGHT,
    DIR_LEFT | DIR_UP | DIR_RIGHT | DIR_DOWN
};

const int TURN_COUNT[] = { 0, 4, 2, 4, 4, 1 };

typedef struct {
    int height;
    int width;
    int cells[8][8];
} Map;

typedef struct {
    int y;
    int x;
    int type;
    int dir;
} Cctv;

typedef struct {
    Cctv elements[8];
    int  size;
} CctvList;

int countMinInvisibleCells(Map& originalMap);
void initCctvList(Map& originalMap, CctvList& cctvList);
void initMap(Map& originalMap, Map& map);
int testCctvs(Map& map, CctvList& cctvList, int cctvIndex);
int countEmptyCells(Map& map);
void cctvTurnOn(Map& map, Cctv& cctv);
void cctvTurnOff(Map& map, Cctv& cctv);
void cctvSet(Map& map, Cctv& cctv, int additive);
void cctvTurnRight(Cctv& cctv);

int main() {
    static Map originalMap;

    scanf("%d %d", &originalMap.height, &originalMap.width);
    for (int i = 0; i < originalMap.height; i++) {
        for (int j = 0; j < originalMap.width; j++) {
            scanf("%d", &originalMap.cells[i][j]);
        }
    }

    int minCount = countMinInvisibleCells(originalMap);

    printf("%d\n", minCount);

    return 0;
}

int countMinInvisibleCells(Map& originalMap) {
    static Map map;
    static CctvList cctvList;

    initCctvList(originalMap, cctvList);
    initMap(originalMap, map);

    int minCount = testCctvs(map, cctvList, 0);
    return minCount;
}

void initCctvList(Map& originalMap, CctvList& cctvList) {
    cctvList.size = 0;
    for (int i = 0; i < originalMap.height; i++) {
        for (int j = 0; j < originalMap.width; j++) {
            int type = originalMap.cells[i][j];
            if (type == 0) continue;
            if (type == 6) continue;

            Cctv& cctv = cctvList.elements[cctvList.size];
            cctv.y = i;
            cctv.x = j;
            cctv.type = type;
            cctv.dir = TYPE_DIR[type];

            cctvList.size++;
        }
    }
}

void initMap(Map& originalMap, Map& map) {
    map.height = originalMap.height;
    map.width = originalMap.width;
    for (int i = 0; i < originalMap.height; i++) {
        for (int j = 0; j < originalMap.width; j++) {
            int type = originalMap.cells[i][j];
            if (type == 6) {
                map.cells[i][j] = CELL_BLOCK;
            }
            else {
                map.cells[i][j] = 0;
            }
        }
    }
}

int testCctvs(Map& map, CctvList& cctvList, int cctvIndex) {
    if (cctvIndex == cctvList.size) {
        return countEmptyCells(map);
    }
    
    int minCount = INT_MAX;
    Cctv& cctv = cctvList.elements[cctvIndex];

    int turnCount = TURN_COUNT[cctv.type];
    for (int i = 0; i < turnCount; i++) {
        cctvTurnOn(map, cctv);
        int count = testCctvs(map, cctvList, cctvIndex + 1);
        minCount = min(minCount, count);
        cctvTurnOff(map, cctv);
        cctvTurnRight(cctv);
    }

    return minCount;
}

int countEmptyCells(Map& map) {
    int count = 0;
    for (int i = 0; i < map.height; i++) {
        for (int j = 0; j < map.width; j++) {
            int cell = map.cells[i][j];
            if (cell == 0) {
                count++;
            }
        }
    }
    return count;
}

void cctvTurnOn(Map& map, Cctv& cctv) {
    cctvSet(map, cctv, 1);
}

void cctvTurnOff(Map& map, Cctv& cctv) {
    cctvSet(map, cctv, -1);
}

void cctvSet(Map& map, Cctv& cctv, int additive) {
    int y, x;

    // center
    y = cctv.y;
    x = cctv.x;
    map.cells[y][x] += additive;

    // up
    if (cctv.dir & DIR_UP) {
        y = cctv.y;
        x = cctv.x;
        for (y -= 1; y >= 0; y--) {
            if (map.cells[y][x] == CELL_BLOCK) break;
            map.cells[y][x] += additive;
        }
    }

    // right
    if (cctv.dir & DIR_RIGHT) {
        y = cctv.y;
        x = cctv.x;
        for (x += 1; x < map.width; x++) {
            if (map.cells[y][x] == CELL_BLOCK) break;
            map.cells[y][x] += additive;
        }
    }

    // down
    if (cctv.dir & DIR_DOWN) {
        y = cctv.y;
        x = cctv.x;
        for (y += 1; y < map.height; y++) {
            if (map.cells[y][x] == CELL_BLOCK) break;
            map.cells[y][x] += additive;
        }
    }

    // left
    if (cctv.dir & DIR_LEFT) {
        y = cctv.y;
        x = cctv.x;
        for (x -= 1; x >= 0; x--) {
            if (map.cells[y][x] == CELL_BLOCK) break;
            map.cells[y][x] += additive;
        }
    }
}

void cctvTurnRight(Cctv& cctv) {
    int dir = cctv.dir;
    cctv.dir = ((dir << 1) & 0xf) | ((dir >> 3) & 0x1);
}
