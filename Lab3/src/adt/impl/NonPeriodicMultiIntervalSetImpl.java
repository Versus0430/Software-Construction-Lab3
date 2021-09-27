package adt.impl;

import adt.Interval;
import adt.MultiIntervalSet;
import adt.interfa.NonPeriodicMultiIntervalSet;

public class NonPeriodicMultiIntervalSetImpl<L> implements NonPeriodicMultiIntervalSet<L> {

    private final long period;
    private final long periodStart;
    private final Interval firstPeriod;
    private final MultiIntervalSet<L> multiIntervalSet;

    // Abstraction function:
    // AF(period) = 周期长度
    // AF(periodStart) = 周期开始时间
    // AF(firstPeriod) = 一个周期的时间区间
    // AF(multiIntervalSet) = 需要检验的multiIntervalSet
    // Representation invariant:
    // Safety from rep exposure:

    /**
     * 构造器
     * @param period 周期长度
     * @param periodStart 周期开始时间
     * @param multiIntervalSet 需要检验的multiIntervalSet
     */
    public NonPeriodicMultiIntervalSetImpl(long period, long periodStart, MultiIntervalSet<L> multiIntervalSet) {
        this.period = period;
        this.periodStart = periodStart;
        this.firstPeriod = new Interval(periodStart,periodStart+period);
        this.multiIntervalSet = multiIntervalSet;
    }

    @Override
    public long getPeriod() {
        return period;
    }

    @Override
    public long getPeriodStart() {
        return periodStart;
    }

    @Override
    public void insert(long start, long end, L label) {
        Interval temp = new Interval(start,end);
        if(!(start >= firstPeriod.getStart()) && !(end <= firstPeriod.getEnd())) //若不在第一个周期的范围中
        {
            throw new RuntimeException("(" + start + "," + end + ") 不在第一个周期范围中");
        }
        multiIntervalSet.insert(start,end,label);
    }
}
