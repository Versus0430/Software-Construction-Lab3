package adt.impl;

import adt.CommonMultiIntervalSet;
import adt.IntervalSet;
import adt.MultiIntervalSet;
import adt.interfa.ICourseIntervalSet;
import adt.interfa.NonOverlapMultiIntervalSet;
import adt.interfa.NonPeriodicMultiIntervalSet;

import java.util.Objects;
import java.util.Set;

public class CourseIntervalSet<L> extends CommonMultiIntervalSet<L> implements ICourseIntervalSet<L> {

    private final NonOverlapMultiIntervalSet<L> nois;
    private final NonPeriodicMultiIntervalSet<L> npis;
    private final int week_num;
    private final MultiIntervalSet<L> multiIntervalSet;

    public static final long period = 7L * 24L;

    public CourseIntervalSet(long periodStart, int week_num) {
        this.multiIntervalSet = new CommonMultiIntervalSet<>();
        this.nois = new NonOverlapMultiIntervalSetImpl<>(true,multiIntervalSet);
        this.npis = new NonPeriodicMultiIntervalSetImpl<>(period,periodStart,multiIntervalSet);
        this.week_num = week_num;
    }

    /**
     * 返回星期数
     * @return 星期数
     */
    public int getWeek_num(){ return week_num;}

    @Override
    public long getPeriod() {
        return npis.getPeriod();
    }

    @Override
    public long getPeriodStart() {
        return npis.getPeriodStart();
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
        CourseIntervalSet<?> that = (CourseIntervalSet<?>) o;
        return week_num == that.week_num && Objects.equals(nois, that.nois) && Objects.equals(npis, that.npis) && Objects.equals(multiIntervalSet, that.multiIntervalSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nois, npis, week_num, multiIntervalSet);
    }
}
