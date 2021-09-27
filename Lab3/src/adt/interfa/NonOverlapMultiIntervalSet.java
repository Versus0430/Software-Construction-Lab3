package adt.interfa;

public interface NonOverlapMultiIntervalSet<L>{
    /**
     * 插入一个标签label，对应的时间段为(start,end)
     * @param start 插入标签的开始时间
     * @param end 插入标签的结束时间
     * @param label 插入标签的标签名
     */
    public void insert(long start,long end,L label);
}
