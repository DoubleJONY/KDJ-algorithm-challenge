package com.rhino.leetcode.hard;

import java.math.BigInteger;
import java.util.ArrayList;

public class Hard1622_Facny_Sequence {
    //https://ko.wikipedia.org/wiki/%EB%AA%A8%EB%93%88%EB%9F%AC_%EC%82%B0%EC%88%A0
    // 모듈러 산술술}

}

class Fancy {
    private final BigInteger MODULO = BigInteger.valueOf(1_000_000_007);
    private final ArrayList<BigInteger> arr = new ArrayList<>();
    private BigInteger multi = BigInteger.ONE;
    private BigInteger adder = BigInteger.ZERO;
    private BigInteger revA = multi;

    public void append(int val) {
        //https://ko.khanacademy.org/computing/computer-science/cryptography/modarithmetic/a/modular-inverses
        // multi 후 새 값이 들어오면, 역원이 바뀐다.
        if (revA == null) revA = multi.modInverse(MODULO);
        // (val + MOD_VALUE - adder) * revA % MODULO
        arr.add(BigInteger.valueOf(val).add(MODULO).subtract(adder).multiply(revA).mod(MODULO));
    }

    public void addAll(int inc) {
        // (adder+inc) % MODULO
        adder = adder.add(BigInteger.valueOf(inc)).mod(MODULO);
    }

    public void multAll(int m) {
        // multi = multi & m  % MODULO    // 곱연산의 경우
        multi = multi.multiply(BigInteger.valueOf(m)).mod(MODULO);
        // 곱연산을 하면 adder 또한 맞춰서 곱해줘야 함.
        adder = adder.multiply(BigInteger.valueOf(m).mod(MODULO));
        // rev 초기화
        revA = null;
    }

    public int getIndex(int idx) {
        if (idx >= arr.size()) return -1;
        // ( arr[i] * a + b ) % MODULO
        return arr.get(idx).multiply(multi).add(adder).mod(MODULO).intValue();
    }
}
