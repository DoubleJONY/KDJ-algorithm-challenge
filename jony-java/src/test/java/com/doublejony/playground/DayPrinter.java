package com.doublejony.playground;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.doublejony.common.AssertResolve.resolve;


@RunWith(DataProviderRunner.class)
public class DayPrinter {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        1,
                        new String[]{
                                "월요일",
                                "화요일",
                                "수요일",
                                "목요일",
                                "금요일"
                        }
                },
                {
                        2,
                        new String[]{
                                "토요일",
                                "일요일"
                        }
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(int input, String[] expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new DayPrinter.Main().solution(input), timer.stop());
    }

    public class Main {

        public String[] solution(int input) {

            return input == DayDic.WORKING_DAYS ? DayDic.getKNamesWithWorkingDay() : DayDic.getKNamesWithWeekend();
        }
    }
}
