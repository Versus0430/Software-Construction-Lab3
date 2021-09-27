package adt;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EmployeeTest {

    @Test
    public void getName() {
        Employee e =  new Employee("a","b","c");
        assertEquals("a",e.getName());
    }

    @Test
    public void getJob() {
        Employee e =  new Employee("a","b","c");
        assertEquals("b",e.getJob());
    }

    @Test
    public void getPhoneNumber() {
        Employee e =  new Employee("a","b","c");
        assertEquals("c",e.getPhoneNumber());
    }
}