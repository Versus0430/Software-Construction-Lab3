package adt.impl;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import adt.Interval;

public class DutyIntervalSetTest {

    @Test
    public void testCheckNoBlank(){
        DutyIntervalSet<String> test = new DutyIntervalSet<>(1,6);
        test.insert(1,2,"a");
        test.insert(3,4,"b");
        assertEquals(false,test.checkNoBlank());
        test.insert(4,6,"c");
        test.insert(2,3,"d");
        assertEquals(true,test.checkNoBlank());
    }

    @Test
    public void testGetBlank(){
        DutyIntervalSet<String> test = new DutyIntervalSet<>(1,6);
        Set<Interval> set = new HashSet<>();
        test.insert(1,2,"a");
        set.add(new Interval(2,6));
        assertEquals(set,test.getBlank());
    }

    @Test
    public void testInsert(){
        DutyIntervalSet<String> test = new DutyIntervalSet<>(1,6);
        try {
            test.insert(2, 5, "a");
            //test.insert(1,6,"c");
            //assert false;
        } catch (RuntimeException e) {
            //assertEquals("重叠时间段",e.getMessage());
        }
    }
}