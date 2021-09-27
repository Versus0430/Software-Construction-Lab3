package gui.ProcessSchedule;

import adt.CommonMultiIntervalSet;
import adt.Process;
import adt.impl.ProcessIntervalSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ProcessScheduleApp {
    private static ProcessIntervalSet<Process> processIntervalSet;
    private static List<Process> processes;
    private static long maxTime;
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        processIntervalSet = new ProcessIntervalSet<>();
        processes = new ArrayList<>();
        maxTime = 0;
        System.out.println("————————欢迎访问操作系统的进程调度管理系统!");
        System.out.println();
        Menu();
    }

    private static void Menu() {
        int choice = 1;
        while (choice != 0 && choice != 2 && choice != 3) {
            System.out.println("------------------");
            System.out.println("操作菜单:");
            System.out.println("1. 增加一组进程");
            System.out.println("2. 随机选择策略");
            System.out.println("3. 最短进程优先策略");
            System.out.println("0. 退出操作");
            System.out.println("------------------");
            System.out.print("请键入操作号:");
            choice = Integer.parseInt(scanner.nextLine());
            System.out.println();
            switch (choice) {
                case 1:
                    addProcess();
                    System.out.println();
                    break;
                case 2:
                    randomRun();
                    System.out.println();
                    System.out.println("操作结束！欢迎再次访问！");
                    break;
                case 3:
                    bestRun();
                    System.out.println();
                    System.out.println("操作结束！欢迎再次访问！");
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
     * 增加一组进程
     */
    private static void addProcess(){
        while(true) {
            System.out.println("请输入一组进程信息：");
            System.out.println("进程ID \t名称 \t最短执行时间\t最长执行时间 (以空格间隔)");
            String line = scanner.nextLine();
            String[] info = line.trim().split(" ");

            if(info.length < 4)
            {
                System.out.println("输入错误！请重新输入！");
                continue;
            }
            Process p = new Process(info[0],info[1],(long)Integer.parseInt(info[2]),(long)Integer.parseInt(info[3]));
            if((long)Integer.parseInt(info[3]) > maxTime)
                maxTime = (long)Integer.parseInt(info[3]);
            processes.add(p);
            System.out.println("是否继续添加？");
            String ans = scanner.nextLine();
            if(ans.equals("n") || ans.equals("N"))
            {
                System.out.println("添加结束");
                return;
            }
        }
    }

    /**
     * 随机选择进程
     */
    private static void randomRun(){
        boolean allFlags = false; //检测所有进程是否已运行完毕
        long alreadyRun = 0; //每次运行时间（检测每次随机分配的时间是否运行完）
        long tempTime = 0;
        long start_time = 0;
        long end_time = 0;
        int probability = 0;
        int size = processes.size();
        Random r1 = new Random();
        Random r2 = new Random();
        Random r3 = new Random();
        while(!allFlags) {
            end_time = (long) r1.nextInt((int)maxTime) + 1;
            alreadyRun = 0;
            while(true) {
                tempTime += (long) r3.nextInt((int) (end_time - alreadyRun)) + 1;
                probability = r2.nextInt(size + 1); //生成随机数范围：[0,size]
                if (probability == 0) //不执行任何进程
                {
                    alreadyRun += tempTime - start_time;
                    start_time = tempTime;
                    if(alreadyRun == end_time)
                        break;
                }
                else
                {
                    if((tempTime - start_time) + processes.get(probability-1).getRunningTime() >= processes.get(probability-1).getMinTime())
                    {
                        processes.get(probability-1).setRunningTime(processes.get(probability-1).getMinTime() - processes.get(probability-1).getRunningTime());
                        processes.get(probability-1).setFlag();
                    }
                    else
                    {
                        int x = probability - 1;
                        processIntervalSet.insert(start_time, tempTime, processes.get(x)); //运行进程i
                        processes.get(x).setRunningTime(tempTime - start_time);
                    }
                    alreadyRun += tempTime - start_time;
                    start_time = tempTime;
                    if(alreadyRun == end_time)
                        break;
                }
            }
            System.out.println();
            System.out.println("系统调度中...");
            System.out.println("查看当前时刻的进程调度？(y/n)");
            String ans = scanner.nextLine();
            if(ans.equals("y") || ans.equals("Y"))
            {
                printProcess();
                System.out.println("当前正在执行的进程：");
                if(probability == 0)
                    System.out.println("\t当前未执行任何进程！");
                else
                    System.out.println("\t进程ID： " + processes.get(probability - 1).getProcessID() + "\t进程名： " + processes.get(probability - 1).getProcessName() + "\t进程执行度： " + (processes.get(probability - 1).getRunningTime() * 100) / processes.get(probability - 1).getMinTime() + "%");
            }
            allFlags = true;
            for (Process process : processes) {
                if (!process.getFlag())
                    allFlags = false;
            }
        }
        System.out.println();
        System.out.println("进程调度结束！");
    }

    /**
     * 可视化当前进程
     */
    private static void printProcess(){
        System.out.println();
        System.out.println("-----------当前时刻之前的进程调度结果：");
        System.out.println("进程ID\t\t┃ 进程名\t\t┃ 进程运行度");
        for(int i=0; i<processes.size(); i++)
        {
            System.out.println(processes.get(i).getProcessID() + "   \t\t┃ " + processes.get(i).getProcessName() + " \t\t┃ " + (processes.get(i).getRunningTime() * 100) / processes.get(i).getMinTime() + "%");
        }
    }

    /**
     * 最短进程优先运行
     */
    private static void bestRun(){
        boolean allFlags = false; //检测所有进程是否已运行完毕
        long alreadyRun = 0; //每次运行时间（检测每次随机分配的时间是否运行完）
        long tempTime = 0;
        long start_time = 0;
        long end_time = 0;
        int probability = 0;
        int size = processes.size();
        Random r1 = new Random();
        Random r2 = new Random();
        Random r3 = new Random();
        while(!allFlags) {
            end_time = (long) r1.nextInt((int)maxTime) + 1;
            alreadyRun = 0;
            while(true) {
                tempTime += (long) r3.nextInt((int) (end_time - alreadyRun)) + 1;
                probability = checkProcess(); // 根据最短进程优先策略选择当前运行进程

                if((tempTime - start_time) + processes.get(probability).getRunningTime() >= processes.get(probability).getMinTime())
                {
                    processes.get(probability).setRunningTime(processes.get(probability).getMinTime() - processes.get(probability).getRunningTime());
                    processes.get(probability).setFlag();
                }
                else
                {
                    int x = probability;
                    processIntervalSet.insert(start_time, tempTime, processes.get(x)); //运行进程i
                    processes.get(x).setRunningTime(tempTime - start_time);
                }
                alreadyRun += tempTime - start_time;
                start_time = tempTime;
                if(alreadyRun == end_time)
                    break;
                System.out.println();
                System.out.println("系统调度中...");
                System.out.println("查看当前时刻的进程调度？(y/n)");
                String ans = scanner.nextLine();
                if(ans.equals("y") || ans.equals("Y"))
                {
                    printProcess();
                    if(!processes.get(probability).getFlag()) {
                        System.out.println("当前正在执行的进程：");
                        System.out.println("\t进程ID： " + processes.get(probability).getProcessID() + "\t进程名： " + processes.get(probability).getProcessName() + "\t进程执行度： " + (processes.get(probability).getRunningTime() * 100) / processes.get(probability).getMinTime() + "%");
                    }
                    else
                        System.out.println("\t当前未执行任何进程！");
                }
            }

            allFlags = true;
            for (Process process : processes) {
                if (!process.getFlag()) {
                    allFlags = false;
                    break;
                }
            }
        }
        System.out.println();
        System.out.println("进程调度结束！");
    }

    /**
     * 检查当前所有进程的执行情况，返回距离其最大执行情况最近的进程号
     */
    private static int checkProcess(){
        int process = 0;
        long minDistance = maxTime;
        for(int i=0; i<processes.size(); i++)
        {
            if(!processes.get(i).getFlag() && ((processes.get(i).getMaxTime() - processes.get(i).getRunningTime()) <= minDistance)){
                minDistance = processes.get(i).getMaxTime() - processes.get(i).getRunningTime();
                process = i;
            }
        }
        minDistance = maxTime;
        return process;
    }
}
