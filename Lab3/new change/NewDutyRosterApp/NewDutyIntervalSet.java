package NewDutyRosterApp;

import adt.*;
import adt.impl.NonBlankMultiIntervalSetImpl;
import adt.impl.NonOverlapMultiIntervalSetImpl;

import java.util.Objects;
import java.util.Set;

public class NewDutyIntervalSet<L> extends CommonMultiIntervalSet<L> implements NewIDutyIntervalSet<L>{

    private final NonBlankMultiIntervalSetImpl<L> nbis ;
    private final NonOverlapMultiIntervalSetImpl<L> nois;
    private final MultiIntervalSet<L> multiIntervalSet;

    public NewDutyIntervalSet(long start, long end) {
        this.multiIntervalSet = new CommonMultiIntervalSet<>();
        this.nbis = new NonBlankMultiIntervalSetImpl<>(start,end,multiIntervalSet);
        this.nois = new NonOverlapMultiIntervalSetImpl<>(false,multiIntervalSet);
    }

    public boolean checkNoBlank() {
        return nbis.checkNoBlank();
    }

    public Set<Interval> getBlank() {
        return nbis.getBlank();
    }

    @Override
    public void insert(long start, long end, L label) {
        nois.insert(start, end, label);
    }

    @Override
    public Set<L> labels(){ return multiIntervalSet.labels();}

    @Override
    public boolean remove(L label){ return multiIntervalSet.remove(label);}

    @Override
    public IntervalSet<Integer> intervals(L label){ return multiIntervalSet.intervals(label);}

    @Override
    public String toString() { return multiIntervalSet.toString();}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewDutyIntervalSet<?> that = (NewDutyIntervalSet<?>) o;
        return Objects.equals(nbis, that.nbis) && Objects.equals(nois, that.nois) && Objects.equals(multiIntervalSet, that.multiIntervalSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nbis, nois, multiIntervalSet);
    }
}
