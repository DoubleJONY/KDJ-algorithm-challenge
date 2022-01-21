package com.doublejony.leetcode;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static com.doublejony.common.AssertResolve.resolve;


@RunWith(DataProviderRunner.class)
public class Leet1622 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new String[]{"Fancy","append","append","getIndex","append","getIndex","addAll","append","getIndex","getIndex","append","append","getIndex","append","getIndex","append","getIndex","append","getIndex","multAll","addAll","getIndex","append","addAll","getIndex","multAll","getIndex","multAll","addAll","addAll","append","multAll","append","append","append","multAll","getIndex","multAll","multAll","multAll","getIndex","addAll","append","multAll","addAll","addAll","multAll","addAll","addAll","append","append","getIndex"},
                        new Integer[]{null, 12, 8, 1, 12, 0, 12, 8, 2, 2, 4, 13, 4, 12, 6, 11, 1, 10, 2, 3, 1, 6, 14, 5, 6, 12, 3, 12, 15, 6, 7, 8, 13, 15, 15, 10, 9, 12, 12, 9, 9, 9, 9, 4, 8, 11, 15, 9, 1, 4, 10, 9},
                        new Integer[]{null, null, null, 8, null, 12, null, null, 24, 24, null, null, 4, null, 12, null, 20, null, 24, null, null, 37, null, null, 42, null, 360, null, null, null, null, null, null, null, null, null, 220560, null, null, null, 285845760, null, null, null, null, null, null, null, null, null, null, 150746316}
                },
                {
                        new String[]{"Fancy", "append", "addAll", "append", "multAll", "getIndex", "addAll", "append", "multAll", "getIndex", "getIndex", "getIndex"},
                        new Integer[]{null, 2, 3, 7, 2, 0, 3, 10, 2, 0, 1, 2},
                        new Integer[]{null, null, null, null, null, 10, null, null, null, 26, 34, 20}
                },
                {
                        new String[]{"Fancy","append","getIndex","getIndex","getIndex","addAll","getIndex","getIndex","multAll","append","append","append","addAll","append","addAll","getIndex"},
                        new Integer[]{null, 6, 0, 4, 0, 2, 0, 0, 10, 5, 6, 7, 8, 3, 4, 1},
                        new Integer[]{null, null, 6, -1, 6, null, 8, 8, null, null, null, null, null, null, null, 17}
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] commands, Integer[] inputs, Integer[] expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Leet1622.Solution().solution(commands, inputs), timer.stop());
    }

    class Solution {
        public Integer[] solution(String[] commands, Integer[] inputs) {

            Fancy fancy = null;
            List<Integer> result = new ArrayList<>();

            for (int i = 0; i < commands.length; i++) {
                String command = commands[i];
                switch (command) {
                    case "Fancy":
                        fancy = new Fancy();
                        result.add(null);
                        break;
                    case "append":
                        fancy.append(inputs[i]);
                        result.add(null);
                        break;
                    case "addAll":
                        fancy.addAll(inputs[i]);
                        result.add(null);
                        break;
                    case "multAll":
                        fancy.multAll(inputs[i]);
                        result.add(null);
                        break;
                    case "getIndex":
                        result.add(fancy.getIndex(inputs[i]));
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + command);
                }
            }

            return result.toArray(new Integer[0]);
        }
    }

    class Fancy {

        private final BigInteger MOD = BigInteger.valueOf(1000000007);

        int length = 0;
        BigInteger[] list;

        public Fancy() {
            this.list = new BigInteger[]{BigInteger.valueOf(-1)};
        }

        public void append(int val) {
            BigInteger[] tempList = new BigInteger[length+1];
            System.arraycopy(list, 0, tempList, 0, list.length);
            tempList[length] = BigInteger.valueOf(val).mod(MOD);
            list = tempList;
            length++;
        }

        public void addAll(int inc) {
            for (int i = 0; i < length; i++) {
                list[i] = list[i].add(BigInteger.valueOf(inc)).mod(MOD);
            }
        }

        public void multAll(int m) {
            for (int i = 0; i < length; i++) {
                list[i] = list[i].multiply(BigInteger.valueOf(m)).mod(MOD);
            }
        }

        public Integer getIndex(int idx) {
            try {
                return list[idx].intValue();
            } catch (ArrayIndexOutOfBoundsException e) {
                return -1;
            }
        }
    }
}
