package z;

import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LimitMatrixTest {
    @org.junit.jupiter.api.Test
    public void t1() throws Exception {
        Solution.MatrixWithState limitMatrix = new Solution.MatrixWithState(
            new Solution.FracMatrix(
                new Solution.Fraction[][]{
                    new Solution.Fraction[]{Solution.Fraction.fn(1), Solution.Fraction.fn(2)},
                    new Solution.Fraction[]{Solution.Fraction.fn(3), Solution.Fraction.fn(4)},
                    new Solution.Fraction[]{Solution.Fraction.fn(4), Solution.Fraction.fn(5)},
                }
            ),
            Arrays.asList(4,8,1),
            Arrays.asList(2,7)
        );

        System.out.println(limitMatrix.get(8,7));
    }

}
