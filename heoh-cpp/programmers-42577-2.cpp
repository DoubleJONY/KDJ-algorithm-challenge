#include <string>
#include <vector>
#include <cstring>

using namespace std;

class Trie {
public:
    Trie* nodes[10];
    bool isBranchNode;
    bool isTerminalNode;

    Trie() {
        memset(nodes, 0, sizeof(nodes));
        isBranchNode = false;
        isTerminalNode = false;
    }

    ~Trie() {
        for (auto node : nodes) {
            if (node) delete node;
        }
    }

    bool checkValidationAndAdd(string numbers) {
        if (numbers == "") isTerminalNode = true;
        else isBranchNode = true;

        if (isTerminalNode && isBranchNode)
            return false;
        if (isTerminalNode)
            return true;

        char i = numbers[0] - '0';

        if (!nodes[i])
            nodes[i] = new Trie();

        return nodes[i]->checkValidationAndAdd(numbers.substr(1, numbers.size()));
    }
};

bool solution(vector<string> phone_book) {
    Trie trie;

    for (auto phoneNumber : phone_book) {
        if (!trie.checkValidationAndAdd(phoneNumber)) {
            return false;
        }
    }
    return true;
}
