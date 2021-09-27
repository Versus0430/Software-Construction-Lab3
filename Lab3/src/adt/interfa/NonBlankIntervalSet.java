package adt.interfa;

import adt.Interval;

import java.util.Set;

public interface NonBlankIntervalSet<L> {
    /**
     * 检查规定时间段是否有空白
     * @return 无空白，则返回true,否则返回false
     */
    public boolean checkNoBlank();

    /**
     * 获取时间轴上的空白段，若没有空白段则返回empty的集合
     * @return 空白段的Set
     */
    public Set<Interval> getBlank();

}
