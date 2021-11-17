package z;

//import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
public class Solution_3_2 {

    public static int solution(int n) {
        Map<String,Integer> cache = new HashMap<>();
        int rt= w(n, n, cache);
        return rt;
    }

    /**
     * @param stepIndex  the step which I want to reach
     * @param upperLimit I can only take step count < upperLimit to reach the stepIndex
     * @param cache      map for caching result
     * @return number of possibility to reach reach stepIndex without upperLimit bound on number of step to take
     */
    static int w(int stepIndex, int upperLimit, Map<String, Integer> cache) {
        String key = "" + stepIndex + ":" + upperLimit;
        Integer cacheValue = cache.get(key);
        if (cacheValue != null) {
            return cacheValue;
        }

        if (upperLimit == 1) return 0;
        if (stepIndex < 0) return 0;
        if (stepIndex == 0 && upperLimit > 0) return 1;
        if (stepIndex == 1 && upperLimit > 1) return 1;

        List<Integer> r = makeRange(upperLimit);

        int rtValue = r
            .stream()
            .map(r_ -> {
                int s_ = stepIndex - r_;
                return w(s_, r_, cache);
            }).mapToInt(e -> e).sum();

        cache.put(key, rtValue);

        return rtValue;
    }

    // make range [1 to x-1]
    static List<Integer> makeRange(int n) {
        if (n < 1) return new ArrayList<>();
        Integer[] r = new Integer[n - 1];
        for (int x = 1; x <= n - 1; ++x) {
            r[x - 1] = x;
        }
        return Arrays.asList(r);
    }
}



//import java.util.HashMap;
//    import java.util.Map;
//    import java.util.List;
//    import java.util.ArrayList;
//    import java.util.Arrays;
//    import java.util.stream.Collectors;
//
//
//public class Solution {
//
//    public static int solution(int n) {
//        return w(n, n, new HashMap<>());
//    }
//
//    static int w(int stepIndex, int upperLimit, Map<String, Integer> cache) {
//        String key = "" + stepIndex + ":" + upperLimit;
//        Integer cacheValue = cache.get(key);
//        if (cacheValue != null) {
//            return cacheValue;
//        }
//
//        if (upperLimit == 1) return 0;
//        if (stepIndex < 0) return 0;
//        if (stepIndex == 0 && upperLimit > 0) return 1;
//        if (stepIndex == 1 && upperLimit > 1) return 1;
//
//        List<Integer> r = makeRange(upperLimit);
//        List<Integer> s = r.stream().map(e -> stepIndex - e).collect(Collectors.toList());
//        List<Integer> rsList = new ArrayList<>();
//        for (int x = 0; x < r.size(); ++x) {
//            rsList.add(w(s.get(x), r.get(x), cache));
//        }
//        int rtValue = rsList.stream().mapToInt(e -> e).sum();
//        cache.put(key, rtValue);
//        return rtValue;
//    }
//
//    // make range [1 to x-1]
//    static List<Integer> makeRange(int n) {
//        if (n < 1) return new ArrayList<>();
//        Integer[] r = new Integer[n - 1];
//        for (int x = 1; x <= n - 1; ++x) {
//            r[x - 1] = x;
//        }
//        return Arrays.asList(r);
//    }
//
//
//}
