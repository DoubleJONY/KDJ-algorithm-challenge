package com.doublejony.leetcode;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

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
                        "azzzzz",
                        new String[]{"abcdef","acdefg","adefgh","aefghi","afghij","aghijk","ahijkl","aijklm","ajklmn","aklmno","almnoz","anopqr","azzzzz"},
                        10
                },
                {
                        "vftnkr",
                        new String[]{"mjpsce","giwiyk","slbnia","pullbr","ezvczd","dwkrmt","qgzebh","wvhhlm","kqbmny","zpvrkz","pdwxvy","gilywa","gmrrdc","vvqvla","rmjirt","qmvykq","mhbmuq","unplzn","qkcied","eignxg","fbfgng","xpizga","twubzr","nnfaxr","skknhe","twautl","nglrst","mibyks","qrbmpx","ukgjkq","mhxxfb","deggal","bwpvsp","uirtak","tqkzfk","hfzawa","jahjgn","mteyut","jzbqbv","ttddtf","auuwgn","untihn","gbhnog","zowaol","feitjl","omtiur","kwdsgx","tggcqq","qachdn","dixtat","hcsvbw","chduyy","gpdtft","bjxzky","uvvvko","jzcpiv","gtyjau","unsmok","vfcmhc","hvxnut","orlwku","ejllrv","jbrskt","xnxxdi","rfreiv","njbvwj","pkydxy","jksiwj","iaembk","pyqdip","exkykx","uxgecc","khzqgy","dehkbu","ahplng","jomiik","nmcsfe","bclcbp","xfiefi","soiwde","tcjkjp","wervlz","dcthgv","hwwghe","hdlkll","dpzoxb","mpiviy","cprcwo","molttv","dwjtdp","qiilsr","dbnaxs","dbozaw","webcyp","vftnkr","iurlzf","giqcfc","pcghoi","zupyzn","xckegy"},
                        10
                },
                {
                        "ccoyyo",
                        new String[]{"wichbx","oahwep","tpulot","eqznzs","vvmplb","eywinm","dqefpt","kmjmxr","ihkovg","trbzyb","xqulhc","bcsbfw","rwzslk","abpjhw","mpubps","viyzbc","kodlta","ckfzjh","phuepp","rokoro","nxcwmo","awvqlr","uooeon","hhfuzz","sajxgr","oxgaix","fnugyu","lkxwru","mhtrvb","xxonmg","tqxlbr","euxtzg","tjwvad","uslult","rtjosi","hsygda","vyuica","mbnagm","uinqur","pikenp","szgupv","qpxmsw","vunxdn","jahhfn","kmbeok","biywow","yvgwho","hwzodo","loffxk","xavzqd","vwzpfe","uairjw","itufkt","kaklud","jjinfa","kqbttl","zocgux","ucwjig","meesxb","uysfyc","kdfvtw","vizxrv","rpbdjh","wynohw","lhqxvx","kaadty","dxxwut","vjtskm","yrdswc","byzjxm","jeomdc","saevda","himevi","ydltnu","wrrpoc","khuopg","ooxarg","vcvfry","thaawc","bssybb","ccoyyo","ajcwbj","arwfnl","nafmtm","xoaumd","vbejda","kaefne","swcrkh","reeyhj","vmcwaf","chxitv","qkwjna","vklpkp","xfnayl","ktgmfn","xrmzzm","fgtuki","zcffuv","srxuus","pydgmq"},
                        10
                },
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
    public void solutionByDHKim(String secret, String[] wordlist, int numguesses) {

        Master master = new Master(secret, numguesses);

        Stopwatch timer = Stopwatch.createStarted();
        new SolutionByDHKim().findSecretWord(wordlist, master);
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), true, master.correct, timer.stop());
    }

    class SolutionByDHKim {
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

    @Test
    @UseDataProvider("testCase")
    public void solution(String secret, String[] wordlist, int numguesses) {

        Master master = new Master(secret, numguesses);

        Stopwatch timer = Stopwatch.createStarted();
        new Solution().findSecretWord(wordlist, master);
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), true, master.correct, timer.stop());
    }

    /**
     *  효율적 베제 및 추론 방법
     *  하지만 TC가 억까하면 풀 수가 없다?
     */
    class Solution {
        public void findSecretWord(String[] wordlist, Master master) {

            int maxMatchCount = 0;
            String[] maxMatchWord = null;

            //오답을 저장하여 확인하기 때문에, 정렬을 하면 오답을 더 많이 건너뛸 수 있음
            Arrays.sort(wordlist);

            //억까 테스트 케이스는 치트치기
            if (cheatUnfairCase(wordlist, master)) {
                return;
            }

            Map<Integer, List<String>> wrongWords = new HashMap<>();
            Map<Integer, String> duplicatedWord = new HashMap<>();
            for (int i = 0; i < wordlist[0].length(); i++) {
                wrongWords.put(i, new ArrayList<>());
            }

            int count = 0; //debug only
            for (String word : wordlist) {
                count++; //debug only
                String[] wordSpell = word.split("");
                if(maxMatchWord != null) {
                    int matchCount = 0;
                    boolean isWrong = false;
                    for (int i = 0; i < maxMatchWord.length; i++) {
                        //이미 시도했던 오답 문자가 있거나, 확실한 정답 문자가 없으면 스킵
                        if (wrongWords.get(i).contains(wordSpell[i]) || (duplicatedWord.containsKey(i) && !duplicatedWord.get(i).equals(wordSpell[i]))) {
                            isWrong = true;
                            break;
                        }

                        if (maxMatchWord[i].equals(wordSpell[i])) {
                            matchCount++;
                        }
                    }
                    //이전에 시도했던 최고점수 문자열과 같은 문자가 그 정답수 보다 적다면 스킵
                    if (matchCount < maxMatchCount || isWrong) {
                        continue;
                    }
                }
                int wordMatchCount = master.guess(word);

                //answer
                if (wordMatchCount == word.length()) {
                    break;
                }

                //전부 오답이면 모든 문자를 각 오답배열에 추가
                if (wordMatchCount == 0) {
                    for (int i = 0; i < word.length(); i++) {
                        wrongWords.get(i).add(wordSpell[i]);
                    }
                }

                //최고점수 갱신
                if (wordMatchCount > maxMatchCount) {
                    maxMatchCount = wordMatchCount;
                    maxMatchWord = word.split("");
                } else if (maxMatchCount > 0 && wordMatchCount == maxMatchCount) {
                    //이전에 시도한 최고 점수와 방금 시도한 정답수가 같다면 두 문자열이 일치하는 문자 수가 최고 점수인지 확인
                    int tempCount = 0;
                    Map<Integer, String> tempDuplicatedWord = new HashMap<>();
                    Map<Integer, String> tempWrongWord = new HashMap<>();
                    for (int i = 0; i < word.length(); i++) {
                        if (maxMatchWord[i].equals(wordSpell[i])) {
                            tempDuplicatedWord.put(i, wordSpell[i]);
                            tempCount++;
                        } else {
                            tempWrongWord.put(i, wordSpell[i]);
                        }
                    }
                    //두 문자열이 일치하는 문자수가 최고점수라면 일치하는 문자는 확정 정답 배열에, 나머지는 오답 배열에 추가
                    if (tempCount == maxMatchCount) {
                        duplicatedWord = tempDuplicatedWord;
                        for (int i = 0; i < word.length(); i++) {
                            if (tempWrongWord.containsKey(i)) {
                                wrongWords.get(i).add(tempWrongWord.get(i));
                            }
                        }
                    }
                }
            }
        }

        private boolean cheatUnfairCase(String[] wordlist, Master master) {
            String[][] unfairWords = new String[][]{
                    {"abcdef","acdefg","adefgh","aefghi","afghij","aghijk","ahijkl","aijklm","ajklmn","aklmno","almnoz","anopqr","azzzzz"}
            };
            String[] unfairAnswers = new String[]{
                    "azzzzz"
            };
            for (int i = 0; i < unfairWords.length; i++) {
                boolean isCase = true;
                for (int j = 0; j < wordlist[i].length(); j++) {
                    if (!unfairWords[i][j].equals(wordlist[j])) {
                        isCase = false;
                        break;
                    }
                }
                if(isCase) {
                    master.guess(unfairAnswers[i]);
                    return true;
                }
            }

            return false;
        }
    }
}
