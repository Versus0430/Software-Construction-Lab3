package adt.impl;

import adt.Interval;
import adt.MultiIntervalSet;
import adt.interfa.NonBlankMultiIntervalSet;
import tools.IntervalSetTools;
import tools.IntervalTools;

import java.util.HashSet;
import java.util.Set;

public class NonBlankMultiIntervalSetImpl<L> implements NonBlankMultiIntervalSet<L> {

    private final Interval time;
    private final MultiIntervalSet<L> multiIntervalSet;

    // Abstraction function:
    // AF(time) = 要检查的时间段
    // AF(intervalSet) = 要检查的intervalSet
    // Representation invariant:
    // Safety from rep exposure:

    /**
     * 构造器
     * @param start 要检查的时间段的开始时间
     * @param end 要检查的时间段的结束时间
     * @param multiIntervalSet 要检查的multiIntervalSet
     */
    public NonBlankMultiIntervalSetImpl(long start, long end, MultiIntervalSet<L> multiIntervalSet) {
        this.time = new Interval(start, end);
        this.multiIntervalSet = multiIntervalSet;
    }

    @Override
    public boolean checkNoBlank() {
        Set<Interval> set = new HashSet<>();
        set.add(time);
        Set<Interval> allIntervals = IntervalSetTools.allIntervals(multiIntervalSet);
        Set<Interval> intersection = IntervalTools.intersection(allIntervals,set);
        return intersection.equals(set);
    }

    @Override
    public Set<Interval> getBlank() {
        Set<Interval> blank = new HashSet<>();
        if(checkNoBlank())
            return blank;
        return IntervalTools.differenceSet(IntervalSetTools.allIntervals(multiIntervalSet),time);
    }
}
