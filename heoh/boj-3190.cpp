#include <iostream>

using namespace std;

const int MAX_N = 100;

const int EMPTY = 0;
const int BODY_LEFT = 1;
const int BODY_UP = 2;
const int BODY_RIGHT = 3;
const int BODY_DOWN = 4;
const int APPLE = 5;
const int WALL = 6;

const int DY[] = {0, 0, -1, 0, 1};
const int DX[] = {0, -1, 0, 1, 0};

int N;
int K;
int L;
int map[MAX_N+2][MAX_N+2] = {0,};

struct Point {
    int row;
    int col;
};

Point movePoint(const Point& p, int dir) {
    return {p.row + DY[dir], p.col + DX[dir]};
}

class Snake {
public:
    Point head;
    Point tail;

    bool move_straight() {
        int headDir = map[head.row][head.col];
        int tailDir = map[tail.row][tail.col];
        Point nextHead = movePoint(head, headDir);
        Point nextTail = movePoint(tail, tailDir);

        if (map[nextHead.row][nextHead.col] == EMPTY) {
            head = nextHead;
            map[head.row][head.col] = headDir;
            map[tail.row][tail.col] = EMPTY;
            tail = nextTail;
            return true;
        }
        else if (map[nextHead.row][nextHead.col] == APPLE) {
            head = nextHead;
            map[head.row][head.col] = headDir;
            return true;
        }
        else {
            return false;
        }
    }

    void turn_left() {
        map[head.row][head.col] = (((map[head.row][head.col] - 1) - 1 + 4) % 4) + 1;
    }

    void turn_right() {
        map[head.row][head.col] = (((map[head.row][head.col] - 1) + 1 + 4) % 4) + 1;
    }
};

Snake snake;

int solution() {
    int t = 0;
    int remainingCommands = L;

    char nextCommand = '\0';
    int nextCommandTime = 1;

    while (true) {
        t++;

        if (!snake.move_straight()) {
            break;
        }

        if (t == nextCommandTime) {
            if (nextCommand == 'L') {
                snake.turn_left();
            }
            else if (nextCommand == 'D') {
                snake.turn_right();
            } 
            
            if (remainingCommands > 0) {
                cin >> nextCommandTime >> nextCommand;
                remainingCommands--;
            }
        }

    }

    return t;
}

int main() {
    cin >> N;
    cin >> K;
    for (int i = 0; i < K; i++) {
        int r, c;
        cin >> r >> c;
        map[r][c] = APPLE;
    }

    for (int r = 1; r <= N; r++) {
        map[r][0] = WALL;
        map[r][N+1] = WALL;
    }
    for (int c = 1; c <= N; c++) {
        map[0][c] = WALL;
        map[N+1][c] = WALL;
    }

    map[1][1] = BODY_RIGHT;
    snake.head = { 1, 1 };
    snake.tail = { 1, 1 };

    cin >> L;

    int answer = solution();
    cout << answer << endl;

    return 0;
}
