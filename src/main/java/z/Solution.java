package z;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Solution {
    public static int[] solution(int[][] m) {
        Solution.InputHandler handler = new Solution.InputHandler(m);
        Solution.FracMatrix matrix = handler.readyForUse();
        Solution.ReArrangeResult reArrange = matrix.reArrange();
        Solution.FracMatrix Q = reArrange.Q;
//        Solution.FracMatrix R = reArrange.Q;
        Solution.FracMatrix F = Solution.FracMatrix.I(Q.rowCount()).subtract(Q).inverse();
        Solution.FracMatrix FR = F.mul(reArrange.R);
        // make Limit
        int terminalCount = reArrange.I.rowCount();
        int nonTerminalCount = reArrange.Q.rowCount();
        Solution.FracMatrix IO = Solution.FracMatrix.O(terminalCount, nonTerminalCount).concat(reArrange.I);
        Solution.FracMatrix FRO = Solution.FracMatrix.O(nonTerminalCount, nonTerminalCount).concat(FR);
        Solution.FracMatrix limitMatrix = FRO.stack(IO);
        List<Integer> terminalStates = FracMatrix.getTerminalState(handler.makeOriginalFracMatrix());
        Fraction[] s0RowUnfiltered = limitMatrix.getRow(0);
        List<Solution.Fraction> s0Row = new ArrayList<>();
        for(int x=0;x<s0RowUnfiltered.length;++x){
            if(terminalStates.contains(x)){
                s0Row.add(s0RowUnfiltered[x]);
            }
        }

        List<Integer> denominators = s0Row.stream().map(e -> e.denominator).collect(Collectors.toList());
        int maxDenominator = denominators.stream().mapToInt(e -> e).max().getAsInt();

        List<Integer> numerators = s0Row.stream().map(e -> {
            if (e.denominator == maxDenominator) {
                return e.numerator;
            } else {
                return e.numerator * maxDenominator / e.denominator;
            }
        }).collect(Collectors.toList());
        numerators.add(maxDenominator);
        int[] rt = numerators.stream().mapToInt(i -> i).toArray();
        return rt;
    }


    static class GaussJordanTransformation {
        public static GJTransResult makeTransformation(FracMatrix input) {
            FracMatrix rt = FracMatrix.I(input.rowCount());
            FracMatrix iterMatrix = input.copy();

            for (int c = 0; c < input.colCount(); ++c) {
                FracMatrix tempMatrix = FracMatrix.I(input.rowCount());
                Fraction diagElement = iterMatrix.get(c, c);
                // eliminate below diag
                if (diagElement.isNotZero()) {
                    for (int r = c + 1; r < input.rowCount(); ++r) {
                        Fraction numer = iterMatrix.get(r, c);
                        Fraction multiplier = numer.div(diagElement).negate();
                        tempMatrix.set(r, c, multiplier);
                    }
                }

                // eliminate above diag
                if (diagElement.isNotZero()) {
                    for (int r = 0; r < c; ++r) {
                        Fraction numer = iterMatrix.get(r, c);
                        Fraction multiplier = numer.div(diagElement).negate();
                        tempMatrix.set(r, c, multiplier);
                    }
                }

                iterMatrix = tempMatrix.mul(iterMatrix);
                rt = (tempMatrix).mul(rt);
            }
            return new GJTransResult(rt, iterMatrix);
        }
    }

    static class GJTransResult {
        FracMatrix transMatrix;
        FracMatrix resultingMatrix; //=transMatrix * inputMatrix

        public GJTransResult(FracMatrix transMatrix, FracMatrix resultingMatrix) {
            this.transMatrix = transMatrix;
            this.resultingMatrix = resultingMatrix;
        }
    }

    static class InputHandler {
        int[][] originalInput;
        int size;

        public InputHandler(int[][] originalInput) {
            this.originalInput = originalInput;
            this.size = this.originalInput.length;
        }

        int originalSum() {
            int sum = 0;
            for (int x = 0; x < this.size; ++x) {
                for (int y = 0; y < this.size; ++y) {
                    sum += originalInput[x][y];
                }
            }
            return sum;
        }

        /**
         * convert an int matrix into a fraction matrix
         */
        FracMatrix toFractionMatrixOverSum(int[][] input) {
            Fraction[][] fractions = new Fraction[size][];
            for (int x = 0; x < size; ++x) {
                fractions[x] = this.convertRowToFracOverSum(input[x]);
            }
            FracMatrix rt = new FracMatrix(fractions);
            return rt;
        }

        /**
         * convert an int row into a fraction row
         * each element / sum
         */
        private Fraction[] convertRowToFracOverSum(int[] row) {
            int sum = Arrays.stream(row).sum();
            if (sum != 0) {
                return Arrays.stream(row).mapToObj(e -> new Fraction(e, sum))
                    .toArray(Fraction[]::new);
            } else {
                return Arrays.stream(row).mapToObj(e -> Fraction.ZERO)
                    .toArray(Fraction[]::new);
            }

        }

        FracMatrix makeOriginalFracMatrix() {
            return this.toFractionMatrixOverSum(this.originalInput);
        }

        // add one, and transposed
        FracMatrix readyForUse() {
            return this.toFractionMatrixOverSum(this.originalInput).fillOneVertical();
        }

        /**
         * generate the init state
         */
        FracMatrix getS0() {
            Fraction[][] ff = new Fraction[1][this.size];
            ff[0][0] = Fraction.ONE;
            for (int x = 1; x < this.size; ++x) {
                ff[0][x] = Fraction.ZERO;
            }
            return new FracMatrix(ff);
        }
    }

    static class LimitMatrix {
        FracMatrix fracMatrix;

        public LimitMatrix(FracMatrix fracMatrix) {
            this.fracMatrix = fracMatrix;
        }

        Fraction get(int fromState, int toState) {
            return Fraction.ZERO;
        }
    }

    static class FracMatrix {
        Fraction[][] rows;

        public FracMatrix(Fraction[][] rows) {
            this.rows = rows;
        }

        static FracMatrix I(int size) {
            Fraction[][] I = new Fraction[size][size];
            for (int r = 0; r < size; ++r) {
                for (int c = 0; c < size; ++c) {
                    if (r == c) {
                        I[r][c] = Fraction.ONE;
                    } else {
                        I[r][c] = Fraction.ZERO;
                    }
                }
            }
            return new FracMatrix(I);
        }

        static FracMatrix O(int rowCount, int colCount) {
            Fraction[][] I = new Fraction[rowCount][colCount];
            for (int r = 0; r < rowCount; ++r) {
                for (int c = 0; c < colCount; ++c) {
                    I[r][c] = Fraction.ZERO;
                }
            }
            return new FracMatrix(I);
        }

        public FracMatrix inverse() {
            GJTransResult result = GaussJordanTransformation.makeTransformation(this);
            Solution.FracMatrix trans = result.transMatrix;
            Solution.FracMatrix resultingMatrix = result.resultingMatrix;
            Solution.FracMatrix inversedValueResultingMAtrix = resultingMatrix.inverseValue();
            Solution.FracMatrix inv = inversedValueResultingMAtrix.mul(trans);
            return inv;
        }

        public FracMatrix inverseValue() {
            FracMatrix copy = this.copy();
            for (int r = 0; r < copy.rowCount(); ++r) {
                Fraction inverse = copy.get(r, r).inverse();
                copy.set(r, r, inverse);
            }
            return copy;
        }

        public FracMatrix replaceRow(int i, Fraction[] newRow) {
            FracMatrix copy = this.copy();
            copy.rows[i] = newRow;
            return copy;
        }

        public FracMatrix subtract(FracMatrix another) {
            FracMatrix copy = this.copy();
            for (int r = 0; r < this.rowCount(); ++r) {
                for (int c = 0; c < this.colCount(); ++c) {
                    copy.set(r, c, this.get(r, c).subtract(another.get(r, c)));
                }
            }
            return copy;
        }

        public void set(int r, int c, Fraction value) {
            this.rows[r][c] = value;
        }

        Fraction[] getRow(int r) {
            return this.rows[r];
        }


        static public List<Integer> getTerminalState(FracMatrix input){
            List<Integer> terminalStates = new ArrayList<>();
            for (int r = 0; r < input.rowCount(); ++r) {
                if (input.isZeroRow(input.getRow(r))) {
                    terminalStates.add(r);
                }
            }
            return terminalStates;
        }
        static public List<Integer> getNonTerminalState(FracMatrix input){
            List<Integer> nonTerminalStates = new ArrayList<>();
            for (int r = 0; r < input.rowCount(); ++r) {
                if (!input.isZeroRow(input.getRow(r))) {
                    nonTerminalStates.add(r);
                }
            }
            return nonTerminalStates;
        }

        public ReArrangeResult reArrange() {
            FracMatrix input = this;
            List<Integer> terminalStates = new ArrayList<>();
            List<Integer> nonTerminalStates = new ArrayList<>();
            for (int r = 0; r < input.rowCount(); ++r) {
                if (this.checkTerminalRow(input.getRow(r), r)) {
                    terminalStates.add(r);
                } else {
                    nonTerminalStates.add(r);
                }
            }
            int terminalCount = terminalStates.size();
            int nonTermCount = nonTerminalStates.size();

            //make R
            Fraction[][] R = new Fraction[nonTermCount][terminalCount];
            for (int r = nonTermCount - 1; r >= 0; --r) {
                int fromState = nonTerminalStates.get(r);
                for (int c = 0; c < terminalCount; ++c) {
                    int toState = terminalStates.get(c);
                    R[r][c] = input.get(fromState, toState);
                }
            }
            // make Q
            Fraction[][] Q = new Fraction[nonTermCount][nonTermCount];
            for (int r = 0; r < nonTermCount; ++r) {
                int fromState = nonTerminalStates.get(r);
                for (int c = 0; c < nonTermCount; ++c) {
                    int toState = nonTerminalStates.get(c);
                    Q[r][c] = input.get(fromState, toState);
                }

            }

            // make I
            Fraction[][] I = I(terminalCount).rows;

            //make O
            Fraction[][] O = new Fraction[terminalCount][nonTermCount];
            for (int r = 0; r < terminalCount; ++r) {
                for (int c = 0; c < nonTermCount; ++c) {
                    O[r][c] = Fraction.ZERO;
                }
            }

            // I|O
            Fraction[][] IO = new Fraction[terminalCount][terminalCount];
            for (int x = 0; x < terminalCount; ++x) {
                IO[x] = concatRow(I[x], O[x]);
            }
            // RQ
            Fraction[][] RQ = new Fraction[nonTermCount][terminalCount];
            for (int x = 0; x < nonTermCount; ++x) {
                RQ[x] = concatRow(R[x], Q[x]);
            }
            // I|O
            // R|Q

            Fraction[][] iorq = new Fraction[input.rowCount()][input.rowCount()];
            for (int x = 0; x < IO.length; ++x) {
                iorq[x] = IO[x];
            }

            for (int x = IO.length; x < input.rowCount(); ++x) {
                iorq[x] = RQ[x - IO.length];
            }
            FracMatrix all = new FracMatrix(iorq);
            FracMatrix Im = new FracMatrix(I);
            FracMatrix Om = new FracMatrix(O);
            FracMatrix Rm = new FracMatrix(R);
            FracMatrix Qm = new FracMatrix(Q);
            return new ReArrangeResult(Om, Im, Rm, Qm, all);
        }

        public FracMatrix concat(FracMatrix another) {
            Fraction[][] rows = new Fraction[this.rowCount()][this.colCount() + another.colCount()];
            for (int r = 0; r < this.rowCount(); ++r) {
                Fraction[] thisRow = this.getRow(r);
                Fraction[] anotherRow = another.getRow(r);
                Fraction[] concatRow = concatRow(thisRow, anotherRow);
                rows[r] = concatRow;
            }
            return new FracMatrix(rows);
        }

        public FracMatrix stack(FracMatrix another) {
            FracMatrix tt = this.transpose();
            FracMatrix anotherTrans = another.transpose();
            return tt.concat(anotherTrans).transpose();
        }

        /**
         * concat 2 row r1,r2 to r1-r2
         */
        public static Fraction[] concatRow(Fraction[] r1, Fraction[] r2) {
            Fraction[] rt = new Fraction[r1.length + r2.length];
            for (int x = 0; x < r1.length; ++x) {
                rt[x] = r1[x];
            }

            for (int x = r1.length; x < r2.length + r1.length; ++x) {
                rt[x] = r2[x - r1.length];
            }
            return rt;
        }

        Fraction get(int r, int c) {
            return this.rows[r][c];
        }

//        List<Fraction[]> getTerminalState() {
//            return Arrays.stream(this.rows).filter(this::checkTerminalRow).collect(Collectors.toList());
//        }
//
//        private Fraction[] inverseRow(Fraction[] row) {
//            Fraction[] newRow = new Fraction[row.length];
//            for (int x = row.length - 1; x >= 0; --x) {
//                int i = row.length - 1 - x;
//                newRow[i] = row[x];
//            }
//            return newRow;
//        }


        FracMatrix fillOneVertical() {
            Fraction[][] r2 = this.copy().rows;

            for (int r = 0; r < rows.length; r++) {
                if (isZeroRow(r2[r])) {
                    r2[r][r] = Fraction.ONE;
                }
            }
            return new FracMatrix(r2);
        }

        boolean checkTerminalRow(Fraction[] row, int rowIndex) {
            boolean diagIsOne = row[rowIndex].equals(Fraction.ONE);
            boolean theRestIsZero = true;
            for (int x = 0; x < row.length; ++x) {
                if (x != rowIndex) {
                    if (false == row[x].equals(Fraction.ZERO)) {
                        theRestIsZero = false;
                    }
                }
            }
            return diagIsOne && theRestIsZero;
        }

        boolean isZeroRow(Fraction[] row) {
            return Arrays.stream(row).allMatch(e -> e.equals(Fraction.ZERO));
        }

        FracMatrix copy() {
            Fraction[][] r2 = new Fraction[rows.length][rows[0].length];
            for (int x = 0; x < rows.length; x++) {
                for (int y = 0; y < rows[0].length; ++y) {
                    r2[x][y] = this.rows[x][y];
                }
            }
            return new FracMatrix(r2);
        }

        FracMatrix transpose() {
            int rc = rows.length;
            int cc = rows[0].length;
            Fraction[][] rt = new Fraction[cc][rc];

            for (int r = 0; r < cc; ++r) {
                for (int c = 0; c < rc; ++c) {
                    rt[r][c] = rows[c][r];
                }
            }
            return new FracMatrix(rt);
        }

        @Override
        public String toString() {
            return Arrays.stream(this.rows).map((Fraction[] row) -> {
                    return "[ " + Arrays.stream(row).map(Fraction::toString)
                        .collect(Collectors.joining(", ")) + "]";
                }
            ).collect(Collectors.joining("\n"));
        }

        FracMatrix mul(FracMatrix another) {
            if (this.colCount() != another.rowCount()) {
                throw new IllegalArgumentException(String.format("this col count = %s, another row count = %s", this.colCount(), another.rowCount()));
            } else {
                FracMatrix anotherTransposed = another.transpose();
                Fraction[][] rtRows = new Fraction[this.rowCount()][another.colCount()];
                for (int r = 0; r < this.rowCount(); ++r) {
                    Fraction[] thisRow = this.rows[r];
                    for (int ar = 0; ar < anotherTransposed.rowCount(); ++ar) {
                        Fraction[] anotherRow = anotherTransposed.rows[ar];
                        rtRows[r][ar] = mulArr(thisRow, anotherRow);
                    }
                }

                return new FracMatrix(rtRows);
            }
        }

        static Fraction mulArr(Fraction[] arr1, Fraction[] arr2) {

            if (arr1.length == arr2.length) {
                Fraction rt = new Fraction(0, 1);
                for (int x = 0; x < arr1.length; x++) {
                    rt = rt.add(arr1[x].mul(arr2[x]));
                }
                return rt;
            } else {
                throw new IllegalArgumentException("must be same size arr");
            }
        }

        int rowCount() {
            return this.rows.length;
        }

        int colCount() {
            return this.rows[0].length;
        }

    }

    static class ReArrangeResult {
        public FracMatrix R;
        public FracMatrix Q;
        public FracMatrix O;
        public FracMatrix I;
        public FracMatrix all;

        public ReArrangeResult(FracMatrix o, FracMatrix i, FracMatrix r, FracMatrix q, FracMatrix all) {
            R = r;
            Q = q;
            O = o;
            I = i;
            this.all = all;
        }
    }

//    static class FracRow{
//        List<Fraction> elements;
//
//        public FracRow(List<Fraction> elements) {
//            this.elements = elements;
//        }
//
//        public FracRow(Fraction[] elements) {
//            this.elements = Arrays.asList(elements);
//        }
//
//        private FracRow reverse(){
//            List<Fraction> newRow = new ArrayList<>();
//            for(int x=this.elements.size()-1;x>=0;--x){
//                int i = this.elements.size()-1-x;
//                newRow.set(i,this.elements.get(x));
//            }
//            return new FracRow(newRow);
//        }
//    }

    static class Matrix {
        int[][] rows;

        public Matrix(int[][] rows) {
            this.rows = rows;
        }

        Matrix transpose() {
            int rc = rows.length;
            int cc = rows[0].length;
            int[][] rt = new int[cc][rc];

            for (int r = 0; r < cc; ++r) {
                for (int c = 0; c < rc; ++c) {
                    rt[r][c] = rows[c][r];
                }
            }
            return new Matrix(rt);
        }

        @Override
        public String toString() {
            return Arrays.stream(this.rows).map((int[] row) -> {
                    return "[ " + Arrays.stream(row).mapToObj((int e) -> {
                        return "" + e;
                    }).collect(Collectors.joining(", ")) + "]";
                }
            ).collect(Collectors.joining("\n"));
        }

        Matrix mul(Matrix another) {
            if (this.colCount() != another.rowCount()) {
                throw new IllegalArgumentException(String.format("this col count = %s, another row count = %s", this.colCount(), another.rowCount()));
            } else {
                Matrix anotherTransposed = another.transpose();
                int[][] rtRows = new int[this.rowCount()][another.colCount()];
                for (int r = 0; r < this.rowCount(); ++r) {
                    int[] thisRow = this.rows[r];
                    for (int ar = 0; ar < anotherTransposed.rowCount(); ++ar) {
                        int[] anotherRow = anotherTransposed.rows[ar];
                        rtRows[r][ar] = mulArr(thisRow, anotherRow);
                    }
                }

                return new Matrix(rtRows);
            }
        }

        static int mulArr(int[] arr1, int[] arr2) {

            if (arr1.length == arr2.length) {
                int rt = 0;
                for (int x = 0; x < arr1.length; x++) {
                    rt = rt + (arr1[x] * arr2[x]);
                }
                return rt;
            } else {
                throw new IllegalArgumentException("must be same size arr");
            }
        }

        int rowCount() {
            return this.rows.length;
        }

        int colCount() {
            return this.rows[0].length;
        }

    }

    static class Fraction {
        int numerator;
        int denominator;

        static Fraction ONE = new Fraction(1, 1);
        static Fraction ZERO = new Fraction(0, 1);

        public Fraction(int numerator, int denominator) {
            if (denominator == 0) {
                throw new IllegalArgumentException("denominator must not be 0");
            }
            this.numerator = numerator;
            this.denominator = denominator;
        }

        public static Fraction fn(int num) {
            return new Fraction(num, 1);
        }

        boolean isNotZero() {
            return this.numerator != 0;
        }

        boolean isZero() {
            return this.numerator == 0;
        }

        Fraction inverse() {
            return new Fraction(this.denominator, this.numerator);
        }

        Fraction add(Fraction another) {
            if (this.denominator == another.denominator) {
                return new Fraction(this.numerator + another.numerator, this.denominator);
            } else {
                int lcd = lcdF(this.denominator, another.denominator);
                return new Fraction(this.numerator * lcd / this.denominator + another.numerator * lcd / another.denominator, lcd).reduce();
            }
        }

        Fraction negate() {
            return new Fraction(-this.numerator, this.denominator);
        }

        Fraction subtract(Fraction another) {
            Fraction opositeAnother = new Fraction(-another.numerator, another.denominator);
            return this.add(opositeAnother);
        }

        Fraction mul(Fraction another) {
            Fraction tr = this.reduce();
            Fraction ar = another.reduce();
            Fraction rt = new Fraction(tr.numerator * ar.numerator, tr.denominator * ar.denominator).reduce();
            return rt;
        }

        Fraction div(Fraction another) {
            return new Fraction(this.numerator * another.denominator, this.denominator * another.numerator).reduce();
        }


        Fraction reduce() {
            int gcd = Solution.gcd(this.denominator, this.numerator);
            return new Fraction(this.numerator / gcd, this.denominator / gcd);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.numerator, this.denominator);
        }

        @Override
        public String toString() {
            return String.format("(%s | %s)", this.numerator, this.denominator);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Fraction) {
                Fraction fraction = (Fraction) obj;
                boolean c1 = ((((long) this.numerator * fraction.denominator)) == (((long) fraction.numerator) * this.denominator));
                return c1;
            } else {
                return false;
            }
        }
    }

    /**
     * least common denominator
     */
    static int lcdF(int d1, int d2) {
        return d1 * d2 / gcd(d1, d2);
    }

    /**
     * greatest common divisor
     */
    static int gcd(int a, int b) {
        if (a == 0) return b;
        if (b == 0) return a;
        int r = a % b;
        return gcd(b, r);
    }
}
