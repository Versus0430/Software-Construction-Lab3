package adt.interfa;

public interface NonPeriodicMultiIntervalSet<L>{
    /**
     * 返回周期长度
     * @return 周期长度
     */
    public long getPeriod();

    /**
     * 返回周期的开始时间
     * @return 周期的开始时间
     */
    public long getPeriodStart();

    /**
     * 插入一个标签label，对应时间段为(start,end)
     * 需要检查是否在第一个周期范围内
     * @param start 插入标签的开始时间
     * @param end 插入标签的结束时间
     * @param label 插入标签的标签名
     */
    public void insert(long start,long end,L label);
}
