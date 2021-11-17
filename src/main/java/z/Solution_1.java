package z;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Solution_1 {

    // string of prime: 235711... (2,3,5,7,11)
    // pick an index [i], then get the substring at [i,i+5]
    // i in [0,10k]
    private static Picker picker = new Picker();
    public static String solution(int i) {
       return picker.pick(i);
    }

    static class Picker {
        public String cachedStr = "";
        private final int startingIndex = -1;
        public int lastLargestIndex = startingIndex;
        private final int step = 1;


        String pick(int minionIndex) {
            while (needToGenerate(minionIndex)) {
                lastLargestIndex += step;
                this.cachedStr = this.cachedStr + primeStr(lastLargestIndex);
            }
            return cachedStr.substring(minionIndex,minionIndex+5);
        }

        private boolean needToGenerate(int minionIndex) {
            return cachedStr.length() < minionIndex + 5;
        }

        public static String primeStr(int n)  {
            List<Integer> list = getPrime(n);
            String rt = list.stream().map(Object::toString).collect(Collectors.joining(""));
            return rt;
        }


        /**
         * 6n +/- 1
         */
        public static List<Integer> getPrime(int n) {
            List<Integer> rt = new ArrayList<>();
            if (n < 0) {
                throw new RuntimeException("n must be from 1");
            }
            if (n == 0) {
                rt.add(2);
            } else if (n == 1) {
                rt.add(3);
            } else {
                int plus = 6 * (n - 1) + 1;
                int minus = 6 * (n - 1) - 1;
                if (isPrime(minus)) rt.add(minus);
                if (isPrime(plus)) rt.add(plus);
            }
            return rt;
        }

        public static boolean isPrime(int number) {

            for (int i = 2; i * i <= number; i++) {
                if (number % i == 0) {
                    return false;
                }
            }
            return true;
        }
    }
}
