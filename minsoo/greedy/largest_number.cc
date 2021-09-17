// https://programmers.co.kr/learn/courses/30/lessons/42883

#include <string>
#include <deque>

using namespace std;

string solution(string number, int k) {
    deque<char> stack;
    for (const auto &n : number) {
        while (!stack.empty() && k && n > stack.back()) {
            stack.pop_back();
            k--;
        }
        stack.emplace_back(n);
    }
    return string(stack.begin(), stack.begin() + stack.size() - k);
}
