class Solution {
public:
    int countTriplets(vector<int>& arr) {
        int n = arr.size();
        int ans = 0;
        for (int i = 0; i < n - 1; i++) {
            int a = arr[i];
            for (int k = i + 1; k < n; k++) {
                a = arr[k] ^ a;
                if (a == 0) {
                    ans += k - i;
                }
            }
        }

        return ans;
    }
};