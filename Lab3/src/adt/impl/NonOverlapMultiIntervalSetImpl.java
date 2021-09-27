package adt.impl;

import adt.Interval;
import adt.MultiIntervalSet;
import adt.interfa.NonOverlapMultiIntervalSet;
import tools.IntervalSetTools;
import tools.IntervalTools;

import java.util.HashSet;
import java.util.Set;

public class NonOverlapMultiIntervalSetImpl<L> implements NonOverlapMultiIntervalSet<L> {

    private final boolean flag;
    private final MultiIntervalSet<L> multiIntervalSet;

    // Abstraction function:
    // AF(flag) = 是否允许有重叠部分的标志
    // AF(multiIntervalSet) = 需要检验的multiIntervalSet
    // Representation invariant:
    // Safety from rep exposure:

    /**
     * 构造器
     * @param flag 是否允许有重叠部分的标志
     * @param multiIntervalSet 需要检验的multiIntervalSet
     */
    public NonOverlapMultiIntervalSetImpl(boolean flag, MultiIntervalSet<L> multiIntervalSet) {
        this.flag = flag;
        this.multiIntervalSet = multiIntervalSet;
    }

    @Override
    public void insert(long start, long end, L label) {
        Set<Interval> temp = new HashSet<>();
        temp.add(new Interval(start,end));
        Set<Interval> allIntervals = IntervalSetTools.allIntervals(this.multiIntervalSet);
        if(multiIntervalSet.labels().contains(label)) //允许重叠是针对不同标签，同一个标签内不允许有重叠
        {
            Set<Interval> partIntervals = IntervalSetTools.allIntervals(multiIntervalSet.intervals(label));
            if(!IntervalTools.intersection(partIntervals,temp).isEmpty())
                throw new RuntimeException(label + "中有重叠时间段");
        }
        if(!flag)
        {
            if(!IntervalTools.intersection(allIntervals,temp).isEmpty())
                throw new RuntimeException("重叠时间段");
        }
        multiIntervalSet.insert(start,end,label);
    }
}
