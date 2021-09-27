package NewDutyRosterApp;

import adt.CommonIntervalSet;
import adt.Employee;
import adt.Interval;
import adt.IntervalSet;
import adt.impl.DutyIntervalSet;
import gui.DutyRoster.MyDate;
import gui.DutyRoster.MyTimeException;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewDutyRosterApp {

    private static gui.DutyRoster.MyDate startTime;
    private static gui.DutyRoster.MyDate endTime;
    private static List<Employee> employees;
    private static NewDutyIntervalSet<Employee> newDutyIntervalSet;
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("————————欢迎访问员工排班管理系统!");
        System.out.println();
        System.out.println("从外部文件读入？");
        String ans = scanner.nextLine();
        if(ans.equals("y") || ans.equals("Y"))
        {
            while(true) {
                try {
                    System.out.println("请输入测试文件名：");
                    String name = scanner.nextLine();
                    fileParse("C:\\github\\HIT-Lab3-1190501809\\src\\gui\\DutyRoster\\test\\" + name);
                    System.out.println();
                    System.out.println("操作结束！欢迎再次访问！");
                } catch (IOException | RuntimeException e) {
                    System.out.println("读取文件不合法！请选择其他合法文件！");
                    continue;
                }
                break;
            }
        }
        else {
            System.out.println("------------------");
            System.out.println("请输入排班日期起止时间：");
            System.out.println("------------------");
            getTime();
            newDutyIntervalSet = new NewDutyIntervalSet<Employee>(0, endTime.toLong() - startTime.toLong());
            employees = new ArrayList<>();
            System.out.println();
            getEmployees();
            System.out.println();
            System.out.println();
            System.out.println("自动排班？");
            String ans1 = scanner.nextLine();
            if(ans1.equals("y") || ans1.equals("Y"))
            {
                autoArranging();
                System.out.println();
                System.out.println("操作结束！欢迎再次访问！");
                return;
            }
            Menu();
        }
    }


    /**
     * 读入起始时间与结束时间
     */
    private static void getTime() {
        while(true) {
            System.out.print("请输入排班开始时间(yyyy-mm-dd):");
            String s1 = scanner.nextLine();
            startTime = toTime(s1);
            if (startTime == null) {
                continue;
            }
            System.out.print("请输入排班结束时间(yyyy-mm-dd):");
            String s2 = scanner.nextLine();
            endTime = toTime(s2);
            if(endTime.toLong() < startTime.toLong())
            {
            	System.out.println("输入时间错误！请重新输入！");
            	continue;
            }
            if (endTime != null) {
                break;
            }
        }
    }

    /**
     * 将输入转换为MyDate对象
     */
    private static gui.DutyRoster.MyDate toTime(String s) {
        try {
            return new gui.DutyRoster.MyDate(s);
        } catch (MyTimeException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 读入员工信息
     */
    private static void getEmployees() {
        int num;
        System.out.println("------------------");
        System.out.println("请输入员工信息:");
        System.out.println("------------------");
        while(true) {
            System.out.println("请输入员工人数：");
            num = Integer.parseInt(scanner.nextLine());
            if (num <= 0) {
                System.out.println("输入错误！请重新输入:");
                continue;
            }
            break;
        }
        int i=1;
        while(i<=num) {
            System.out.println("员工" + i + ":");
            System.out.println("姓名\t职务\t手机号码");
            String line = scanner.nextLine();
            String[] info = line.trim().split("\\s+");
            if(info.length < 3) {
                System.out.println("输入错误！请重新输入:");
                continue;
            }
            employees.add(new Employee(info[0], info[1], info[2]));
            i++;
        }
    }

    /**
     * 菜单
     */
    private static void Menu(){
        int choice = 1;
        while(choice != 0) {
            System.out.println("------------------");
            System.out.println("操作菜单:");
            System.out.println("1. 打印员工表");
            System.out.println("2. 删除员工表中的某个员工");
            System.out.println("3. 向排班表增加排班记录"); //可连续增加多条 && 以日为单位，最少一天，可以为多天
            System.out.println("4. 删除排班表中的某个员工");
            System.out.println("5. 打印排班表");
            System.out.println("0. 退出操作");
            System.out.println("------------------");
            System.out.print("请键入操作号:");
            choice = Integer.parseInt(scanner.nextLine());
            System.out.println();
            switch (choice) {
                case 1:
                    PrintEmployeeTable();
                    System.out.println();
                    break;
                case 2:
                    deleteEmployee();
                    System.out.println();
                    break;
                case 3:
                    addNewRecord();
                    System.out.println();
                    break;
                case 4:
                    deleteRecord();
                    System.out.println();
                    break;
                case 5:
                    printTable();
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
     * 打印员工表
     */
    private static void PrintEmployeeTable(){
        int i = 0;
        System.out.println("----------------------- \t 员工表    \t----------------------");
        System.out.println("    \t  员工姓名\t\t\t\t 职位\t\t\t\t 手机号码");
        while(i < employees.size()) {
            print_table(employees.get(i).getName()+"", employees.get(i).getJob()+"", employees.get(i).getPhoneNumber()+"", "");
            i++;
        }
    }

    /**
     * 删员工表中的某个员工
     */
    private static void deleteEmployee() {
        while (true) {
            System.out.println("请输入选择删除员工的姓名:");
            String oneName = scanner.nextLine();
            for (Employee e : employees) {
                if (e.getName().equals(oneName)) {
                    employees.remove(e);
                    System.out.println("删除成功！");
                    return;
                }
            }
            System.out.println("未查到此员工！请重新操作：");
        }
    }

    /**
     * 增加一条新的排班记录
     */
    private static void addNewRecord() {
        boolean flag1 = false;
        System.out.println("请输入值班信息：");
        while(true) {
            System.out.println("值班人姓名\t值班开始日期\t值班结束日期 (请以空格间隔）");
            String[] info = scanner.nextLine().trim().split("\\s");
            if(info.length < 3) {
                System.out.println("输入错误!");
                continue;
            }
            String duty_name = info[0];
            for(Employee e:employees) {
                if(e.getName().equals(duty_name))
                    flag1 = true;
            }
            if(!flag1) {
                System.out.println("输入的值班人查无此人，请重新输入！");
                continue;
            }
            gui.DutyRoster.MyDate start_time = toTime(info[1]);
            if (start_time == null)
                continue;
            if(start_time.toLong() < startTime.toLong() || start_time.toLong() >= endTime.toLong()) {
                System.out.println("输入时间范围错误！");
                continue;
            }
            gui.DutyRoster.MyDate end_time = toTime(info[2]);
            if(end_time == null)
                continue;
            if(end_time.toLong() <= startTime.toLong() || end_time.toLong() > endTime.toLong()) {
                System.out.println("输入时间范围错误！");
                continue;
            }
            long st = start_time.toLong() - startTime.toLong();
            long et = end_time.toLong() - startTime.toLong();
            try {
                for (Employee e : employees) {
                    if (e.getName().equals(duty_name))
                        newDutyIntervalSet.insert(st, et, e);
                }
            }catch(RuntimeException e){
                System.out.println("插入重叠时间段！请重新排入时间！");
                continue;
            }
            System.out.println("是否检查当前排班情况? (y/n)");
            String ans1 = scanner.nextLine();
            if(ans1.equals("y") || ans1.equals("Y")) {
                boolean flag = checkTable();
                if (flag) {
                    System.out.println("当前排班时间已满!");
                    return;
                }
            }

            System.out.println("是否继续添加排班信息? (y/n)");
            String ans2 = scanner.nextLine();
            if(ans2.equals("n") || ans2.equals("N"))
                break;
        }
    }

    /**
     * 检查当前排班情况,已满则返回true，否则返回false
     */
    private static boolean checkTable(){
        long x = 0;
        if (newDutyIntervalSet.checkNoBlank()) {
            return true;
        }
        System.out.println("当前排班时间未满!");
        System.out.print("未安排时间：");
        for(Interval i:newDutyIntervalSet.getBlank()) {
            System.out.println("[" + startTime.add(i.getStart()) + "," + startTime.add(i.getEnd()) + "]" );
            x += i.getEnd() - i.getStart();
        }
        System.out.println("未安排时间占总时间段的比例: " + (x * 100L) / (endTime.toLong() - startTime.toLong()) + "%");
        return false;
    }

    /**
     * 删除排班表中的某个员工及其对应的信息
     */
    private static void deleteRecord(){
        boolean flag = false;
        while(true) {
            System.out.println("请输入排班表中需要删除的员工姓名:");
            String employee_name = scanner.nextLine();
            for (Employee e : newDutyIntervalSet.labels()) {
                if (e.getName().equals(employee_name)) {
                    flag = true;
                    newDutyIntervalSet.remove(e);
                    System.out.println("删除成功！");
                    return;
                }
            }
            System.out.println("排班表中查无此员工! 请重新输入！");
        }
    }

    /**
     * 打印排班表
     */
    private static void printTable(){
    	int num = 0;
        if(!newDutyIntervalSet.checkNoBlank())
        {
            System.out.println("有空白！");
            for(Interval i:newDutyIntervalSet.getBlank())
            {
                System.out.println("空白段:["+startTime.add(i.getStart())+", " + startTime.add(i.getEnd()) + "]");
            }
            return;
        }
        System.out.println("----------------------------------- \t 排班表    \t------------------------------------");
        System.out.println("    \t\t日期\t\t\t\t 值班人名字\t\t\t\t 职位\t\t\t\t 手机号码");
        List<Interval> s = sorting();
        for (Interval interval : s) {
            for (Employee e : employees) {
            	num = 0;
                for (Integer k : newDutyIntervalSet.intervals(e).labels()) {
                    if (newDutyIntervalSet.intervals(e).start(k) == interval.getStart() && newDutyIntervalSet.intervals(e).end(k) == interval.getEnd()) {
                        for (long m = interval.getStart(); m <= interval.getEnd(); m++) {
                        	if(m != 0 && num == 0)
                        		num++;
                        	else {
                        		print_table(startTime.add(m) + "", e.getName() + "", e.getJob() + "", e.getPhoneNumber() + "");
                        		num++;
                        	}	
                        }
                    }
                }
            }
        }
    }

    /**
     * 根据值班时间给员工排序
     */
    private static List<Interval> sorting() {
        List<Interval> list = new ArrayList<>();
        assert employees != null;
        for(Employee e:employees){
            assert newDutyIntervalSet.intervals(e).labels() != null;
            for(Integer i:newDutyIntervalSet.intervals(e).labels()){
                list.add(new Interval(newDutyIntervalSet.intervals(e).start(i),newDutyIntervalSet.intervals(e).end(i)));
            }
        }
        list.sort(new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                return (int) o1.getStart() - (int) o2.getStart();
            }
        });
        return list;
    }

    /**
     *文件读取
     */
    public static void fileParse(String filename) throws IOException {
        long record = 0;
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String str;
        ArrayList<String> parseLine = new ArrayList<>();
        while ((str = br.readLine()) != null) {
            parseLine.add(str);
        }

        ArrayList<Employee> employeeArray = new ArrayList<>();

        for (String checkStr : parseLine) {
            if (checkStr.startsWith("Period")) {
                String period = "\\d{4}-\\d{2}-\\d{2}";
                Pattern patternPeriod = Pattern.compile(period);
                Matcher matPeriod = patternPeriod.matcher(checkStr);
                ArrayList<String> periodTime = new ArrayList<>();
                while (matPeriod.find()) {
                    periodTime.add(matPeriod.group());
                }
                startTime = toTime(periodTime.get(0));
                endTime = toTime(periodTime.get(1));
                assert endTime != null;
                newDutyIntervalSet = new NewDutyIntervalSet<Employee>(
                        0, endTime.toLong() - startTime.toLong()
                );
                break;
            }
        }

        for (String parseString : parseLine) {
            String employee = "[a-zA-Z]+";
            Pattern patternEmployee = Pattern.compile(employee);
            Matcher matEmployee = patternEmployee.matcher(parseString);
            String name = null;
            String job = null;
            ArrayList<String> employeeList = new ArrayList<>();
            while (matEmployee.find()) {
                employeeList.add(matEmployee.group());
            }

            if (employeeList.size() > 1) {
                name = employeeList.get(0);
                job = employeeList.get(1);
                String employeeNumber = "\\d{3}-\\d{4}-\\d{4}";
                Pattern patternEmployeeNumber = Pattern.compile(employeeNumber);
                Matcher matEmployeeNumber = patternEmployeeNumber.matcher(parseString);
                String tempNumber = null;
                if (matEmployeeNumber.find()) {
                    tempNumber = matEmployeeNumber.group();
                }

                StringBuilder convertNumber = new StringBuilder();
                if(tempNumber != null) {
                    String[] numberArray = tempNumber.split("-");
                    for (String s : numberArray) {
                        convertNumber.append(s);
                    }
                    employeeArray.add(new Employee(name, job, convertNumber.toString()));
                }
            }

            if (employeeList.size() == 1 && !employeeList.get(0).equals("Roster") && !employeeList.get(0).equals("Employee")) {
                String employeeName = employeeList.get(0);
                String parseTime = "\\d{4}-\\d{2}-\\d{2}";
                Pattern patternTime = Pattern.compile(parseTime);
                Matcher matTime = patternTime.matcher(parseString);
                gui.DutyRoster.MyDate st;
                gui.DutyRoster.MyDate et;
                ArrayList<String> time = new ArrayList<>();
                while (matTime.find()) {
                    time.add(matTime.group());
                }
                st = toTime(time.get(0));
                et = toTime(time.get(1));
                for (Employee emp : employeeArray) {
                    if (emp.getName().equals(employeeName)) {
                        assert st != null;
                        assert et != null;
                        if(st.toLong() - startTime.toLong() == 0) {
                            newDutyIntervalSet.insert(st.toLong() - startTime.toLong(), et.toLong() - startTime.toLong(), emp);
                        }
                        else {
                            newDutyIntervalSet.insert(st.toLong() - startTime.toLong() - 1, et.toLong() - startTime.toLong(), emp);
                        }
                    }
                }
            }
        }
        if(!newDutyIntervalSet.checkNoBlank())
        {
            System.out.println("非法文件！读取结束！");
            return;
        }
        printTable();
    }

    static void print_table(String date, String name, String job, String phoneNumber) {
        int size = 16;
        String[] table = {date,name, job, phoneNumber};
        for (int i = 0; i < 4; ++i) {
            int len = table[i].length();
            int left_space =  (size-len)%2==0 ?(size-len)/2 :(size-len)/2+1 ;
            int right_space =    (size-len)/2    ;
            for (int j = 0; j < left_space+3; ++j) {
                System.out.print(" ");
            }
            System.out.print(table[i]);
            for (int j = 0; j < right_space+3; ++j) {
                System.out.print(" ");
            }
        }
        System.out.print("\n");
    }


    /**
     * 自动排班
     */
    private static void autoArranging(){
        int num = 0 ;
        boolean flag = false;
        Random r = new Random();
        long temp = 0;
        long st = 0;
        do {
            for (Employee e : employees) {
                if(num + 1 == employees.size() && st < endTime.toLong() - startTime.toLong())
                {
                    newDutyIntervalSet.insert(st, endTime.toLong() - startTime.toLong(),e);
                    flag = true;
                    break;
                }
                num++;
                assert endTime.toLong() >= startTime.toLong();
                temp += r.nextInt((int) endTime.toLong() - (int) startTime.toLong() - (int) temp + 1);
                if(temp > endTime.toLong() - startTime.toLong())
                    temp = endTime.toLong() - startTime.toLong();
                newDutyIntervalSet.insert(st, temp, e);
                st = temp;
                if (temp == endTime.toLong() - startTime.toLong()) {
                    flag = true;
                    break;
                }
            }
        } while (!flag);
        printTable();
    }

}

