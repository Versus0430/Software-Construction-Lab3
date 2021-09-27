package adt.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ProcessIntervalSetTest {
    @Test
    public void testInsert(){
        ProcessIntervalSet<String> test = new ProcessIntervalSet<>();
        try{
            test.insert(1,4,"a");
            //test.insert(3,8,"a");
            //assert false;
        }catch(RuntimeException ex){
            //assertEquals("a中有重叠时间段",ex.getMessage());
        }
        try{
            test.insert(5,8,"b");
            //test.insert(7,9,"c");
            //assert false;
        }catch(RuntimeException e){
            //assertEquals("重叠时间段",e.getMessage());
        }
    }

}
