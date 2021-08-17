#include <string>
#include <vector>
#include <map>

using namespace std;

string solution(vector<string> participant, vector<string> completion) {
    string answer = "";
    map<string, int> counter;

    for (string& s : completion) {
        counter[s]++;
    }

    for (string& s : participant) {
        counter[s]--;
        if (counter[s] < 0) {
            answer = s;
        }
    }

    return answer;
}
