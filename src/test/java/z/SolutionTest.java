package z;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    void z(){
        int[][] m = new int[][]{
          new int[]{0,1,0,0,0,1},
          new int[]{4,0,0,3,2,0},
          new int[]{0,0,0,0,0,0},
          new int[]{0,0,0,0,0,0},
          new int[]{0,0,0,0,0,0},
          new int[]{0,0,0,0,0,0},
        };
//        int[][] m = new int[][]{
//            new int[]{0,1,0,0,1},
//            new int[]{4,0,3,2,0},
//            new int[]{0,0,0,0,0},
//            new int[]{0,0,0,0,0},
//            new int[]{0,0,0,0,0},
//        };
        Solution.InputHandler handler = new Solution.InputHandler(m);
        int iterativeCount = handler.originalSum();
        iterativeCount =11;
        Solution.FracMatrix matrix = handler.readyForUse();
        Solution.FracMatrix s0 = handler.getS0().transpose();
        Solution.FracMatrix z = matrix;
        for(int x=0;x<iterativeCount;++x){
            z = z.mul(matrix);
        }
        Solution.FracMatrix z2 = z.mul(s0);
        System.out.println(z2);
    }

    @Test
    void qwe2(){
//        int[][] m = new int[][]{
//            new int[]{0,1,0,0,0,1},
//            new int[]{4,0,0,3,2,0},
//            new int[]{0,0,0,0,0,0},
//            new int[]{0,0,0,0,0,0},
//            new int[]{0,0,0,0,0,0},
//            new int[]{0,0,0,0,0,0},
//        };

//        int[][] m = new int[][]{
//            new int[]{0,2,1,0,0},
//            new int[]{0,0,0,3,4},
//            new int[]{0,0,0,0,0},
//            new int[]{0,0,0,0,0},
//            new int[]{0,0,0,0,0},
//        };
        int[][] m = new int[][]{
            new int[]{0,},
        };

        int[]rt = Solution.solution(m);
        System.out.println("enddd");
    }
    @Test
    void qwe3(){
        int[][] m = new int[][]{
            new int[]{0,1,},
            new int[]{4,0,},
        };
        int[][] m2 = new int[][]{
            new int[]{9,3,},
            new int[]{0,2,},
        };
        Solution.InputHandler handler = new Solution.InputHandler(m);
        Solution.InputHandler handler2 = new Solution.InputHandler(m2);
        System.out.println(handler2.makeOriginalFracMatrix().subtract(handler.makeOriginalFracMatrix()));

        System.out.println("enddd");
    }

    @Test
    void lcdF() {
//        assertEquals(8,Solution.lcdF(8,2));
//        assertEquals(21,Solution.lcdF(7,3));
//        assertEquals(7*6,Solution.lcdF(6,7));

    }

    @Test
    void gcd() {
        assertEquals(6,Solution.gcd(270,192));
    }

    @Test
    void qwe() {
        Solution.FracMatrix  matrix = new Solution.FracMatrix(new Solution.Fraction[][]{
            new Solution.Fraction[]{new Solution.Fraction(3,4), new Solution.Fraction(4,10)},
            new Solution.Fraction[]{new Solution.Fraction(1,4), new Solution.Fraction(6,10)}
        });

        Solution.FracMatrix  s0 = new Solution.FracMatrix(new Solution.Fraction[][]{
            new Solution.Fraction[]{Solution.Fraction.ONE},
            new Solution.Fraction[]{Solution.Fraction.ZERO}
        });
        Solution.FracMatrix m = matrix;
        for(int x=0;x<2;++x){
            m=m.mul(matrix);
        }
        System.out.println(m);
        System.out.println(m.mul(s0));


    }
}
