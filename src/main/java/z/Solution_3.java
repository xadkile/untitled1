package z;

import java.math.BigInteger;

public class Solution_3 {
    public static String solution(long x, long y) {
        BigInteger xb = BigInteger.valueOf(x);
        BigInteger yb = BigInteger.valueOf(y);
        // b in: y = -x + b
        BigInteger b = xb.add(yb);
        BigInteger beforeCut = b.subtract(BigInteger.valueOf(2));
        BigInteger refPoint = BigInteger.ZERO;
        for (BigInteger iterator = BigInteger.ONE;
             iterator.compareTo(beforeCut) <= 0;
             iterator = iterator.add(BigInteger.ONE)) {
            refPoint = refPoint.add(iterator);
        }
        BigInteger rt = refPoint.add(xb);
        return rt.toString();
    }
}
