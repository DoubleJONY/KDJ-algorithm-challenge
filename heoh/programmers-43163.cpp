#include <string>
#include <vector>
#include <queue>

using namespace std;

bool word_can_be_next_step(string cur, string next) {
    int n_diffs = 0;
    for (int i = 0; i < cur.size(); i++) {
        n_diffs += cur[i] != next[i];
    }
    return n_diffs == 1;
}

int solution(string begin, string target, vector<string> words) {
    vector<bool> visited(words.size(), false);
    
    queue<pair<int, string>> q;
    q.push({0, begin});
    
    while (!q.empty()) {
        auto item = q.front();
        int step = item.first;
        string cur = item.second;
        q.pop();

        if (cur == target)
            return step;

        for (int i = 0; i < words.size(); i++) {
            if (visited[i])
                continue;
            if (word_can_be_next_step(cur, words[i])) {
                q.push({step + 1, words[i]});
                visited[i] = true;
            }
        }
    }
    
    return 0;
}
