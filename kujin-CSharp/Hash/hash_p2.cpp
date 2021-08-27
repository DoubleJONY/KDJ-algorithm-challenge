#include <iostream>
#include <string>
#include <vector>
#include <unordered_map>
#include <algorithm>


using namespace std;

bool solution(vector<string> phone_book) {
      bool answer = true;
   
    unordered_map<string, int> map;
    sort(phone_book.begin(), phone_book.end());
    //sort 한번 하고
    for (int index = 0; index < phone_book.size(); index++)
    {
 
        if(phone_book[index].size() > 0)
        {
            map[phone_book[index]] = 1;

            for (int stringIndex = 0; stringIndex < phone_book[index].size(); stringIndex++)
            {
                
                if(map[phone_book[index].substr(0,stringIndex)] == 1)
                {
                    answer = false;
                    break;
                }

            }

        }
        if(answer == false)
            break;
    }

    return answer;
}