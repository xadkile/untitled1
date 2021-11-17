package z;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class NumPair implements NumComb {
    Integer first;
    Integer second;

    public NumPair(Integer first, Integer second) {
        this.first = first;
        this.second = second;
    }

    @NotNull
    public Integer getFirst() {
        return first;
    }

    @Override
    public Integer getLast() {
        return this.getSecond();
    }

    @Override
    public Boolean isAcceptable() {
        return this.first < this.second;
    }

    @Nullable
    public Integer getSecond() {
        return second;
    }


    public Num getFirstAsNum(){
        return new Num(this.first);
    }

    public Num getSecondAsNum(){
        return new Num(this.second);
    }

    public List<NumPair> splitSecond() {
        return Solution_3_1.sequentialSplit(this.second);
    }

    public boolean hasSecond() {
        return this.second != null;
    }

    @NotNull
    public Integer getComparativeRear() {
        if (this.hasSecond()) return this.second;
        else return this.first;
    }

    public Boolean canBeCombineWith(NumPair another) {
        return this.getComparativeRear() < another.getFirst();
    }

    @Override
    public List<Integer> flat(){
        return Arrays.asList(this.first,this.second);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NumComb) {
            NumComb co = (NumComb) obj;
            return this.flat().equals(co.flat());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("(%s=%s)", this.first, this.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.flat());
    }
}
