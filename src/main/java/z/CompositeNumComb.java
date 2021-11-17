package z;

import com.sun.istack.internal.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompositeNumComb implements NumComb {
    @NotNull
    private NumComb first;
    @NotNull
    private NumComb second;

    public CompositeNumComb(@NotNull NumComb first, @NotNull NumComb second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof NumComb){
            NumComb node = (NumComb) obj;
            return this.flat().equals(node.flat()) ;
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.flat());
    }

    @Override
    public String toString() {
        return String.format("(%s = %s)",this.first.toString(),this.second.toString());
    }

    @Override
    public Integer getFirst() {
        return this.first.getFirst();
    }

    @Override
    public Integer getLast() {
        return this.second.getLast();
    }

    @Override
    public Boolean isAcceptable() {
        boolean okJoin= this.first.getLast() < this.second.getFirst();
//        return okJoin && this.first.isAcceptable() && this.second.isAcceptable();
        return okJoin;
    }

    @Override
    public List<Integer> flat() {
        return Stream.of(this.first.flat(), this.second.flat()).flatMap(e->e.stream()).collect(Collectors.toList());
    }
}
