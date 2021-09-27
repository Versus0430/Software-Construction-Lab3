package adt;

import static org.junit.Assert.assertEquals;

import org.junit.Test;





public class ProcessTest {

    @Test
    public void getProcessID() {
        Process p = new Process("1","a",10,20);
        assertEquals("1",p.getProcessID());
    }

    @Test
    public void getProcessName() {
        Process p = new Process("1","a",10,20);
        assertEquals("a",p.getProcessName());
    }

    @Test
    public void getMinTime() {
        Process p = new Process("1","a",10,20);
        assertEquals(10,p.getMinTime());
    }

    @Test
    public void getMaxTime() {
        Process p = new Process("1","a",10,20);
        assertEquals(20,p.getMaxTime());
    }

    @Test
    public void getRunningTime() {
        Process p = new Process("1","a",10,20);
        assertEquals(0,p.getRunningTime());
    }

    @Test
    public void setFlag() {
        Process p = new Process("1","a",10,20);
        p.setFlag();
        assertEquals(true,p.getFlag());
    }

    @Test
    public void resetFlag() {
        Process p = new Process("1","a",10,20);
        p.resetFlag();
        assertEquals(false,p.getFlag());
    }

    @Test
    public void setRunningTime() {
        Process p = new Process("1","a",10,20);
        p.setRunningTime(10);
        assertEquals(10,p.getRunningTime());
    }

    @Test
    public void getFlag() {
        Process p = new Process("1","a",10,20);
        assertEquals(false,p.getFlag());
    }
}