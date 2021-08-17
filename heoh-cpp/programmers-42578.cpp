#include <string>
#include <vector>
#include <map>

using namespace std;

int solution(vector<vector<string>> clothes) {
    map<string, int> counts;
    
    for (vector<string>& pair : clothes) {
        counts[pair[1]]++;
    }
    
    int nCases = 1;
    for (auto it = counts.begin(); it != counts.end(); it++) {
        nCases *= (it->second + 1);
    }
    
    int answer = nCases - 1;
    return answer;
}
