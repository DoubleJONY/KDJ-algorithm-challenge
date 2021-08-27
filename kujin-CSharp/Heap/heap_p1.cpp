#include <string>
#include <vector>
#include <queue>

using namespace std;
int solution(vector<int> scoville, int K) {
    int answer = 0;
    priority_queue <int, vector<int>, greater<int>> foods (scoville.begin(), scoville.end());
    
    int first = 0;
    int second = 0;

    while (true) {
        if(foods.top() > K)
            break;
        
        if (foods.size() < 2) 
        return -1;
        
        first = 0;
        second = 0;
        
        first = foods.top();
        foods.pop();

        second = foods.top();
        foods.pop();

        foods.push(first + second * 2);

        answer++;	
    }

    return answer;

}