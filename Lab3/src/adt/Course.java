package adt;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course {
    private final String courseID;
    private final String courseName;
    private final String teacherName;
    private final String place;
    private final int weekLearningTime;
    private boolean flag;
    private List<Long> startTime;

    /**
     * 构造器
     * @param courseID 课程ID
     * @param courseName 课程名
     * @param teacherName 教师姓名
     * @param place 地点
     */
    public Course(String courseID, String courseName, String teacherName, String place, int weekLearningTime) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.place = place;
        this.weekLearningTime = weekLearningTime;
        this.flag = false;
        startTime = new ArrayList<>();
    }

    /**
     * 返回课程ID
     * @return 课程ID
     */
    public String getCourseID() {
        return courseID;
    }

    /**
     * 返回课程名
     * @return 课程名
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * 返回教师姓名
     * @return 教师姓名
     */
    public String getTeacherName() {
        return teacherName;
    }

    /**
     * 返回地点
     * @return 地点
     */
    public String getPlace() {
        return place;
    }

    /**
     * 返回周学时数
     * @return 周学时数
     */
    public int getWeekLearningTime() { return weekLearningTime;}

    /**
     * 设置拍完课的标志
     */
    public void setFlag() { flag = true;}

    /**
     * 返回是否已排完课的标志
     * @return 是否排完课的标志
     */
    public boolean getFlag(){ return flag;}

    /**
     * 加入一个开始时间
     * @param time 一个开始时间
     */
    public void addStartTime(long time){
        startTime.add(time);
    }

    public List<Long> getStartTime(){
        return new ArrayList<>(startTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return weekLearningTime == course.weekLearningTime && flag == course.flag && Objects.equals(courseID, course.courseID) && Objects.equals(courseName, course.courseName) && Objects.equals(teacherName, course.teacherName) && Objects.equals(place, course.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseID, courseName, teacherName, place, weekLearningTime, flag);
    }
}
