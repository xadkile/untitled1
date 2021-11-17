package z;

import org.junit.jupiter.api.Test;

public class GaussJordanTransformationTest {
    @Test
    void tt() {
        Solution.FracMatrix matrix = new Solution.FracMatrix(
            new Solution.Fraction[][]{
                new Solution.Fraction[]{Solution.Fraction.fn(-2), Solution.Fraction.fn(2), Solution.Fraction.fn(-5)},
                new Solution.Fraction[]{Solution.Fraction.fn(2), Solution.Fraction.fn(-3), Solution.Fraction.fn(7)},
                new Solution.Fraction[]{Solution.Fraction.fn(-4), Solution.Fraction.fn(3), Solution.Fraction.fn(-7)},
            }
        );
        System.out.println(matrix.inverse().mul(matrix));
    }
}
