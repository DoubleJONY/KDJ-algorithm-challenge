#include <string>
#include <vector>
#include <cstring>

using namespace std;

vector<int> g_numbers;
int g_target;
int cache[21][2001];

int countCases(int index, int sum) {
    if (cache[index][sum] >= 0)
        return cache[index][sum];
    if (index >= g_numbers.size())
        return (cache[index][sum] = (sum == g_target ? 1 : 0));
    
    int picked = g_numbers[index];
    int count = 0;
    count += countCases(index + 1, sum + picked);
    count += countCases(index + 1, sum - picked);
    return (cache[index][sum] = count);
}

int solution(vector<int> numbers, int target) {
    g_numbers = numbers;
    g_target = target;
    memset(cache, -1, sizeof(cache));
    int answer = countCases(0, 0);
    return answer;
}
