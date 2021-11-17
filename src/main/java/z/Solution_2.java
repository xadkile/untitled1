package z;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution_2 {
    public static String solution(int[] xs) {

        if(xs.length==1){
            return BigInteger.valueOf(xs[0]).toString();
        }

        if(xs.length==0){
            return BigInteger.ZERO.toString();
        }

        List<Integer> negativeNumbers = new ArrayList<>();
        BigInteger positiveProduct = BigInteger.ZERO;

        for (int x : xs) {
            if (x > 0) {
                if (positiveProduct.equals(BigInteger.ZERO)) {
                    positiveProduct = BigInteger.ONE;
                }
                positiveProduct = positiveProduct.multiply(BigInteger.valueOf(x));
            }
            if (x < 0) {
                negativeNumbers.add(x);
            }
        }

        BigInteger negativeProduct = BigInteger.ZERO;
        if (!negativeNumbers.isEmpty()) {
            // remove the biggest negative number if there's an odd number of negative number
            List<Integer> sortedNegative = negativeNumbers.stream()
                .sorted()
                .collect(Collectors.toList());

            int lastIndex = sortedNegative.size();
                if (negativeNumbers.size() % 2 != 0) {
                    lastIndex = lastIndex - 1;
                }

            List<Integer> subList = sortedNegative.subList(0, lastIndex);
            if (!subList.isEmpty()) {
                negativeProduct = subList
                    .stream()
                    .map(BigInteger::valueOf)
                    .reduce(BigInteger.ONE, BigInteger::multiply);
            }
        }

        List<BigInteger> rt = Stream.of(positiveProduct, negativeProduct)
            .filter(e -> !e.equals(BigInteger.ZERO))
            .collect(Collectors.toList());
        if (rt.isEmpty()) {
            return BigInteger.ZERO.toString();
        } else {
            return rt.stream().reduce(BigInteger.ONE, BigInteger::multiply).toString();
        }
    }
}
