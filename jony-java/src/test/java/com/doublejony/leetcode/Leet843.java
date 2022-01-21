package com.doublejony.leetcode;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.doublejony.common.AssertResolve.resolve;

class Master {

    String[] secret;
    int numguesses;
    int guessCount = 0;
    boolean correct = false;

    public Master(String secret, int numguesses) {
        this.secret = secret.split("");
        this.numguesses = numguesses;
    }

    public int guess(String word) {
        if(guessCount >= numguesses) {
            System.out.println("You took too many guesses.");
            return -1;
        }

        String[] words = word.split("");
        int matchCount = 0;
        for (int i = 0; i < words.length; i++) {
            if(words[i].equals(secret[i])) {
                matchCount++;
            }
        }

        if(matchCount == word.length()) {
            System.out.println("You guessed the secret word correctly.");
            correct = true;
        }
        guessCount++;
        return matchCount;
    }
}

@RunWith(DataProviderRunner.class)
public class Leet843 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        "anqomr",
                        new String[]{"pzrooh","aaakrw","vgvkxb","ilaumf","snzsrz","qymapx","hhjgwz","mymxyu","jglmrs","yycsvl","fuyzco","ivfyfx","hzlhqt","ansstc","ujkfnr","jrekmp","himbkv","tjztqw","jmcapu","gwwwmd","ffpond","ytzssw","afyjnp","ttrfzi","xkwmsz","oavtsf","krwjwb","bkgjcs","vsigmc","qhpxxt","apzrtt","posjnv","zlatkz","zynlqc","czajmi","smmbhm","rvlxav","wkytta","dzkfer","urajfh","lsroct","gihvuu","qtnlhu","ucjgio","xyycvd","fsssoo","kdtmux","bxnppe","usucra","hvsmau","gstvvg","ypueay","qdlvod","djfbgs","mcufib","prohkc","dsqgms","eoasya","kzplbv","rcuevr","iwapqf","ucqdac","anqomr","msztnf","tppefv","mplbgz","xnskvo","euhxrh","xrqxzw","wraxvn","zjhlou","xwdvvl","dkbiys","zbtnuv","gxrhjh","ctrczk","iwylwn","wefuhr","enlcrg","eimtep","xzvntq","zvygyf","tbzmzk","xjptby","uxyacb","mbalze","bjosah","ckojzr","ihboso","ogxylw","cfnatk","zijwnl","eczmmc","uazfyo","apywnl","jiraqa","yjksyd","mrpczo","qqmhnb","xxvsbx"},
                        10
                },
                {
                        "hbaczn",
                        new String[]{"gaxckt","trlccr","jxwhkz","ycbfps","peayuf","yiejjw","ldzccp","nqsjoa","qrjasy","pcldos","acrtag","buyeia","ubmtpj","drtclz","zqderp","snywek","caoztp","ibpghw","evtkhl","bhpfla","ymqhxk","qkvipb","tvmued","rvbass","axeasm","qolsjg","roswcb","vdjgxx","bugbyv","zipjpc","tamszl","osdifo","dvxlxm","iwmyfb","wmnwhe","hslnop","nkrfwn","puvgve","rqsqpq","jwoswl","tittgf","evqsqe","aishiv","pmwovj","sorbte","hbaczn","coifed","hrctvp","vkytbw","dizcxz","arabol","uywurk","ppywdo","resfls","tmoliy","etriev","oanvlx","wcsnzy","loufkw","onnwcy","novblw","mtxgwe","rgrdbt","ckolob","kxnflb","phonmg","egcdab","cykndr","lkzobv","ifwmwp","jqmbib","mypnvf","lnrgnj","clijwa","kiioqr","syzebr","rqsmhg","sczjmz","hsdjfp","mjcgvm","ajotcx","olgnfv","mjyjxj","wzgbmg","lpcnbj","yjjlwn","blrogv","bdplzs","oxblph","twejel","rupapy","euwrrz","apiqzu","ydcroj","ldvzgq","zailgu","xgqpsr","wxdyho","alrplq","brklfk"},
                        10
                },
                {
                        "acckzz",
                        new String[]{"acckzz", "ccbazz", "eiowzz", "abcczz"},
                        10
                },
                {
                        "hamada",
                        new String[]{"hamada","khaled"},
                        10
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String secret, String[] wordlist, int numguesses) {

        Master master = new Master(secret, numguesses);

        Stopwatch timer = Stopwatch.createStarted();
        new Solution().findSecretWord(wordlist, master);
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), true, master.correct, timer.stop());
    }

    /**
     * // This is the Master's API interface.
     * // You should not implement it, or speculate about its implementation
     * interface Master {
     *     public int guess(String word) {}
     * }
     */
    class Solution {
        public void findSecretWord(String[] wordlist, Master master) {

            int maxMatchCount = 0;
            String[] maxMatchWord = null;

            for (String word : wordlist) {
                String[] wordSpell = word.split("");
                if(maxMatchWord != null) {
                    int matchCount = 0;
                    for (int i = 0; i < maxMatchWord.length; i++) {
                        if (maxMatchWord[i].equals(wordSpell[i])) {
                            matchCount++;
                        }
                    }
                    if (matchCount < maxMatchCount) {
                        continue;
                    }
                }
                int wordMatchCount = master.guess(word);

                if (wordMatchCount == word.length()) {
                    break;
                }

                if (wordMatchCount > maxMatchCount) {
                    maxMatchCount = wordMatchCount;
                    maxMatchWord = word.split("");
                }
            }
        }
    }
}
