#include <string>
#include <vector>
#include <algorithm>
#include <iostream>
using namespace std;

int solution(vector<int> people, int limit) {
    int answer = 0;

            sort(people.begin(), people.end());

            int size = people.size();
            int i = 0;
            int j = 0;

            for (i = size-1; i > j; i--)
            {
                if ( people[i] + people[j] <= limit)
                {
                    j++;
                    answer++;	
                }
                else
                {
                    answer++;
                }				
            }
            if (i == j)
            {
                answer++;
            }

            return answer;
}