package adt;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CommonMultiIntervalSetTest extends MultiIntervalSetTest{

    @Override
    public MultiIntervalSet<String> emptyInstance() {
        return new CommonMultiIntervalSet<String>();
    }

    /**
     * Testing strategy
     * 通过三个样例，来测试toString方法是否正确
     */
    @Test
    public void testToString(){
        MultiIntervalSet<String> s = emptyInstance();
        s.insert(0,5,"a");
        s.insert(10,20,"b");
        s.insert(20,30,"a");
        assertEquals("CommonMultiIntervalSet{map={a={ 0=[0,5] 1=[20,30] }, b={ 0=[10,20] }}}",s.toString());
    }

}