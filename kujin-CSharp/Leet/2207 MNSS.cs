public class Solution {
    public long MaximumSubsequenceCount(string text, string pattern) {
        var firstChar = pattern[0];
        char secondChar = pattern[1];
        
        long countFirst = 0, countSecond = 0, result = 0;
        
        for(int i = 0; i < text.Length; i++) {
            char c = text[i];
            
            if(c == firstChar) {
                countFirst++;
            } else if (c == secondChar) {
                countSecond++;
                result += countFirst;
            }
        }
        
        result += Math.Max(countFirst, countSecond);

        if (firstChar == secondChar)
        {
            return ((countFirst + 1) * (countFirst) / 2);
        }

        return result;
    }
}