package adt;



import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;



public class CourseTest {

    @Test
    public void getCourseID() {
        Course c = new Course("1","a","b","c",4);
        assertEquals("1",c.getCourseID());
    }

    @Test
    public void getCourseName() {
        Course c = new Course("1","a","b","c",4);
        assertEquals("a",c.getCourseName());
    }

    @Test
    public void getTeacherName() {
        Course c = new Course("1","a","b","c",4);
        assertEquals("b",c.getTeacherName());
    }

    @Test
    public void getPlace() {
        Course c = new Course("1","a","b","c",4);
        assertEquals("c",c.getPlace());
    }

    @Test
    public void getWeekLearningTime() {
        Course c = new Course("1","a","b","c",4);
        assertEquals(4,c.getWeekLearningTime());
    }

    @Test
    public void setFlag() {
        Course c = new Course("1","a","b","c",4);
        c.setFlag();
        assertEquals(true,c.getFlag());
    }

    @Test
    public void getFlag() {
        Course c = new Course("1","a","b","c",4);
        assertEquals(false,c.getFlag());
    }

    @Test
    public void addStartTime() {
        Course c = new Course("1","a","b","c",4);
        c.addStartTime(2);
        List<Long> l = new ArrayList<>();
        l.add(2L);
        assertEquals(l,c.getStartTime());
    }

    @Test
    public void getStartTime() {
        Course c = new Course("1","a","b","c",4);
        assertEquals(new ArrayList<Long>(),c.getStartTime());
    }
}