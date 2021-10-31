#include <string>
#include <vector>

using namespace std;

void set_group_and_propagate(int n, vector<vector<int>>& computers, int computer_id, vector<int>& group_ids, int group_id) {
    group_ids[computer_id] = group_id;
    
    vector<int>& neighbors = computers[computer_id];
    for (int neighbor_id = 0; neighbor_id < neighbors.size(); neighbor_id++) {
        if (neighbors[neighbor_id] == 0)
            continue;
        if (group_ids[neighbor_id] != -1)
            continue;
        
        set_group_and_propagate(n, computers, neighbor_id, group_ids, group_id);
    }
}

int solution(int n, vector<vector<int>> computers) {
    vector<int> group_ids(n, -1);
    int n_groups = 0;
    for (int i = 0; i < n; i++) {
        if (group_ids[i] != -1)
            continue;
        
        int group_id = n_groups;
        set_group_and_propagate(n, computers, i, group_ids, group_id);
        n_groups++;
    }
    return n_groups;
}
