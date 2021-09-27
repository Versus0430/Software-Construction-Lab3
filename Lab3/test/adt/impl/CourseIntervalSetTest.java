package adt.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CourseIntervalSetTest {
    @Test
    public void testGetWeek_num(){
        CourseIntervalSet<String> test = new CourseIntervalSet<>((long)0,1);
        assertEquals(1,test.getWeek_num());
    }

    @Test
    public void testGetPeriod(){
        CourseIntervalSet<String> test = new CourseIntervalSet<>((long)0,1);
        assertEquals((long)168,test.getPeriod());
    }

    @Test
    public void testInsert(){
        CourseIntervalSet<String> test = new CourseIntervalSet<>((long)0,1);
        try{
            test.insert(1,3,"a");
            //test.insert(2,4,"a");
            //assert false;
        }catch(RuntimeException ex){
            //assertEquals("a中有重叠时间段",ex.getMessage());
        }
    }
}