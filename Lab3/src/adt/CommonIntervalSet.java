package adt;

import tools.IntervalSetTools;

import java.util.*;
import java.util.stream.Collectors;

public class CommonIntervalSet<L> implements IntervalSet<L> {

    private final Map<L,Interval> labels = new HashMap<>();

    // Abstraction function:
    //   AF(labels) = 标签集合
    // Representation invariant:
    // Safety from rep exposure:
    //   设置labels为private的
    //   由于labels是mutable的，所以返回标签集时进行了defensive copies

    /**
     * 初始化构造
     */
    public CommonIntervalSet() {
    }

    @Override
    public void insert(long start, long end, L label) {
        Interval i = new Interval(start,end);
        labels.put(label,i);
    }

    @Override
    public Set<L> labels() {
        return new HashSet<>(labels.keySet());
    }

    @Override
    public boolean remove(L label) {
        if(labels.containsKey(label))
        {
            labels.remove(label);
            return true;
        }
        return false;
    }

    @Override
    public long start(L label) {
        long start = 0;
        for(L l:labels.keySet())
        {
            if(l.equals(label))
            {
                start = labels.get(l).getStart();
            }
        }
        return start;
    }

    @Override
    public long end(L label) {
        long end = 0;
        for(L l:labels.keySet())
        {
            if(l.equals(label))
            {
                end = labels.get(l).getEnd();
            }
        }
        return end;
    }

    @Override
    public String toString(){
        StringBuilder re = new StringBuilder();
        re.append("{ ");
        for(L l:labels.keySet())
        {
            re.append(l).append("=").append(labels.get(l).toString()).append(" ");
        }
        re.append("}");
        return re.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonIntervalSet<?> that = (CommonIntervalSet<?>) o;
        return labels.equals(that.labels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(labels);
    }

    /**
     * 返回一个IntervalSet对象中的时间冲突比例
     * 冲突是指同一时间段类安排了两个不同的interval对象
     * 用发生冲突的时间段总长度除以总长度，得到冲突比例
     * @param set 一个IntervalSet对象
     * @return 一个IntervalSet对象中的时间冲突比例
     */
    double calcConflictRatio(IntervalSet<L> set){
        double conflictProportion = 0;
        long length = 0;
        List<Interval> s = IntervalSetTools.allIntervals(set).stream().sorted().collect(Collectors.toList());
        for(int i = 0; i<s.size(); i++)
            length += s.get(i).getEnd() - s.get(i).getStart();
        Interval completeWorks = new Interval(s.get(0).getStart(),s.get(s.size() - 1).getEnd());
        conflictProportion = IntervalSetTools.calCnoflictRatio(set,completeWorks,length);
        return conflictProportion;
    }

    /**
     * 返回一个IntervalSet对象中的空闲时间比例
     * 空闲时间指的是某段时间内没有安排任何Interval对象
     * 用空闲时间段总长度除以总长度，得到空闲时间比例
     * @param set 一个IntervalSet对象
     * @return 一个IntervalSet对象中的空闲时间比例
     */
    double calcFreeTimeRatio(IntervalSet<L> set){
        double freeTimeProportion = 0;
        List<Interval> s = IntervalSetTools.allIntervals(set).stream().sorted().collect(Collectors.toList());
        Interval completeWorks = new Interval(s.get(0).getStart(),s.get(s.size() - 1).getEnd());
        freeTimeProportion = IntervalSetTools.calcFreeTimeRatio(set,completeWorks);
        return freeTimeProportion;
    }
}
