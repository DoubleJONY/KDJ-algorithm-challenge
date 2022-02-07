#include <vector>
#include <cstring>

using namespace std;

const int MAX_ROW = 200;
const int MAX_COL = 200;

const vector<pair<int, int>> DIRECTIONS {
    {-1, 0},
    {0, -1},
    {1, 0},
    {0, 1},
};

class Solution {
public:
    vector<vector<int>> *pMatrix;
    int nRows;
    int nCols;
    int cache[MAX_ROW][MAX_COL];

    int longestIncreasingPath(vector<vector<int>> &matrix) {
        init(matrix);

        int maxLength = 0;
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {
                maxLength = max(maxLength, getLongestIncreasingPathLengthFrom(r, c));
            }
        }

        return maxLength;
    }

    int getLongestIncreasingPathLengthFrom(int row, int col) {
        if (cache[row][col] != 0) {
            return cache[row][col];
        }

        int value = getValue(row, col);
        int maxLength = 1;

        for (pair<int, int> offset : DIRECTIONS) {
            int nextRow = row + offset.first;
            int nextCol = col + offset.second;
            int nextValue = getValue(nextRow, nextCol);

            if (value < nextValue) {
                int length = 1 + getLongestIncreasingPathLengthFrom(nextRow, nextCol);
                maxLength = max(maxLength, length);
            }
        }

        return (cache[row][col] = maxLength);
    }

    bool isValid(int row, int col) {
        if (row < 0 || row >= nRows) return false;
        if (col < 0 || col >= nCols) return false;
        return true;
    }

    int getValue(int row, int col) {
        if (isValid(row, col)) {
            return (*pMatrix)[row][col];
        }
        return 0;
    }

    void init(vector<vector<int>> &matrix) {
        pMatrix = &matrix;
        nRows = matrix.size();
        nCols = matrix[0].size();
        memset(cache, 0, sizeof(cache));
    }
};
