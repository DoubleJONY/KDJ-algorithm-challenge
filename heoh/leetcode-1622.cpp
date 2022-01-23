const int MOD = 1e9 + 7;
const int MAX_LENGTH = 1e5 + 1;


class Fancy {
public:
    int values[MAX_LENGTH];
    int length;
    long long op_mul[MAX_LENGTH];
    long long op_inc[MAX_LENGTH];

    Fancy() {
        length = 0;
    }

    void append(int val) {
        values[length] = val;
        ++length;
        op_mul[length] = 1;
        op_inc[length] = 0;
    }

    void addAll(int inc) {
        op_inc[length] += inc;
    }

    void multAll(int m) {
        int top = length;
        op_mul[top] = (op_mul[top] * m) % MOD;
        op_inc[top] = (op_inc[top] * m) % MOD;
    }

    int getIndex(int idx) {
        if (idx >= length)
            return -1;

        long long val = values[idx];
        for (int i = idx + 1; i <= length; ++i) {
            val *= op_mul[i];
            val += op_inc[i];
            val %= MOD;
        }
        return val;
    }
};
