// https://programmers.co.kr/learn/courses/30/lessons/42628?language=cpp

#include <set>
#include <string>
#include <vector>

using namespace std;

vector<int> solution(vector<string> operations) {
    multiset<int> mset;
    for (const auto &s : operations) {
        if (s[0] == 'I')
            mset.insert(stoi(s.substr(2)));
        else if (!mset.empty()) {
            if (s[2] == '-')
                mset.erase(mset.begin());
            else
                mset.erase(--mset.end());
        }
    }
    return mset.empty() ? vector<int>{0, 0} : vector<int>{*mset.rbegin(), *mset.begin()};
}
