package tools;

import adt.CommonIntervalSet;
import adt.Interval;
import adt.IntervalSet;
import adt.MultiIntervalSet;

import java.util.HashSet;
import java.util.Set;

public class IntervalSetTools {
    /**
     * 返回IntervalSet类型的set中的所有的intervals
     * @param set IntervalSet类型的集合set
     * @param <L> 泛型
     * @return IntervalSet类型的set中的所有的intervals
     */
    public static <L> Set<Interval> allIntervals(IntervalSet<L> set){
        Set<Interval> res = new HashSet<>();
        if(set != null)
        {
            for(L l: set.labels())
             {
                res.add(new Interval(set.start(l), set.end(l)));
             }
        }
        return res;
    }

    /**
     * 返回MultiIntervalSet类型的set中的所有的intervals
     * @param set MultiIntervalSet类型的set
     * @param <L> 泛型
     * @return MultiIntervalSet类型的set中的所有的intervals
     */
    public static <L> Set<Interval> allIntervals(MultiIntervalSet<L> set){
        Set<Interval> res = new HashSet<>();
        IntervalSet<Integer> temp = new CommonIntervalSet<>();
        for(L l:set.labels())
        {
            temp = set.intervals(l);
            if(temp != null) {
                for (Integer m : temp.labels()) {
                    res.add(new Interval(set.intervals(l).start(m), set.intervals(l).end(m)));
                }
            }
        }
        return res;
    }

    /**
     * 计算空闲时间比例
     * @param set IntervalSet对象
     * @param completeWorks 完整时间段
     * @param <L> 泛型
     * @return 空闲时间比例
     */
    public static <L> double calcFreeTimeRatio(IntervalSet<L> set, Interval completeWorks) {
        long length = completeWorks.getEnd() - completeWorks.getStart();
        return 1.0 * IntervalTools.length(IntervalTools.differenceSet(allIntervals(set), completeWorks)) / length;
    }

    /**
     * 计算空闲时间比例
     * @param set MultiIntervalSet对象
     * @param completeWorks 完整时间段
     * @param <L> 泛型
     * @return 空闲时间比例
     */
    public static <L> double calcFreeTimeRatio(MultiIntervalSet<L> set, Interval completeWorks) {
        long length = completeWorks.getEnd() - completeWorks.getStart();
        return 1.0 * IntervalTools.length(IntervalTools.differenceSet(allIntervals(set), completeWorks)) / length;
    }

    /**
     * 计算冲突比例
     * @param set IntervalSet对象
     * @param completeWorks 完整时间段
     * @param <L> 泛型
     * @return 冲突比例
     */
    public static <L> double calCnoflictRatio(IntervalSet<L> set, Interval completeWorks,long length){
        Set<Interval> s = new HashSet<>();
        s.add(completeWorks);
        return 1.0 * (length - IntervalTools.length(IntervalTools.intersection(allIntervals(set), s))) / length;
    }

    /**
     * 计算冲突比例
     * @param set MultiIntervalSet对象
     * @param completeWorks 完整时间段
     * @param <L> 泛型
     * @return 冲突比例
     */
    public static  <L> double calcConflictRatio(MultiIntervalSet<L> set, Interval completeWorks,long length){
        Set<Interval> s = new HashSet<>();
        s.add(completeWorks);
        return 1.0 * (length - IntervalTools.length(IntervalTools.intersection(allIntervals(set), s))) / length;
    }
}
