package adt.impl;

import adt.Interval;
import adt.IntervalSet;
import adt.interfa.NonOverlapIntervalSet;
import tools.IntervalSetTools;
import tools.IntervalTools;

import java.util.HashSet;
import java.util.Set;

public class NonOverlapIntervalSetImpl<L> implements NonOverlapIntervalSet<L> {
    private final boolean flag;
    private final IntervalSet<L> intervalSet;

    // Abstraction function:
    // AF(flag) = 是否允许有重叠部分的标志
    // AF(intervalSet) = 需要检验的intervalSet
    // Representation invariant:
    // Safety from rep exposure:

    /**
     * 构造器
     * @param flag 是否允许有重叠部分的标志
     * @param intervalSet 需要检验的intervalSet
     */
    public NonOverlapIntervalSetImpl(boolean flag, IntervalSet<L> intervalSet) {
        this.flag = flag;
        this.intervalSet = intervalSet;
    }

    @Override
    public void insert(long start, long end, L label) {
        Set<Interval> temp = new HashSet<>();
        temp.add(new Interval(start,end));
        if(!flag)
        {
            Set<Interval> allIntervals = IntervalSetTools.allIntervals(intervalSet);
            if(!IntervalTools.intersection(allIntervals,temp).isEmpty()) //有重叠部分
            {
                throw new RuntimeException("重叠时间段");
            }
        }
        intervalSet.insert(start,end,label);
    }
}
