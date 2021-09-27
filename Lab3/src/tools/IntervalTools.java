package tools;

import adt.Interval;

import java.util.*;
import java.util.stream.Collectors;

public class IntervalTools {
    /**
     * 合并区间集合
     * @param set 需要被合并的区间集合
     * @return 若set为null，则返回null
     *         若set大小小于等于1，则直接返回set
     *         返回合并后的区间集合
     */
    public static Set<Interval> merge(Set<Interval> set){
        if(set == null)
            return null;
        if(set.size() <= 1)
            return set;
        int i;
        List<Interval> intervals = new ArrayList<>(set);
        intervals.sort(new Comparator<Interval>() {

            @Override
            public int compare(Interval o1, Interval o2) {
                return (int) o1.getStart() - (int) o2.getStart();
            }
        });
        Set<Interval> res = new HashSet<>();
        Interval curr;
        Interval next;
        curr = intervals.get(0);
        for(i=1; i<set.size(); i++)
        {
            next  = intervals.get(i);
            if(curr.getEnd() < next.getStart())
            {
                res.add(curr);
                curr = next;
            }
            else
            {
                curr = new Interval(curr.getStart(), Long.max(next.getEnd(), curr.getEnd()));
            }
        }
        res.add(curr);
        return res;
    }

    /**
     * 返回x集合的区间与y区间的差集部分
     * @param x x集合
     * @param y y区间
     * @return x集合的区间与y区间的差集部分
     */
    public static Set<Interval> differenceSet(Set<Interval> x,Interval y){
        Set<Interval> s = new HashSet<>();
        Set<Interval> res = new HashSet<>();
        //List<Interval> list = IntervalTools.merge(x).stream().sorted().collect(Collectors.toList());
        List<Interval> list = new ArrayList<>(x);
        Collections.sort(list, new Comparator<Interval>() {

            @Override
            public int compare(Interval o1, Interval o2) {
                int i =(int) o1.getStart() -(int) o2.getStart();
                return i;
            }
        });
        s.add(y);
        if(x.isEmpty())
            return s;
        if(!(list.get(0).getStart() == y.getStart()))
        {
            res.add(new Interval(y.getStart(),list.get(0).getStart()));
        }
        for(int i=0; i<list.size()-1; i++)
        {
            res.add(new Interval(list.get(i).getEnd(),list.get(i+1).getStart()));
        }
        if(!(list.get(list.size() - 1).getEnd() == y.getEnd()))
            res.add(new Interval(list.get(list.size() - 1).getEnd(),y.getEnd()));
        return res;
    }

    /**
     * 求两个集合的交集
     * @param x 集合x
     * @param y 集合y
     * @return 集合x与集合y的交集
     */
    public static Set<Interval> intersection(Set<Interval> x,Set<Interval> y){
        List<Interval> list1 = new ArrayList<>(x);
        list1.sort(new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                return (int) o1.getStart() - (int) o2.getStart();
            }
        });
        List<Interval> list2 = new ArrayList<>(y);
        list2.sort(new Comparator<Interval>() {
            @Override
            public int compare(Interval o3, Interval o4) {
                return (int) o3.getStart() - (int) o4.getStart();
            }
        });
        int numx=0;
        int numy=0;
        Set<Interval> intersection = new HashSet<>();
        while(numx < x.size() && numy < y.size())
        {
            long x_start = list1.get(numx).getStart();
            long x_end = list1.get(numx).getEnd();
            long y_start = list2.get(numy).getStart();
            long y_end = list2.get(numy).getEnd();
            if(y_end >= x_start && y_start <= x_end) //此时有交集
            {
                long temp_start = Long.max(x_start,y_start);
                long temp_end = Long.min(x_end,y_end);
                if(temp_start != temp_end)
                    intersection.add(new Interval(temp_start,temp_end));
            }
            if(x_end < y_end) //此时x段的结束时间小于y段的结束时间，遍历下一个x段，y段不变
                numx++;
            else //此时y段的结束时间小于x段的结束时间，遍历下一个y段，x段不变
                numy++;
        }
        return IntervalTools.merge(intersection);
    }

    /**
     * 返回set中所有不重复区间的长度之和
     * @param set 集合set
     * @return set中所有不重复区间的长度之和
     */
    public static long length(Set<Interval> set){
        if(set.size() == 0)
            return 0;
        //List<Interval> list = IntervalTools.merge(set).stream().sorted().collect(Collectors.toList());
        List<Interval> list = new ArrayList<>(set);
        Collections.sort(list, new Comparator<Interval>() {

            @Override
            public int compare(Interval o1, Interval o2) {
                int i =(int) o1.getStart() -(int) o2.getStart();
                return i;
            }
        });
        long res = 0;
        for(int i=0; i<set.size(); i++)
        {
            res += list.get(i).getEnd() - list.get(i).getStart();
        }
        return res;
    }

}
