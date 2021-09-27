package adt.impl;

import adt.Interval;
import adt.IntervalSet;
import adt.interfa.NonBlankIntervalSet;
import tools.IntervalSetTools;
import tools.IntervalTools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NonBlankIntervalSetImpl<L> implements NonBlankIntervalSet<L> {

    private final Interval time;
    private final IntervalSet<L> intervalSet;

    // Abstraction function:
    // AF(time) = 要检查的时间段
    // AF(intervalSet) = 要检查的intervalSet
    // Representation invariant:
    // Safety from rep exposure:

    /**
     * 构造器
     * @param start 要检查的时间段的开始时间
     * @param end 要检查的时间段的结束时间
     * @param intervalSet 要检查的intervalSet
     */
    public NonBlankIntervalSetImpl(long start, long end, IntervalSet<L> intervalSet) {
        this.intervalSet = intervalSet;
        this.time = new Interval(start,end);
    }

    @Override
    public boolean checkNoBlank() {
        Set<Interval> set = new HashSet<>();
        set.add(time);
        Set<Interval> allIntervals = IntervalSetTools.allIntervals(intervalSet);
        Set<Interval> intersection = IntervalTools.intersection(allIntervals,set);
        return set.equals(intersection);
    }

    @Override
    public Set<Interval> getBlank() {
        Set<Interval> blank = new HashSet<>();
        if(checkNoBlank()) {
            System.out.println("1");
            return blank;
        }
        return IntervalTools.differenceSet(IntervalSetTools.allIntervals(intervalSet),time);
    }

}
