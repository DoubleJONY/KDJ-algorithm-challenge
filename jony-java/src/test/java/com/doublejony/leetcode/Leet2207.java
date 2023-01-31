package com.doublejony.leetcode;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

import static com.doublejony.common.AssertResolve.resolve;

@RunWith(DataProviderRunner.class)
public class Leet2207 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][] {
                {
                        "vnedkpkkyxelxqptfwuzcjhqmwagvrglkeivowvbjdoyydnjrqrqejoyptzoklaxcjxbrrfmpdxckfjzahparhpanwqfjrpbslsyiwbldnpjqishlsuagevjmiyktgofvnyncizswldwnngnkifmaxbmospdeslxirofgqouaapfgltgqxdhurxljcepdpndqqgfwkfiqrwuwxfamciyweehktaegynfumwnhrgrhcluenpnoieqdivznrjljcotysnlylyswvdlkgsvrotavnkifwmnvgagjykxgwaimavqsxuitknmbxppgzfwtjdvegapcplreokicxcsbdrsyfpustpxxssnouifkypwqrywprjlyddrggkcglbgcrbihgpxxosmejchmzkydhquevpschkpyulqxgduqkqgwnsowxrmgqbmltrltzqmmpjilpfxocflpkwithsjlljxdygfvstvwqsyxlkknmgpppupgjvfgmxnwmvrfuwcrsadomyddazlonjyjdeswwznkaeaasyvurpgyvjsiltiykwquesfjmuswjlrphsdthmuqkrhynmqnfqdlwnwesdmiiqvcpingbcgcsvqmsmskesrajqwmgtdoktreqssutpudfykriqhblntfabspbeddpdkownehqszbmddizdgtqmobirwbopmoqzwydnpqnvkwadajbecmajilzkfwjnpfyamudpppuxhlcngkign",
                        "rr",
                        496
                },
                {
                        "fwymvreuftzgrcrxczjacqovduqaiig",
                        "yy",
                        1
                },
                {
                        "uoyscfbrttgjaezfsgorwqilmczvzahynqwyjrqxizswmowqjcllovkybmumiliiqqjbdlqcapmzgaihbpaytuegrxzpqzmapztgbivvjghxzaamdzztbhkalpegxhvuhohjstawvdqcqfglbmlbxeqrjryelpfexubpieyzufsbglncihiatzkukcalsnxmujrzwzszqwflszryqqugjuzxybkztrxwjeklfvrknmmvirgakvfkihoowppbmsobvwtrpcwwrilhfyjzxgiyxhhdusrykvjycmxqfwqeuygetgicpopzmoqamwqszcvqtmqcdalumrfwckqwpjkghdqxfcoxvoasbcucqzlxnhbhqywbozzifnfwslzsjbbofdsgpwnfoceaehbmamreyzzhxecuvziswatjxjdfwlnyohdsbvsufdcobwhmfjisjwedejauoneyjuzfehvfadfahpfosttscqjlzjkvpwdxsvaybpondaffedwqtvdeyuashbtuylrsqpzcczvxfbfpliugwkgyqqjagmiiheusgafqtogpydljmiuabutvqhrcgpuzklsvihsunpukhmgrjnjieihmpstjfkukzpzhvnpbsebfhoqkqczfjjbntgqicjnjdncujhsjkgochjxhircnnmoxpdqwcybsrwafjfispadevqggzfyhfqblwkdrllskhkutafftzuojnhpgrzqbxqebjocomwjosesfybhbmciwakkawksyqqnlaxpklyfhwsoqihihevoprwatavzhbyiyiiniryycclrrlwkmhprdxqbmowdvvxppxdaxcfqirrbhtutjbbcaqkikgzjqsqqrjcvtvbsiceftjbauissxuwugxoxyabgivgszrseqcoxowlaqftpjtomelazoxxdmjmzzrgjnxehwowwabbarkxdazqpedbnmnxirfbbvjbtshojidgnvxwiimzmhhpqrnhnjyvwrxqrujhdtjiqzdmitedjtkejdkkdmtuijxofvsalcfxpxgxuvwuvjastieckcsvidrrsnhyaczbtncebqapjmxdzvbhodjxzncrqnvcnvsdsarcwwmizrtqwqluptnnvfqoyozaaxyolenpoclxvarndsipnxyghspshqobfilbtzxixqfzgkhegguhjfqscfqajrsshjneppcwrmqvnubyckwczbuvszussghzhxnqwrkzumzwikffycsznnifvfbtgouorobzzynghtzqvkcfjafeqfnxbrtsnouotpsrwhsumorwcmwclvgujjlnzcivrqrjsofxxvbardxxufvaysyukyfgbvpdapumckbquhowhmrowsqaywvlhqztiuugqnrabsddiqhkureqrkgnwebkgphlrhmzivcnqysuqicllnerkoariejyytxaodgbhjonnsxpuhegxceuygcchkzcxizwaplflbdgqwwuzxwzahkpfjnaulifpenyopjldisrkmgounisqurqrejvfsylfxaclgnaxispvnzfqqmkzclniwstjsnzzzchexuewrnafsfhnmdgdsafwvhpqjkczvqbowlteetrpynwygbqadqeikisvxmytxydflvsuxtpkubtadlrpkhdhtleokatinsioqrliccembumjwlkycprektrikofpttfsgeuxwxfclktvjasotwrewddlyvllksrlhyivwajrrzdttmuiaeboakrdrulcvifehyemgqrbdspeaqhefxxkhjylexlizpduuahdvwyokimrmdoawbmoobnlbppmttluammaocplrnznxgatwsxkrdrnoexbndptdlbffpvavuaucgnrbveafvxnyyzcyeelwlnshbpnjztngxsyrvajuylrkazvywmirmflzsfzjsxtimdhtuddycqgxtqoxzvegpzjfvjgpedkeokrtzdcsdrkoaeoajqrvqdgqmjkvgpkmnhncfaybmrxgvsmfbduoxjnpmmrmkmyptyzuvyzszuankvpzzbygkhoiexjdtirpfvfxqomjrcgigomafffrawsibrcujizbtrqbeusfmqpfdmhxujbyuxkfavvtitpkppwjkxiwlglzqqpwbzoxjycurmrnsmhecwyzvarnsjqwlvgdvszcjfccsyjejlbqhyzkwntypklciyzyaetgyelvefwahjfgjscnsktjaarytviwijvmozgyxohltjdjmscszjkxcxkdnejqnqhitqsvtccxhipmsfpcvohjoovqbgdqnyxdkxktxqquzcdasyogkqrshwytlcdruxoonhtcketmsegswhfqomvhxhrcczqnrbhdusvducsmjotrdvskkznlangwdvdrtzeqlcftygjiwxyjzlnhhxuaxrugjzeombocihftcutxtnecrijhzlusiuxhhadcsrrajvxdeklwmyuyfqlokavwgianauyemlcoavjigzkdksovlaqvyjrallhkxraacwimhcmaymdeufolomhcwcdqlgmwrlbufsjarixbdiabdhggzugfsxhacqczkuoyfqxixovkhsgianhiamdweaycakjekugzqovdhjdvuqvdlmmguqsfrrdpnlzgtxkfchkyrwjyajyclggpmkpwgpgtjpvvgfkqqedqjmxjwogayfnfipueckpbwbxrpwsmmhpntcgtygrxlyxostfzszdsfoufydqgweqcduxxjxlshvickjulndmqgrocjzvczpgimixeyjiyxfwjytlswhuxzutgndvibcvtujpcygzzmkhhylfgceauxvkuppcyphzpjzlduiquvzdokhrawlbcmwtlavjyokybfsqepuvsatgclfnqxgugztzbjbhaxvvxegcabrnvctrahngcalvypwwzbwjjuhwfmstkhfylenvawpxtnizpvmsepkfrzjrisuuioyyhlxllfwaqqumrlomkfimacpattehfkdenkmurmavpjwrzsmhayapynvthwzvyamyvdsnbdgjwyaxmsilkaxpfdhjpdvqlypxpyyowvdwgrzbjgjcdftryfcimhgbrrloebzcahswcuntjrcdmwxpeelrlhsztqfwmvchxxgvnhdnxfsgcbrdjzfdtxtnlqylwsszugecdbhffcwadpxumlmnzjeaejakmjqstzixucvsutisenjcrssqzeaygpnrdjjanfijguyofhkipvsypjlcmihzxiqmhwbydspiodkfgryyeescyffoqbskcatbqhyenpzymrfbmbvcnasducgtwdeabnyztdnppgvaqsjzmkbignjquxarmqwehnsuruwszrxkpohjycihrzoviqhrhtokfdgxunoxwzevjgkcliennamgvizzxwskchoyfveugyyokcnhshnjrholmbarovdelbuxwtdtuhndpodqxbvezgrujdygonmvtsgluafxxhzqhqfasrlrvlrzgimmpgszfqrngaksrvatpqjzaczrzugnijoiutbjvbeusxrpsjtydisfkdwbgaxfwlxxovouhwfhntwvuvtfppdilaosqxvdmyjyrfwpsshsecdzztfsxcjyohmktmvhhnmlhzidpjkhgpnldkgeopzbogdrmluupfbaoppxnkdaerlqeaxtwwkpdutydgmjzalqbgtiqqpvmpzwmomanzydptpxftefegzayqesioozrhrcybhsyvmfwaczwtljgkwmhdkhetvuyibgoovchqcastgkfteawsbmeuyigmqybkcaykqtzeyklyjlhsmhwzniphznbbwdhgrzwqeizkgxmmuwjjqwzdxrumlzeciugqsqlgxbisvktbaqxiudlybqxackbhdjsexupstdtdulegisnrggnljtwcewetwcuqtyfqwiedyxudpacvqaztbrnsshpyabkyumaxkkafjcljdpumdgebecnkpvzkimyibckvxfrzpmmrjbawsqxgsnrjfzanndjpktikpfmlfihaiedgpqmttxqhbgvqdsjvwdeoobcyxuqdbnneygcgmlgkkxnyezjxprskpsimcsfacmqnzmzwmpkbbvvypldowtdavmbqfmpeymqiwwdyyztivfbgzeuqsnokqjxvtkahfxmauwtlhgovxxhwegcohdwgrbevaknvfwjlorejsqfjlyidkuhdahzzallkadbumjpaawzxnxaqlvvfwtzyczkibuweixrrsfsdiupqhntwfswwkmdlejthpupdgwuhyjoaeesboggjfbaiwhlfhqjrgkhmeykgetgvmlqrekhfudpqmvbhloaqnimtrieldxsbzzjsfiagvrbovdtmieocbztiqpohxqeflruuwlgtgnpvygdwcgnqzyktrizgnzdnesakzgrzrefhinzettgtvziiaaqayypozffhohdqtsrmqqyjkjriehufxcmmnsbtjeoqtqnysrcflrdrukxqifhawhdrujojjrjmhfwutkclzqxlzkxvsmijazweeefmrdxzcrlwappncfddnrfjkugjwwcnhgswapbddejzjhewlhnzplcejynjqhbxaqwatxqnpbhakfsvbanachufxxqgwuyncgjkqlloumtxxsgxabhyixprscpmztjcdzngozihpunhuabatrjheaisgnlnavubzkzfxusrkccswbiyzzuqdsnxzgmjhthizpynrvlmmjxrakienavhqxznxcsdiztbbtbasmzcqyzlmlstgtlqpdxgulvbffzufghhxmabfetxpfmtihvqqrxddqkhyhlcphwqdikpqvmxlasgfkihvnmyzgafehidpctmrdozbuchgtehpookypkurgjqvbmnitbopodnnhueqmzadhsigoebflwlvfzvfcdrplzmvkoiiucqfqwzxlqrfpcrpxjyrspfhvljirvgeswuykrtbwmtacjjamhurreouxtsbetholzsnedarpyxrnhfnxmbcmzsvyplgwwwyklwqmytpaqajpqjvorestnqltwylsibvdigyxtqaagslghosagadhuhjiynbmkejjzshwddifbyyegwmzkqopcjywcalmgosixqzvdibwokaenfvwpyxmthiqnezjwatwvgxckybxgplakcwpqeccsqwymuyvbvgefvcfmqlffsczsnbehkiekweckzpvdijbhenrboaaepgmeynrgkwshqhemikbpbgwwopwwctfpnmpccfplqnzwrenfuxcdykmvitzrfthkzhjxrfnnaabcvharwmthoxeyitytucrzgktnfsadnezmehkxlplppdgxmslyhxnivexzlgmrhylndqxcuqamysndktogeswntvqympdvvklfefcypnhtkbkuzhiiyxipvrrdznidsnhsnpngixypabvlcflhjmdezhkwxbssclfrixfdyipsldcmhwitzysmcmaqrqzgwiiusywqctzosguvradizcbrwklztudewwiorcgthjpbptlzftuwdzylziwbpmtqjbefqmhvmmiinkfetijpbuehiudclwwcwxqclfveiwmnmiecvtlljnynxyenwliuwimfsyvnkxybqrvaqpjejmvjbthhqteivlxjbjpaeymyemfhzcucznkrqsayboglywgjumeqgcnkfcmhyhtifmfryqotatmghwdcybhuzhixvczksuepjawdwnvacrlazjimxybtsyxholbizqankyhpyfgtinbdfzoxvdnfyumrwamnsnumipbtwyiubwzbhcdtthbetsdgjbkkkyaevmhkkrhfytomcxfuwxfmxplxnvrjghrfymrqisuphxmrofnltnyjxwpbdvfblzpqbvhiykgpzzglvzxeolsbfhzipemzfxximueomxecowwoaajmxljaxdsvfrybxpbaonqdrmsrxuifgvyutzaldftrhurustptxxmxmdvfrzpdbomcwqzfwvxasfkqlsechzapqpmrbgusbwucvvxmmedjfpgterbjhrweywqhilmommscrjeuuhwdjypbmbaohgqlmsnigtsbzoyejaiusgnnpfhqsbmuyzkziuebnoaslgtcopscutzpocjhdevbtcwftbtuojqppkicuvywpmaflbgpnehkzlnkqodjlnvyjoxurctvetrreeiuomzmkaxecwdggelhkwdxdnnlgvsbnunkcvhynrfcjnzuejzbphowprbazddokemkojtajonqayrmmbghtjplkliayfbgzyooannlbhrdxhrhhzxqxvzaftlpbzlpmylsgihjkqbdlrkgkqoryebiehzssoptangnjhwkrmvlghkpgsrgmrtmdpwxieccwhtywsivwqmzplgdfhnfulgqqjmflaxanyayfwbgomfmmtqfzreezvgsozxzgimadboqrbqninnncqnjkdurzzyqjxqtxfygcglmhpfmcmtezwxbpcoptkbhquqburbibzlognunlvcoimcgaxrotuqniqjqzktlibzagsmshlbsnqfadegqnxybbmmkoxjfzshdyfbxukykebepbyssdjmumaokfuvbmjbjydpubogexhvzcevwthoxkatejpqwzitdnvbuokbgdkcykpwbqagmlrubyjojdzlflkjsueuatoxizksnlwtzliiuaoyizzqgrrzetthxjlovquwnsqepnnasahdfhtwreqiwzjcqrcjrokjttxocnxgtqwjykmmxfnocsgrqdjsiqdqzgittsapzigvdxsicapqpkfpsevmndwoxqcyugrwwvumtajfljgmvgcfyqakbzksfzktckydmaobvbkmssohifvxulaoihaicycsarkdgirisalddwqyosrufvzgkpnadndyrgfozlkxzqghfohraheecwthgrjqbcnfyczpzbtyvkzuujnnjbteashetqcfwkbbjjtpghwljgewoabjhzvhtxzyqcnryqfkwbqiylfzkhpwyovlkwscqxtshgoalwrkdzvfhgftlevlabhejtdqrhjtiluxwwieayjgqsytwhlooaxynbabiwuoiqjcgrxdasambaqqmaelbjtffcszedavopzmkfomxvzbbbmqgzdapdwclinqytnklslpdoubyequoljczquxmkkkglnmveopanjemmmxiqvsmbcjmviuhyoowudkjktlcfzixpctodpfmgzrzanhmwyhdhbfiuzgcjecnfkwvgrfwavtnydrqyfmmnonxkxowldhluurhoyufxqtnvzhsnxbzkvfewgykjtqbaurtxzrwgkzdiysmxdmuvdfbrgarhighgxxieseztlesxntfewsxsgtzhctjfiejeewzaguklpacbmlfetcvtznsofuylmvyvsyuhtcydkepyqcvnhapnmsddvufnkyqtayxdiirtjdhpazoxtklwrzpwghbwhlkxubxtqyjcahmfvfvxeuaezdybymrvczblzmgfbluowqqxosdhxdigedlxfncctnujsphnhapcjvchylvquvhggroevfpgdlvsqoacffbljmorcaguugtisvquzouowcsgdibbpnmaqhnytenzenlomunnpswuasxpdrpankrjxempfeytnntkidpjmbxnmaovvouheqoabufzffdhkipatckspfohkykgfsdwbsfzxfehalcbixhhlzsypbvdzvdjxyvuddhccxfadgqlhabrcqnmxeuahisnzobnwazjioiknauajkjiwslshhhigidqbfdchpsatupibnnixnytawomkhrdxlivaimyjfzwmoraekifrphlfyyfvwsvcmthfevxybvnovfbxvpyotlxwyxhpnwbjfetfrxyzfchdnizpoutszxwwgylsiyxizpctlrjzenhthszyymagdyoiupllmerrdcmxeehrtnklbuejivehwjvewwumvmqrazpoyuumcszswqbbacjgxcdsbuphcaphvlyearcecwntqkaojhqkowrtpatiizqravprryebvckayvkccrfknzzitussjnwbjizceurinrtlvkjboldpvfmpphfialugsgidecxdnfsutndnsvcaburxtrdmuouftdtnnbjamxwohjijwtbmzfsaduizxbfvjldochycqzvixymycgrtbjlhqlxiswraqejvwrklfgrnweiluhiwkbymzalerldifbduzizgeypxduazpdhrreyfcwjmxlprbjgqpukylqppwgmqnsnbbghuhifaujzrkmtoottahlydphkfwkokswobnrsfnbkolyckdhkkafemxqfohyoyaeyqdgehlmqykgrhjjpkjzvjgbswhrgrewhrvuxsmdagzgcbuzdvgfqimslpcdmynwhjgcijenjdxjeimknimogjfwz",
                        "tm",
                        57136
                },
                {
                        "flvocvebfrymkmttdwajzojfyiztsxrojvtnwneqdrdaxvmguekoyozvujqmzfpimqdqnowlrmaupxliysjirlzycvfmsnuhnofcqbxjgoruohoqxllhpidgkscnstewwdhvxzgbqtfmltrdkqpdfjwjkdxfqhkmtygxpmiuyzhyygymjydydvbktlysagagrzovtumewdoyiowynqlatbjxkabumjcfdsoxjylxcyybzbuesbcduaiptndcxvzsxwmxysdgsnmqdsdehzockvjcgtjpogdewpgqydllerezlikyrraxrfodomjajtinkpaehnnhicztxwmbzvbffghynkqdkklglomtwdirbfmjujsiarruenvtvtfdrpamjglsrkscddanphfnegnirqkxtvdkszigadayhhtfsyvjffiukvylsovodnujjoduobmzpxzknizaameniptkwhhcymitsodalirdjmdrdkowvinkeravxmdmjyyhkadjhwwakezdyljbboeercpomznuwfgzvljutjrclvxdlmgwvapmyyecrtaihkcuporzpaurqlesyhzlldrhwzqbukzfkzxhfxfqsmvcunuomjifjfrupdjetuegkswaktaudwhoiiuaenuuauutozwgdgmwowqybreqngzwzrveyfekixdbzuncvzuzwtuqhizecxnzmcinxhpcywfwtweximesynmzirfmmgxjmmeacpfwxbatbapvqdbtmubwriubqmjdtxyrmtdfuaqenogtcozoiqjshg",
                        "oh",
                        550
                },
                {
                        "mpzmtfoieatrvqpcnehwwyzrjffaforihdbczkjuxurvvllzvlsbxandwxtxrcwmkanopjeqlpznrdxbkjbkcfmhvddkitwkidsuuwjseeafimufexhlzfaeowoosfjvomplnbyycsvuttfitabnjhaqfrgoqrjtbwlhnpiplrpulmglqgimuvlvdauymjkmblfotoiitracfuxybudhrouygtwhwztulqxmsbrxaupdwxsyzetzcapdsfhwdbumjtayvyhwpbnictggfvnwmfqpqdlzrejmgwxueyueiziwqpdfsmastqkolkxsqusufeosmsonnomdbkmwbttcbryoaodupzkeshmehqtopwsjodsxcwtnbtrhblgncvkjyluyqvusrdkiudxlamcxathbgwiuehwdjxlovpabglbojdvfcuzwfztxkjujzzhdngbbqamideppwbbxvyfvolpglktpwqraenepplikomqoomcfxtafsrjrcumaiqvvxpfqlonlvtaofcpxcbokgucnqlmthnjqidonbwatnqykcbjqczwcukehujtkezyxgpvzbalxkcfjvbcxqcselftdsuqtucqwwcdajhkgczmipladzfxdebjzpzepptbqaydfsiqppnudppjskqxvtuouibvettbrmybtlgnlrcozffkvrbksqompjlyhclgnkjkvejtkzocxgedqvqwdoeeeffqpigqqlrhxuwyiisnkjjujzyiwezodprnufgrpbopihktxcykojkaionpvyppefqogupsxpmrqinbitgwcrthogxoeoacjzmeqsimnetewilwzhxllvudncmbcdoggadaeejczepeoljovhrpqbgrikwvbhwhtcwkaznwswifyrknetlbsxiatafdishkdxzpmremvlkcuaasmrpbjbschuwnwqpantmjqzmdalyqnzjlhmuiuyvqurcyxrbzqgrqqendctlvmakx",
                        "vc",
                        837
                },
                {
                        "zigfj",
                        "ju",
                        1
                },
                {
                        "abdcdbc",
                        "ac",
                        4
                },
                {
                        "aabb",
                        "ab",
                        6
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String text, String pattern, long expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected,
                new Solution().maximumSubsequenceCount(text, pattern), timer.stop());
    }

    class Solution {

        public long maximumSubsequenceCount(String text, String pattern) {

            long maxSeq = 0L;

            char pattern0 = pattern.charAt(0);
            char pattern1 = pattern.charAt(1);

            text = text.replaceAll("[^(" + pattern0 + "|" + pattern1 + ")]", "");

            if (pattern0 == pattern1) {
                return ((long) text.length() * (text.length() + 1)) / 2;
            }

            for (int i = 0; i < 2; i++) {
                char patternString = i == 0 ? pattern0 : pattern1;
                for (int j = i; j < text.length() + i; j++) {
                    String tempText = text.substring(0, j) + patternString + text.substring(j);
                    long seq = 0L;

                    long a = 0;
                    for (int k = 0; k < tempText.length(); k++) {
                        if (tempText.charAt(k) == pattern0) {
                            a++;
                        } else {
                            seq += a;
                        }
                    }
                    maxSeq = Math.max(maxSeq, seq);
                }
            }

            return maxSeq;
        }
    }

}
