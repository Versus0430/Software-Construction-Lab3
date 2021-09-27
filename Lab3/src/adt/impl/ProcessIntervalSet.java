package adt.impl;

import adt.CommonMultiIntervalSet;
import adt.IntervalSet;
import adt.MultiIntervalSet;
import adt.impl.NonOverlapMultiIntervalSetImpl;
import adt.interfa.IProcessIntervalSet;
import adt.interfa.NonOverlapMultiIntervalSet;

import java.util.Objects;
import java.util.Set;

public class ProcessIntervalSet<L> extends CommonMultiIntervalSet<L> implements IProcessIntervalSet<L> {

    private final NonOverlapMultiIntervalSet<L> nois;
    private final MultiIntervalSet<L> multiIntervalSet;

    /**
     * 构造器
     */
    public ProcessIntervalSet() {
        this.multiIntervalSet = new CommonMultiIntervalSet<>();
        this.nois = new NonOverlapMultiIntervalSetImpl<>(false,multiIntervalSet);
    }

    @Override
    public void insert(long start,long end,L label){
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
        ProcessIntervalSet<?> that = (ProcessIntervalSet<?>) o;
        return Objects.equals(nois, that.nois) && Objects.equals(multiIntervalSet, that.multiIntervalSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nois, multiIntervalSet);
    }
}
