package adt;

import java.util.Set;

public interface IntervalSet<L> {
    /**
     * Create an empty IntervalSet.
     * @param <L> type of labels in the IntervalSet, must be immutable
     * @return a new empty IntervalSet
     */
    public static <L> IntervalSet<L> empty() {
        return new CommonIntervalSet<L>();
    }

    /**
     * 在当前对象插入新的时间段和标签
     * @param start
     * @param end
     * @param label
     * @return
     */
    public void insert(long start, long end, L label);

    /**
     * 获得当前对象中的标签集合
     * @return 当前对象中的标签集合
     */
    public Set<L> labels();

    /**
     * 从当前对象中移除某个标签所关联的时间段
     * @param label
     * @return 若在标签集中包含次标签，则删除该标签所关联的时间段，返回true,否则返回false
     */
    public boolean remove(L label);

    /**
     * 返回某个标签对应的时间段的开始时间
     * @param label
     * @return 标签对应的时间段的开始时间
     */
    public long start(L label);

    /**
     * 返回某个标签对应的时间段的结束时间
     * @param label
     * @return 标签对应的时间段的结束时间
     */
    public long end(L label);


}
