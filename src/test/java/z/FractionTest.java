package z;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FractionTest {
    @Test
    void composite() {
        Solution.Fraction f1 = new Solution.Fraction(1,2);
        Solution.Fraction f2 = new Solution.Fraction(1,3);
        Solution.Fraction f3 = new Solution.Fraction(9,4);
        Solution.Fraction f4 = f1.add(f2).mul(f3);
        assertEquals(new Solution.Fraction(15,8),f4);


        Solution.Fraction f5 = new Solution.Fraction(0,2);
        Solution.Fraction f6 = new Solution.Fraction(1,3);
        Solution.Fraction f7 = f5.add(f6);
        new Solution.Fraction(1,3).equals(f7);
        assertEquals(new Solution.Fraction(1,3),f7);
    }
    @Test
    void add() {
        Solution.Fraction f1 = new Solution.Fraction(1,2);
        Solution.Fraction f2 = new Solution.Fraction(1,3);
        Solution.Fraction f3 = f1.add(f2);
        assertEquals(new Solution.Fraction(5,6),f3);
    }

    @Test
    void reduce(){
        Solution.Fraction f1 = new Solution.Fraction(7,21);
        Solution.Fraction fe = new Solution.Fraction(1,3);
        assertEquals(fe,f1.reduce());
        Solution.Fraction f2 = new Solution.Fraction(98/2,98);
        Solution.Fraction fe2 = new Solution.Fraction(1,2);
        assertEquals(fe2,f2.reduce());
    }

    @Test
    void mul(){
//        Solution.Fraction f1 = new Solution.Fraction(1,9);
//        Solution.Fraction f2 = new Solution.Fraction(6,4);
//        Solution.Fraction f3 = f1.mul(f2);
//        System.out.println(f3);
//        assertEquals(new Solution.Fraction(1,6),f3);
        Solution.Fraction f1 = new Solution.Fraction(4,9);
        Solution.Fraction f2 = new Solution.Fraction(0,4);
        Solution.Fraction f3 = f1.mul(f2);
        System.out.println(f3.equals(new Solution.Fraction(0,100)));
    }

    @Test
    void subtract(){
        Solution.Fraction f1 = new Solution.Fraction(4,9);
        Solution.Fraction f2 = new Solution.Fraction(1,4);
        Solution.Fraction f3 = f2.subtract(f1);
        System.out.println(f3);
    }


    @Test
    void gcd() {
        assertEquals(6,Solution.gcd(270,192));
    }
}
