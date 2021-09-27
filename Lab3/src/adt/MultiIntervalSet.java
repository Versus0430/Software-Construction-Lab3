package adt;

import java.util.Set;

public interface MultiIntervalSet<L>{
    /**
     * 创建一个空对象
     * @param <L>
     * @return
     */
    public static <L> MultiIntervalSet<L> empty() {
        return new CommonMultiIntervalSet<L>();
    }

    /**
     * 利用initial中的包含的数据创建非空对象
     */
    public void MultiIntervalSet(IntervalSet<L> initial);

    /**
     * 在当前对象插入新的时间段和标签
     * @param start
     * @param end
     * @param label
     */
    public void insert(long start,long end,L label);

    /**
     * 获得当前对象中的标签集合
     * @return 当前对象中的标签集合
     */
    public Set<L> labels();

    /**
     * 从当前对象中移除某个标签所关联的所有时间段
     * @param label
     * @return 若在标签集中包含次标签，则删除该标签所关联的时间段，返回true,否则返回false
     */
    public boolean remove(L label);

    /**
     * 从当前对象中获取与某个标签所关联的所有时间段,其中的时间段需按照开始时间从小到大的次序排列
     * @param label
     * @return 某个标签所关联的所有时间段，其中的时间段需按照开始时间从小到大的次序排列,若找不到该标签，则返回空
     */
    public IntervalSet<Integer> intervals(L label);

}
