package com.rhino.leetcode.hard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

interface Master{
    int guess(String word);
}

public class Hard00843_Guess_the_world {
}

class Solution {
    public void findSecretWord(String[] wordlist, Master master) {
        // basic ver
        int x=0;

        for(int i=0;i<10;i++){
            String guess = wordlist[new Random().nextInt(wordlist.length)];
            x = master.guess(guess);
            List<String> tList = new ArrayList<>();
            for(String word: wordlist){
                if(matchCharCount(guess,word) == x){
                    tList.add(word);
                }
            }
            wordlist = tList.toArray(new String[tList.size()]);
        }

    }
    private int matchCharCount(String a,String b){
        int matchCnt = 0;
        for(int i=0;i<a.length();i++){
            if(a.charAt(i) == b.charAt(i)){
                matchCnt++;
            }
        }
        return matchCnt;
    }
}