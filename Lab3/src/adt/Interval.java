package adt;

import java.util.Objects;

public class Interval {
    //fields
    private long start;
    private long end;

    // Abstraction function:
    //   AF(start) = 标签对应的开始时间
    //   AF(end) = 标签对应的结束时间
    // Representation invariant:
    //   时间段大于等于0
    // Safety from rep exposure:
    //   将start,end设置为private


    public Interval(long start, long end) {
        this.start = start;
        this.end = end;
    }

    /**
     * 检查RI
     */
    public void checkRep() {
        assert end >= start;
    }

    //methods

    /**
     * 返回某标签对应的开始时间
     *
     * @return 返回某标签对应的开始时间
     */
    public long getStart() {
        checkRep();
        return start;
    }

    /**
     * 返回某标签对应的结束时间
     *
     * @return 返回某标签对应的结束时间
     */
    public long getEnd() {
        checkRep();
        return end;
    }

    /**
     * 删除某标签的时间段
     */
    public void remove() {
        this.start = 0;
        this.end = 0;
        checkRep();
    }

    /**
     * 返回时间段的长度
     * @return 时间段的长度
     */
    public long length(){
        return end - start;
    }

    @Override
    public String toString() {
        return "[" +
                start + ","
                + end +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interval interval = (Interval) o;
        return start == interval.start && end == interval.end;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
