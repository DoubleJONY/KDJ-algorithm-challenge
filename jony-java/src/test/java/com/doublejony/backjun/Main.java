package com.doublejony.backjun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        List<String> input = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String temp;
        while (!(temp = br.readLine()).isEmpty()) {
            input.add(temp);
        }

        String answer = solution(input.toArray(new String[input.size()]));

        System.out.println(answer);
    }

    public static String solution(String[] input) {
        return input[0];
    }
}
