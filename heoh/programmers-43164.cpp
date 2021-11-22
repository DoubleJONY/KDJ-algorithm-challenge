#include <string>
#include <vector>
#include <algorithm>
#include <map>

using namespace std;

struct Ticket {
    string src;
    string dst;
    bool used;
};

vector<Ticket> tickets;
map<string, vector<Ticket*>> ticket_map;

void init_tickets(vector<vector<string>> raw_tickets) {
    sort(raw_tickets.begin(), raw_tickets.end());
    for (auto data : raw_tickets) {
        string src = data[0];
        string dst = data[1];

        tickets.push_back({ src, dst, false });
    }
    for (Ticket& ticket : tickets) {
        if (ticket_map.count(ticket.src) == 0)
            ticket_map[ticket.src] = vector<Ticket*>();
        ticket_map[ticket.src].push_back(&ticket);
    }
}

bool build_path(vector<string>& path, string cur) {
    path.push_back(cur);

    if (path.size() == tickets.size() + 1) {
        return true;
    }

    for (auto ticket : ticket_map[cur]) {
        if (ticket->used)
            continue;

        ticket->used = true;
        if (build_path(path, ticket->dst))
            return true;
        ticket->used = false;
    }

    path.pop_back();

    return false;
}

vector<string> solution(vector<vector<string>> raw_tickets) {
    vector<string> path;
    init_tickets(raw_tickets);
    build_path(path, "ICN");
    return path;
}
