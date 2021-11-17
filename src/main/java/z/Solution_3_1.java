package z;

import java.util.*;
import java.util.stream.Collectors;

public class Solution_3_1 {


    public static int solution(int n) {
        HashMap<Integer, Set<NumComb>> cache = new HashMap<>();
        Set<NumComb> set = solutionRecursive(n,cache);
        set.forEach(System.out::println);
        return set.size();
    }
    public static Set<NumComb> solutionX(int n) {
        HashMap<Integer, Set<NumComb>> cache = new HashMap<>();
        Set<NumComb> set = solutionRecursive(n,cache);
        return set;
    }
    public static Set<NumComb> solutionRecursive(int n,HashMap<Integer, Set<NumComb>> cache ) {
        Set<NumComb> cachedResult = cache.get(n);
        if(cachedResult!=null){
            return cachedResult;
        }

        Set<NumComb> accumSet = solutionOneLayer(n,cache);
        List<NumPair> combList = sequentialSplit(n);
        for(NumPair p : combList){
            List<NumComb> z = new ArrayList<>(solutionRecursive(p.getFirst(),cache));
            z.add(p.getFirstAsNum());
            Set<NumComb> firstCombList = new HashSet<>(z);
            for(NumComb fc :firstCombList){
                CompositeNumComb comb = new CompositeNumComb(fc,p.getSecondAsNum());
                if(comb.isAcceptable()){
                    accumSet.add(comb);
                }
            }

            List<NumComb> z2 = new ArrayList<>(solutionRecursive(p.getSecond(),cache));
            z2.add(p.getSecondAsNum());
            Set<NumComb> secondCombList = new HashSet<>(z2);
            for(NumComb sc :secondCombList){
                CompositeNumComb comb = new CompositeNumComb(p.getFirstAsNum(),sc);
                if(comb.isAcceptable()){
                    accumSet.add(comb);
                }
            }
        }
        cache.put(n,accumSet);
        return accumSet;
    }

    public static Set<NumComb> solutionOneLayer(int n,HashMap<Integer, Set<NumComb>> cache ) {
        List<NumPair> combList = Solution_3_1.sequentialSplit(n);
        Set<NumComb> accumSet = combList.stream().filter(NumPair::isAcceptable).map(i->(NumComb)i).collect(Collectors.toSet());
        return accumSet;
    }


    static List<NumComb> checkAndGet(Integer num, HashMap<Integer, List<NumComb>> cache) {
        List<NumComb> cachedNds = cache.get(num);
        if (cachedNds != null) {
            return cachedNds;
        } else {
            return Solution_3_1.sequentialSplit(num).stream().map(e -> (NumComb) e).collect(Collectors.toList());
        }
    }

    public static void blindAddToCache(Integer num, List<NumComb> addition, HashMap<Integer, List<NumComb>> cache) {
        List<NumComb> list = cache.getOrDefault(num, new ArrayList<>());
        list.addAll(addition);
    }

    public static void addUniqueToCache(Integer num, List<NumComb> addition, HashMap<Integer, List<NumComb>> cache) {
        List<NumComb> list = cache.getOrDefault(num, new ArrayList<>());
        list.addAll(addition);
        Set<NumComb> set = new HashSet<>(list);
        cache.put(num, new ArrayList<>(set));
    }


    public static List<NumComb> filterOk(List<NumComb> pairList) {
        return pairList.stream()
            .filter(NumComb::isAcceptable)
            .collect(Collectors.toList());
    }

    public static List<NumComb> inclusiveSplit(int num){
        List<NumComb> rt = new ArrayList<>(Arrays.asList(new Num(num)));
        List<NumPair> firstSplit = Solution_3_1.sequentialSplit(num);
        rt.addAll(firstSplit);
        return rt;
    }

    public static List<NumPair> sequentialSplit(int num) {
        int stopPoint = num / 2 + 1;
        List<NumPair> rt2 = new ArrayList<>();
        for (int x = 1; x < stopPoint; ++x) {
            rt2.add(new NumPair(x, num - x));
        }
        return rt2;
    }


//    public static Set<NumComb> solutionRecursive(int n,HashMap<Integer, List<NumComb>> cache ) {
//        Set<NumComb> accumSet = solutionOneLayer(n,cache);
//        List<NumPair> combList = sequentialSplit(n);
//        for(NumPair p : combList){
//            List<NumComb> firstCombList = inclusiveSplit(p.getFirst());
//            Num secondNum = p.getSecondAsNum();
//            for(NumComb fc :firstCombList){
//                CompositeNumComb comb = new CompositeNumComb(fc,secondNum);
//                if(comb.isAcceptable()){
//                    accumSet.add(comb);
//                }
//            }
//            List<NumComb> secondCombList = inclusiveSplit(p.getSecond());
//            for(NumComb sc : secondCombList){
//                CompositeNumComb comb = new CompositeNumComb(p.getFirstAsNum(),sc);
//                if(comb.isAcceptable()){
//                    accumSet.add(comb);
//                }
//            }
//        }
//        return accumSet;
//    }

}
