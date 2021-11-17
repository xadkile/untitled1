package z;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MatrixTest {


    @Test
    void transpose() {
        Solution.Matrix m1 = new Solution.Matrix(new int[][]{
            new int[]{1,2,3},
            new int[]{4,5,6},
        });
        Solution.Matrix m2 = m1.transpose();
        System.out.println(m2);
    }

    @Test
    void mul() {
        Solution.Matrix m1 = new Solution.Matrix(new int[][]{
            new int[]{1,2,3},
            new int[]{4,5,6},
        });
        Solution.Matrix m2 = new Solution.Matrix(new int[][]{
            new int[]{-1,1},
            new int[]{-2,2},
            new int[]{-3,3},
        });
        System.out.println(m2.mul(m1));
    }
}
