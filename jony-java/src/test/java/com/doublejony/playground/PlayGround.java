package com.doublejony.playground;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.doublejony.common.AssertResolve.resolve;


@RunWith(DataProviderRunner.class)
public class PlayGround {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        "Hello World!",
                        "Hello World!"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String input, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new PlayGround.Main().solution(input), timer.stop());
    }

    public class Main {

        public String solution(String input) {

            List<String> list1 = Stream.of("1", "2", "3", "4", "5", "6").map(a -> a + ",").collect(Collectors.toList());
            list1.forEach(System.out::println);

            return "Hello World";
        }
    }
}
