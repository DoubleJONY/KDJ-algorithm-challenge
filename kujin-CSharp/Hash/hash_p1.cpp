#include <iostream>
#include <string>
#include <vector>
#include <unordered_map>

using namespace std;

string solution(vector<string> participant, vector<string> completion) {
    string answer = "";
    unordered_map<string, int> map;

    for(vector<int>::size_type i =0; i<participant.size(); i++){
        map[participant[i]] += 1;
    }
    for(vector<int>::size_type i =0; i<completion.size(); i++){
        map[completion[i]] -= 1;
    }
    for(vector<int>::size_type i =0; i<participant.size(); i++){
        if(map[participant[i]] != 0)
        {
            answer = participant[i];
            break;
        }
    }


    return answer;
}