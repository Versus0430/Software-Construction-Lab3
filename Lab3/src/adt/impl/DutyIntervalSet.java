package adt.impl;

import adt.CommonIntervalSet;
import adt.Employee;
import adt.Interval;
import adt.IntervalSet;
import adt.interfa.IDutyIntervalSet;

import java.util.*;

public class DutyIntervalSet<L> extends CommonIntervalSet<L> implements IDutyIntervalSet<L> {
    private final NonBlankIntervalSetImpl<L> nbis ;
    private final NonOverlapIntervalSetImpl<L> nois;
    private final IntervalSet<L> intervalSet;

    public DutyIntervalSet(long start, long end) {
        this.intervalSet = new CommonIntervalSet<>();
        this.nbis = new NonBlankIntervalSetImpl<>(start,end,intervalSet);
        this.nois = new NonOverlapIntervalSetImpl<>(false,intervalSet);
    }

    @Override
    public boolean checkNoBlank() {
        return nbis.checkNoBlank();
    }

    @Override
    public Set<Interval> getBlank() {
        return nbis.getBlank();
    }

    @Override
    public void insert(long start, long end, L label) {
        nois.insert(start, end, label);
    }

    @Override
    public Set<L> labels() {
        return intervalSet.labels();
    }

    @Override
    public boolean remove(L label){
        return intervalSet.remove(label);
    }

    @Override
    public long start(L label) {
        return intervalSet.start(label);
    }

    @Override
    public long end(L label){
        return intervalSet.end(label);
    }

    @Override
    public String toString(){
        return intervalSet.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DutyIntervalSet<?> that = (DutyIntervalSet<?>) o;
        return Objects.equals(nbis, that.nbis) && Objects.equals(nois, that.nois) && Objects.equals(intervalSet, that.intervalSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nbis, nois, intervalSet);
    }
}
