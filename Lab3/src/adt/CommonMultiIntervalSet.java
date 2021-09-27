package adt;

import tools.IntervalSetTools;
import tools.IntervalTools;

import java.util.*;
import java.util.stream.Collectors;

public class CommonMultiIntervalSet<L> implements MultiIntervalSet<L> {

    private final Map<L, IntervalSet<Integer>> map = new HashMap<>();

    // Abstraction function:
    //   AF(map) = 标签与其对应的时间段的映射Map
    // Representation invariant:
    //   标签对应的时间段不为空
    // Safety from rep exposure:
    //   设置map为private的

    /**
     * 初始化构造
     */
    public CommonMultiIntervalSet() {
    }

    /**
     *检查RI
     */
    public void checkRep(){
        for(L l: map.keySet())
        {
            assert map.get(l).end(0) != 0;
        }
    }

    @Override
    public void MultiIntervalSet(IntervalSet<L> initial) {
        initial.labels().forEach(label->{
            IntervalSet<Integer> set = new CommonIntervalSet<>();
            set.insert(initial.start(label),initial.end(label),0);
            map.put(label,set);
        });
    }

    @Override
    public void insert(long start, long end, L label) {
        if(map.containsKey(label))
        {
            map.get(label).insert(start,end,map.get(label).labels().size());
        }
        else
        {
            IntervalSet<Integer> set = new CommonIntervalSet<>();
            set.insert(start,end,0);
            map.put(label,set);
            checkRep();
        }
    }

    @Override
    public Set<L> labels() {
        Set<L> s = new HashSet<>();
        for(L l:map.keySet())
            s.add(l);
        return s;
    }

    @Override
    public boolean remove(L label) {
        if(map.containsKey(label))
        {
            map.remove(label);
            checkRep();
            return true;
        }
        return false;
    }

    @Override
    public IntervalSet<Integer> intervals(L label) {
        IntervalSet<Integer> s = map.get(label);
        if(s == null)
            return null;
        List<Interval> list = new ArrayList<>();
        for(Integer l:s.labels())
        {
            Interval n = new Interval(s.start(l),s.end(l));
            list.add(l,n);
        }

        Collections.sort(list, new Comparator<Interval>() {

            @Override
            public int compare(Interval o1, Interval o2) {
                int i =(int) o1.getStart() -(int) o2.getStart();
                return i;
            }
        });
        IntervalSet<Integer> ret = new CommonIntervalSet<>();
        for(int i=0; i<list.size(); i++)
            ret.insert(list.get(i).getStart(),list.get(i).getEnd(),(Integer) i);
        return ret;
    }

    @Override
    public String toString() {
        return "CommonMultiIntervalSet{" +
                "map=" + map +
                '}';
    }

    /**
     * 计算两个MultiIntervalSet对象的相似度
     * 对于同一时间段的两个对象，若标注的label等价，则二者相似度为1，否则为0
     * 对于同一时间段中只有一个对象或者二者都没有，则相似度为0
     * @param s1 MultiIntervalSet对象1
     * @param s2 MultiIntervalSet对象2
     * @return 将两个对象的各interval的相似度与interval长度相乘后求和，除以总长度，然后返回二者整体相似度
     */
    double Similarity(MultiIntervalSet<L> s1, MultiIntervalSet<L> s2){
       double similarity = 0;
       long maxLength;
       Set<L> l1 = s1.labels();
       Set<L> l2 = s2.labels();
       for(L l:l1)
       {
           if(l2.contains(l))
           {
               Set<Interval> t1 = IntervalSetTools.allIntervals(s1.intervals(l));
               Set<Interval> t2 = IntervalSetTools.allIntervals(s2.intervals(l));
               Set<Interval> temp = IntervalTools.intersection(t1,t2);
               for(Interval i:temp)
               {
                   similarity += i.length();
               }
           }
       }
       long temp1 = 0;
       long temp2 = 0;
       List<Interval> all1 = IntervalSetTools.allIntervals(s1).stream().sorted().collect(Collectors.toList());
       List<Interval> all2 = IntervalSetTools.allIntervals(s2).stream().sorted().collect(Collectors.toList());
       temp1 = all1.get(all1.size() - 1).getEnd() - all1.get(0).getStart();
       temp2 = all2.get(all2.size() - 1).getEnd() - all2.get(0).getStart();
       maxLength = Long.max(temp1,temp2);
       similarity /= maxLength;
       return similarity;
    }

    /**
     * 返回一个MultiIntervalSet对象中的时间冲突比例
     * 冲突是指同一时间段类安排了两个不同的interval对象
     * 用发生冲突的时间段总长度除以总长度，得到冲突比例
     * @param set 一个MultiIntervalSet对象
     * @return 一个MultiIntervalSet对象中的时间冲突比例
     */
    double calcConflictRatio(MultiIntervalSet<L> set){
        double conflictProportion = 0;
        long length = 0;
        List<Interval> s = IntervalSetTools.allIntervals(set).stream().sorted().collect(Collectors.toList());
        for(int i=0; i<s.size(); i++)
        {
            length += s.get(i).getEnd() - s.get(i).getStart();
        }
        Interval completeWorks = new Interval(s.get(0).getStart(),s.get(s.size() - 1).getEnd());
        conflictProportion = IntervalSetTools.calcConflictRatio(set,completeWorks,length);
        return conflictProportion;
    }

    /**
     * 返回一个MultiIntervalSet对象中的空闲时间比例
     * 空闲时间指的是某段时间内没有安排任何Interval对象
     * 用空闲时间段总长度除以总长度，得到空闲时间比例
     * @param set 一个MultiIntervalSet对象
     * @return 一个MultiIntervalSet对象中的时间比例
     */
    double calcFreeTimeRatio(MultiIntervalSet<L> set){
        double freeTimeProportion = 0;
        List<Interval> s = IntervalSetTools.allIntervals(set).stream().sorted().collect(Collectors.toList());
        Interval completeWorks = new Interval(s.get(0).getStart(),s.get(s.size() - 1).getEnd());
        freeTimeProportion = IntervalSetTools.calcFreeTimeRatio(set,completeWorks);
        return freeTimeProportion;
    }

}
