package adt;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CommonIntervalSetTest extends IntervalSetTest{

    @Override public IntervalSet<String> emptyInstance() {
        return new CommonIntervalSet<String>();
    }

    /**
     * Testing strategy
     * 测试三个标签的时间段，检测结果是否匹配
     */
    @Test
    public void testtoString(){
        IntervalSet<String> s = emptyInstance();
        s.insert(0,5,"A");
        s.insert(10,30,"B");
        s.insert(30,35,"C");
        assertEquals("{ A=[0,5] B=[10,30] C=[30,35] }",s.toString());
    }

    //测试Label中的每个方法

    /**
     * Testing strategy
     * 根据三个测试样例测试方法
     */
    @Test
    public void testgetStart(){
        Interval i1 = new Interval(0,5);
        assertEquals(0,i1.getStart());
        Interval i2 = new Interval(10,15);
        assertEquals(10,i2.getStart());
        Interval i3 = new Interval(20,25);
        assertEquals(20,i3.getStart());
    }

    /**
     * Testing strategy
     * 根据三个测试样例测试方法
     */
    @Test
    public void testgetEnd(){
        Interval i1 = new Interval(0,5);
        assertEquals(5,i1.getEnd());
        Interval i2 = new Interval(10,15);
        assertEquals(15,i2.getEnd());
        Interval i3 = new Interval(20,25);
        assertEquals(25,i3.getEnd());
    }

    /**
     * Testing strategy
     * 通过删除三个样例来测试方法
     */
    @Test
    public void testRemove(){
        Interval l1 = new Interval(0,5);
        Interval l2 = new Interval(10,15);
        Interval l3 = new Interval(20,25);
        l1.remove();
        assertEquals(true,l1.getStart() == 0 && l1.getEnd() == 0);
        l2.remove();
        assertEquals(true,l2.getStart() == 0 && l2.getEnd() == 0);
        l3.remove();
        assertEquals(true,l3.getStart() == 0 && l3.getEnd() == 0);
    }
}