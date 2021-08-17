#include <string>
#include <vector>
#include <algorithm>

using namespace std;

bool solution(vector<string> phone_book) {
    sort(phone_book.begin(), phone_book.end());

    for (int i = 1; i < phone_book.size(); i++) {
        string& prev = phone_book[i - 1];
        string& cur = phone_book[i];
        if (prev == cur.substr(0, prev.size())) {
            return false;
        }
    }
    
    return true;
}
