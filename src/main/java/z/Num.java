package z;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Num implements NumComb{
    Integer i;

    public Num(Integer i) {
        this.i = i;
    }

    public Integer getNum() {
        return i;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof NumComb){
            return this.flat().equals(((NumComb) obj).flat());
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.i);
    }

    @Override
    public Integer getFirst() {
        return this.getNum();
    }

    @Override
    public Integer getLast() {
        return getNum();
    }

    @Override
    public Boolean isAcceptable() {
        return true;
    }

    @Override
    public List<Integer> flat() {
        return Arrays.asList(this.i);
    }

    @Override
    public String toString() {
        return String.format("(%s)", this.i);
    }
}
