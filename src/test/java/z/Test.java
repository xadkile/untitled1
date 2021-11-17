package z;

import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test {
    @org.junit.jupiter.api.Test
    public void t1() throws Exception {
        Solution_1.Picker picker = new Solution_1.Picker();
        assertEquals("71113", Solution_1.solution(3));
        assertEquals("23571", Solution_1.solution(0));
        System.out.println(picker.pick(10000));
        System.out.println(picker.lastLargestIndex);
        System.out.println(picker.cachedStr.length());
    }

    @org.junit.jupiter.api.Test
    public void t2() throws Exception {
        assertEquals("8", Solution_2.solution(new int[]{0, 2, 0, 2, 2, 0}));
        assertEquals("0", Solution_2.solution(new int[]{0}));
        assertEquals("0", Solution_2.solution(new int[]{}));
        assertEquals("0", Solution_2.solution(new int[]{-5, 0}));
        assertEquals("-5", Solution_2.solution(new int[]{-5}));
        assertEquals("10", Solution_2.solution(new int[]{-5, -2, 0}));
        assertEquals("495", Solution_2.solution(new int[]{-5, -2, 0, 0, 1, -99}));
        assertEquals("2", Solution_2.solution(new int[]{-5, 2, 0}));
        assertEquals("6", Solution_2.solution(new int[]{-5, 2, 0, 3}));
        assertEquals("30", Solution_2.solution(new int[]{5, 2, 0, 3}));
        assertEquals("10", Solution_2.solution(new int[]{5, 2, 0}));
        assertEquals("0", Solution_2.solution(new int[]{0, 0, 0, -1}));
        assertEquals("0", Solution_2.solution(new int[]{0, 0, 0, -1}));
    }

    @org.junit.jupiter.api.Test
    public void t22() {
        assertEquals("19", Solution_3.solution(4, 3));
        assertEquals("10", Solution_3.solution(4, 1));
        assertEquals("5", Solution_3.solution(2, 2));
        assertEquals("1", Solution_3.solution(1, 1));
        assertEquals("16", Solution_3.solution(1, 6));
    }

    @org.junit.jupiter.api.Test
    public void t31() {
        assertEquals(1, Solution_3_1.solution(3));
        assertEquals(1, Solution_3_1.solution(4));
        assertEquals(2, Solution_3_1.solution(5));
        assertEquals(2, Solution_3_1.solution(7));
        assertEquals(3, Solution_3_1.solution(8));
        assertEquals(3, Solution_3_1.solution(9));
        assertEquals(8, Solution_3_1.solution(10));
        assertEquals(487067745, Solution_3_1.solution(200));
    }

    @org.junit.jupiter.api.Test
    public void zzzZ() {


//        assertEquals(0,Solution.solution(1));
//        assertEquals(0,Solution.solution(2));
//        assertEquals(1,Solution.solution(3));
//        assertEquals(1,Solution.solution(4));
//        assertEquals(2,Solution.solution(5));
//        assertEquals(3,Solution.solution(6));
//        assertEquals(4,Solution.solution(7));
//        assertEquals(5,Solution.solution(8));
//        assertEquals(7,Solution.solution(9));
//        assertEquals(9, Solution.solution(10));
        System.out.println(Solution_3_1.solution(30));
//        assertEquals(487067745,Solution.solution(200));
//        System.out.println(Solution.sequentialSplit(8));
//        System.out.println(Solution.sequentialSplit(7));
//        System.out.println(Solution.sequentialSplit(6));
//        System.out.println(Solution.sequentialSplit(5));
//        System.out.println(Solution.sequentialSplit(4));
//        int n =6;
//        Set<NumComb> set = Solution.solutionX(n);
//        set.stream().map(e->e.getLast()).sorted().map(e->n-e).forEach(System.out::println);
//        set.forEach(System.out::println);
    }

    Pair<Integer, List<Pair<Integer, Integer>>>
    countSplitMore(int num, int accumCount, Map<Integer, Integer> cache, int startNum) {
        return new Pair<>(1,new ArrayList<>());
    }
    @org.junit.jupiter.api.Test
    public void splitSequential(){
        Solution_3_1.sequentialSplit(7).forEach(System.out::println);
        System.out.println("==");
        Solution_3_1.sequentialSplit(10).forEach(System.out::println);
    }


    @org.junit.jupiter.api.Test
    public void sp(){
//        assertEquals(487067745,Solution.solution(200));
//        assertEquals(0,Solution.solution(0));
//        assertEquals(0,Solution.solution(1));
//        assertEquals(0,Solution.solution(2));
//        assertEquals(1,Solution.solution(3));
//        assertEquals(1,Solution.solution(4));
//        assertEquals(2,Solution.solution(5));
//        assertEquals(3,Solution.solution(6));
//        assertEquals(4,Solution.solution(7));
//        assertEquals(5,Solution.solution(8));
//        assertEquals(7,Solution.solution(9));
//        assertEquals(9,Solution.solution(10));
        assertEquals(487067745, Solution_3_2.solution(200));

    }

    int solution(int n){
        if(n<=2 && n>=0) return 0;
        return w(n,n,new HashMap<>());
    }


    int w(int stepIndex, int upperLimit, Map<Pair<Integer,Integer>,Integer>cache){
        Integer cacheValue = cache.get(new Pair<>(stepIndex,upperLimit));
        if(cacheValue!=null){
            return cacheValue;
        }

        if(upperLimit ==1) return 0;
        if(stepIndex<0) return 0;
        if(stepIndex==0) return 1;
        if(stepIndex==1 && upperLimit>1) return 1;

        List<Integer> r = makeRange(upperLimit);
        List<Integer> s = r.stream().map(e -> stepIndex-e).collect(Collectors.toList());
        List<Integer> rsList = new ArrayList<>();
        for(int x =0 ;x < r.size();++x){
            rsList.add(w(s.get(x),r.get(x), cache));
        }
        int rtValue = rsList.stream().mapToInt(e->e).sum();
        cache.put(new Pair<>(stepIndex,upperLimit),rtValue);
        return rtValue;
    }

    // 1 -> x-1
    List<Integer> makeRange(int n){
        if(n<1) return new ArrayList<>();
        Integer[] r = new Integer[n-1];
        for(int x = 1;x<=n-1;++x){
            r[x-1]=x;
        }
        return Arrays.asList(r);
    }
}
