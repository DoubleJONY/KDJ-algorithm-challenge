#include <string>
#include <vector>
#include <queue>
#include <cstdio>

using namespace std;

struct Node {
	int value;
	bool isAlive = true;

	bool operator<(const Node& o) const {
		return value < o.value;
	}
};

struct cmpLess {
	bool operator()(Node* t, Node* u) {
		return *t < *u;
	}
};

struct cmpGreater {
	bool operator()(Node* t, Node* u) {
		return *u < *t;
	}
};

class DoublePriorityQueue {
private:
	vector<Node> nodes;
	priority_queue<Node*, vector<Node*>, cmpGreater > minHeap;
	priority_queue<Node*, vector<Node*>, cmpLess > maxHeap;

public:
	DoublePriorityQueue() {
		nodes.reserve(1000000);
	}

	void push(int value) {
		nodes.push_back(Node());
		Node* newNode = &nodes.back();

		newNode->value = value;

		minHeap.push(newNode);
		maxHeap.push(newNode);
	}

	int topMin() {
		while (!minHeap.empty()) {
			Node* node = minHeap.top();
			if (node->isAlive) {
				return node->value;
			}
			minHeap.pop();
		}

		return 0;
	}

	int topMax() {
		while (!maxHeap.empty()) {
			Node* node = maxHeap.top();
			if (node->isAlive) {
				return node->value;
			}
			maxHeap.pop();
		}

		return 0;
	}

	bool popMin() {
		while (!minHeap.empty()) {
			Node* node = minHeap.top();
			minHeap.pop();
			if (node->isAlive) {
				node->isAlive = false;
				return true;
			}
		}

		return false;
	}

	bool popMax() {
		while (!maxHeap.empty()) {
			Node* node = maxHeap.top();
			maxHeap.pop();
			if (node->isAlive) {
				node->isAlive = false;
				return true;
			}
		}

		return false;
	}
};

vector<int> solution(vector<string> operations) {
	DoublePriorityQueue q;
	for (string operation : operations) {
		char op;
		int value;
		sscanf(operation.c_str(), "%c %d", &op, &value);

		if (op == 'I') {
			q.push(value);
		}
		else if (op == 'D') {
			if (value == 1) {
				q.popMax();
			}
			else {
				q.popMin();
			}
		}
	}

	vector<int> answer;
	answer.push_back(q.topMax());
	answer.push_back(q.topMin());
	return answer;
}
