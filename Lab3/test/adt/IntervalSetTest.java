package adt;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Test;

public abstract class IntervalSetTest {

    public abstract IntervalSet<String> emptyInstance();

    /**
     * Testing strategy
     * 通过与Collections.emptySet()方法，测试空对象是否创建成功
     */
    @Test
    public void testEmpty(){
        IntervalSet<String> s = emptyInstance();
        assertEquals(Collections.emptySet(),s.labels());
    }

    /**
     * Testing strategy
     * 根据时间段的大小划分：大于0，小于等于0
     * 根据labels中标签名是否重复划分：重复，未重复
     * 根据时间段对应的标签是否唯一划分：唯一，不唯一
     */
    @Test
    public void testInsert(){
        IntervalSet<String> s = emptyInstance();
        s.insert(0,5,"A");
        assertEquals(true,s.labels().contains("A"));
    }

    /**
     * Testing strategy
     *根据labels的内容来划分：空，非空
     */
    @Test
    public void testLabels(){
        IntervalSet<String> s = emptyInstance();
        assertEquals(Collections.emptySet(),s.labels());
        testInsert();
        s.insert(0,10,"a");
        assertEquals(false,s.labels().isEmpty());
    }

    /**
     * Testing strategy
     * 根据删除的标签情况：在集合中，未在集合中
     */
    //@Test
    //public void testRemove(){
        //IntervalSet<String> s = emptyInstance();
        //testInsert();
       // s.insert(10,20,"a");
       // s.remove("a");
        //assertEquals(0,s.start("a"));
        //assertEquals(0,s.end("a"));
    //}

    /**
     * Testing strategy
     * 根据返回的值进行三个样例测试
     */
    @Test
    public void testStart(){
        IntervalSet<String> s = emptyInstance();
        testInsert();
        s.insert(0,10,"a");
        s.insert(10,20,"b");
        s.insert(20,30,"c");
        assertEquals(0,s.start("a"));
        assertEquals(10,s.start("b"));
        assertEquals(20,s.start("c"));
    }

    /**
     * Testing strategy
     * 根据返回的值进行三个样例测试
     */
    @Test
    public void testEnd(){
        IntervalSet<String> s = emptyInstance();
        testInsert();
        s.insert(0,10,"a");
        s.insert(10,20,"b");
        s.insert(20,30,"c");
        assertEquals(10,s.end("a"));
        assertEquals(20,s.end("b"));
        assertEquals(30,s.end("c"));
    }
}