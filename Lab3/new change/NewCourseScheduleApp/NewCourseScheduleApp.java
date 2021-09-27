package NewCourseScheduleApp;

import adt.Course;
import adt.impl.CourseIntervalSet;
import gui.DutyRoster.MyDate;
import gui.DutyRoster.MyTimeException;

import java.util.*;

public class NewCourseScheduleApp {

    private static NewCourseIntervalSet<Course> newCourseIntervalSet;
    private static MyDate startTime;
    private static int totalWeekNum;
    private static List<Course> courses;
    private static List<Integer> Week;
    private static int[][] timeRecord;
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("————————欢迎访问课表管理系统!");
        System.out.println();
        timeRecord = new int[7][5];
        for(int m=0; m<7; m++)
            for(int n=0; n<5; n++)
                timeRecord[m][n] = 0;
        while(true) {
            System.out.print("请输入学期开始日期(yyyy-mm-dd):");
            String s1 = scanner.nextLine();
            startTime = toTime(s1);
            if (startTime == null) {
                System.out.println("输入错误！请重新输入！");
                continue;
            }
            System.out.print("请输入学期总周数:");
            String s2 = scanner.nextLine();
            totalWeekNum = Integer.parseInt(s2);
            if(totalWeekNum <= 0){
                System.out.println("输入错误！请重新输入！");
                continue;
            }
            newCourseIntervalSet = new NewCourseIntervalSet<Course>(startTime.toLong() * 24L,totalWeekNum);
            break;
        }
        courses = new ArrayList<>();
        Week = new ArrayList<>();
        System.out.println();
        Menu();
    }

    /**
     * 将输入转换为MyDate对象
     */
    private static MyDate toTime(String s) {
        try {
            return new MyDate(s);
        } catch (MyTimeException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 菜单
     */
    private static void Menu(){
        int choice = 1;
        while(choice != 0) {
            System.out.println("------------------");
            System.out.println("操作菜单:");
            System.out.println("1. 增加一组课程");
            System.out.println("2. 为一个课程进行排课");
            System.out.println("3. 查看任意一天的课表");
            System.out.println("0. 退出操作");
            System.out.println("------------------");
            System.out.print("请键入操作号:");
            choice = Integer.parseInt(scanner.nextLine());
            System.out.println();
            switch (choice) {
                case 1:
                    addCourse();
                    System.out.println();
                    break;
                case 2:
                    courseArranging();
                    System.out.println();
                    break;
                case 3:
                    findCourse();
                    System.out.println();
                    break;
                case 0:
                    System.out.println("操作结束！欢迎再次访问！");
                    break;
                default:
                    System.out.println("输入错误！请重新选择操作：");
                    System.out.println();
            }
        }
    }

    /**
     * 增加一组课程
     */
    private static void addCourse(){
        System.out.println("请输入以下课程信息：");
        while(true) {
            System.out.println("课程ID \t课程名称 \t教师名字 \t教学地点 \t周学时数 (请以空格间隔)");
            String[] info = scanner.nextLine().trim().split(" ");
            if (Integer.parseInt(info[4]) % 2 != 0)
            {
                System.out.println("周学时数为偶数！请重新输入！");
                continue;
            }
            courses.add(new Course(info[0],info[1],info[2],info[3],Integer.parseInt(info[4])));

            System.out.println("继续添加课程？");
            String s = scanner.nextLine();
            if(s.equals("n") || s.equals("N")){
                break;
            }
        }
    }

    /**
     * 为一组课程排课
     */
    private static void courseArranging(){
        boolean flag = false;
        boolean flag2 = false;
        String[] s;
        int ch = 0;
        s = new String[]{"一", "二", "三", "四", "五", "六", "日"};
        int num = 0;
        while(true)
        {
            System.out.println();
            System.out.print("请输入进行排课的课程名：");
            String courseName = scanner.nextLine();
            for(Course c:courses)
            {
                if (c.getCourseName().equals(courseName)) {
                    flag2 = true;
                    break;
                }
            }
            if(!flag2){
                System.out.println("查无此课程！请重新输入！");
                continue;
            }
            flag =false;
            for (Course c : courses)
            {
                num = 0;
                if (c.getCourseName().equals(courseName))
                {
                    for(int i=0; i<7; i++) {
                        System.out.println("星期" + s[i] + "：");
                        System.out.println("0. 不选课");
                        System.out.println("1. [8-10]");
                        System.out.println("2. [10-12]");
                        System.out.println("3. [13-15]");
                        System.out.println("4. [15-17]");
                        System.out.println("5. [19-21]");
                        System.out.println("请选择上课时间：（输入时间段前的号码 || 可选择多个 || 请以空格间隔)");
                        String[] line = scanner.nextLine().trim().split(" ");
                        for (int k = 0; k < line.length; k++) {
                            ch = Integer.parseInt(line[k]);
                            switch (ch) {
                                case 0:
                                    break;
                                case 1:
                                    if(timeRecord[i][0] + 1 > 1)
                                    {
                                        System.out.println();
                                        System.out.println("重复排课！");
                                        System.out.println("当前1,2节课已排课！");
                                        continue;
                                    }
                                    else {
                                        newCourseIntervalSet.insert(8 + i * 24, 10 + i * 24, c);
                                        timeRecord[i][0]++;
                                        c.addStartTime(8 + i * 24);
                                        num++;
                                    }
                                    break;
                                case 2:
                                    if(timeRecord[i][1] + 1 > 1)
                                    {
                                        System.out.println();
                                        System.out.println("重复排课！");
                                        System.out.println("当前3,4节课已排课！");
                                        continue;
                                    }
                                    newCourseIntervalSet.insert(10 + i * 24, 12 + i * 24, c);
                                    timeRecord[i][1]++;
                                    c.addStartTime(10 + i * 24);
                                    num++;
                                    break;
                                case 3:
                                    if(timeRecord[i][2] + 1 > 1)
                                    {
                                        System.out.println();
                                        System.out.println("重复排课！");
                                        System.out.println("当前5,6节课已排课！");
                                        continue;
                                    }
                                    else {
                                        newCourseIntervalSet.insert(13 + i * 24, 15 + i * 24, c);
                                        timeRecord[i][2]++;
                                        c.addStartTime(13 + i * 24);
                                        num++;
                                    }
                                    break;
                                case 4:
                                    if(timeRecord[i][3] + 1 > 1)
                                    {
                                        System.out.println();
                                        System.out.println("重复排课！");
                                        System.out.println("当前7,8节课已排课！");
                                        continue;
                                    }
                                    else {
                                        newCourseIntervalSet.insert(15 + i * 24, 17 + i * 24, c);
                                        timeRecord[i][3]++;
                                        c.addStartTime(15 + i * 24);
                                        num++;
                                    }
                                    break;
                                case 5:
                                    if(timeRecord[i][4] + 1 > 1)
                                    {
                                        System.out.println();
                                        System.out.println("重复排课！");
                                        System.out.println("当前9,10节课已排课！");
                                        continue;
                                    }
                                    else {
                                        newCourseIntervalSet.insert(19 + i * 24, 21 + i * 24, c);
                                        timeRecord[i][4]++;
                                        c.addStartTime(19 + i * 24);
                                        num++;
                                    }
                                    break;
                                default:
                                    System.out.println("输入错误！请重新输入 '输入错误部分' ！");
                                    continue;
                            }
                            if (num == c.getWeekLearningTime()) {
                                System.out.println("选课学时已达上限！");
                                c.setFlag();
                                flag = true;
                                break;
                            }
                        }
                        if(flag)
                            break;
                    }
                    if(flag)
                        break;
                }
            }
            System.out.println();
            System.out.println("查看课程排课情况？");
            String line2 = scanner.nextLine();
            if(line2.equals("y") || line2.equals("Y"))
                checkCourse();

            System.out.println();
            System.out.println("是否继续排课？");
            String line3 = scanner.nextLine();
            if(line3.equals("n") || line3.equals("N")){
                System.out.println();
                System.out.println("排课结束！");
                break;
            }

        }
    }

    /**
     * 检查课程排课情况
     */
    private static void checkCourse(){
        List<Course> s1 = new ArrayList<>();
        List<Course> s2 = new ArrayList<>();
        int num1 =0;
        int num2 =0;
        for (Course course : courses) {
            if (course.getFlag())
                s1.add(course);
            else
                s2.add(course);
        }
        System.out.println();
        System.out.println("---------------");
        System.out.println("已排课完毕的课程：");
        for(Course c:s1)
            System.out.print(c.getCourseName() + "  ");
        System.out.println();
        System.out.println("---------------");
        System.out.println("未排课完毕的课程：");
        for(Course c:s2)
            System.out.print(c.getCourseName() + "  ");
        System.out.println();
        for(int m=0; m<7; m++)
            for(int n=0; n<5; n++)
            {
                if(timeRecord[m][n] > 0) {
                    num1++;
                    if(timeRecord[m][n] > 1)
                        num2++;
                }
            }
        System.out.println("---------------");
        System.out.println("空闲时间比例：" + (num1 * 100) / 35 + "%");
        System.out.println("---------------");
        System.out.println("重复时间比例：" + (num2 * 100) / 35 + "%");
    }

    /**
     * 查找某一天的课表
     */
    private static void findCourse(){
        String[] s;
        s = new String[]{"一", "二", "三", "四", "五", "六", "日"};
        while(true) {
            System.out.println("请输入需要查看的课表的时间信息：");
            System.out.println("第几周：(1~" + totalWeekNum + ")");
            int week = Integer.parseInt(scanner.nextLine());
            if (week > totalWeekNum || week <= 0) {
                System.out.println("输入错误！请重新输入！");
                continue;
            }
            System.out.println("星期几: (1~7)");
            int day = Integer.parseInt(scanner.nextLine());
            if(day <= 0 || day > 7){
                System.out.println("输入错误！请重新输入！");
                continue;
            }
            System.out.println();
            System.out.println("  第" + week + "周" + "\t" + "星期" + s[day-1]);
            System.out.println();
            System.out.println("上午\t┃第1,2节  \t┃");
            for(Course c:courses)
            {
                if(c.getStartTime().contains((long)(8+(day-1)*24))) {
                    System.out.println("  \t┃       \t┃" + c.getCourseName() + "-" + c.getTeacherName() + "[" + c.getWeekLearningTime() + "课时]" + "-" + c.getPlace());
                }
            }
            System.out.println("-------------------------");
            System.out.println("上午\t┃第3,4节  \t┃");
            for(Course c:courses)
            {
                if(c.getStartTime().contains((long)(10+(day-1)*24))) {
                    System.out.println("  \t┃       \t┃" + c.getCourseName() + "-" + c.getTeacherName() + "[" + c.getWeekLearningTime() + "课时]" + "-" + c.getPlace());
                }
            }
            System.out.println("-------------------------");
            System.out.println("下午\t┃第5,6节  \t┃");
            for(Course c:courses)
            {
                if(c.getStartTime().contains((long)(13+(day-1)*24))) {
                    System.out.println("  \t┃       \t┃" + c.getCourseName() + "-" + c.getTeacherName() + "[" + c.getWeekLearningTime() + "课时]" + "-" + c.getPlace());
                }
            }
            System.out.println("-------------------------");
            System.out.println("下午\t┃第7,8节  \t┃");
            for(Course c:courses)
            {
                if(c.getStartTime().contains((long)(15+(day-1)*24))) {
                    System.out.println("  \t┃       \t┃" + c.getCourseName() + "-" + c.getTeacherName() + "[" + c.getWeekLearningTime() + "课时]" + "-" + c.getPlace());
                }
            }
            System.out.println("-------------------------");
            System.out.println("晚上\t┃第9,10节  \t┃");
            for(Course c:courses)
            {
                if(c.getStartTime().contains((long)(19+(day-1)*24))) {
                    System.out.println("  \t┃       \t┃" + c.getCourseName() + "-" + c.getTeacherName() + "[" + c.getWeekLearningTime() + "课时]" + "-" + c.getPlace());
                }
            }
            System.out.println("是否继续查课？");
            String line9 = scanner.nextLine();
            if(line9.equals("n") || line9.equals("N")){
                System.out.println();
                System.out.println("查课结束！");
                break;
            }

        }
    }
}

