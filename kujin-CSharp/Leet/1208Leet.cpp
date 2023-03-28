class Solution {
public:
    string removeDuplicates(string s, int k) {
        stack<pair<char, int>> StringStack;

        for(int i = 0; i < s.length(); i++) {
            char c = s[i];

            if(StringStack.empty()) {
                StringStack.push(make_pair(c, 1));
                continue;
            }

            auto [chr, count] = StringStack.top();
            if(c == chr) {
                if(count+1 >= k) {
                    StringStack.pop();
                } else {
                    StringStack.top().second++;
                }
            } else {
                StringStack.push(make_pair(c, 1));
            }
        }

        string res = "";

        while(!StringStack.empty()) {
            auto [c, count] = StringStack.top();
            res = string(count, c) + res;
            StringStack.pop();
        }
        return res;
    }
};