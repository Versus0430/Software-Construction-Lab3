package adt;

import java.util.Objects;

public class Process {
    private final String processID;
    private final String processName;
    private final long minTime;
    private final long maxTime;
    private long runningTime; //进程已运行的时间
    private boolean flag; //进程是否运行完毕的标志

    /**
     * 构造器
     * @param processID 进程ID
     * @param processName 进程名
     * @param minTime 最短执行时间
     * @param maxTime 最长执行时间
     */
    public Process(String processID, String processName, long minTime, long maxTime) {
        this.processID = processID;
        this.processName = processName;
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.runningTime = 0;
        this.flag = false;
    }

    /**
     * 返回进程ID
     * @return 进程ID
     */
    public String getProcessID() {
        return processID;
    }

    /**
     * 返回进程名
     * @return 进程名
     */
    public String getProcessName() {
        return processName;
    }

    /**
     * 返回最短执行时间
     * @return 最短执行时间
     */
    public long getMinTime() {
        return minTime;
    }

    /**
     * 返回最长执行时间
     * @return 最长执行时间
     */
    public long getMaxTime() {
        return maxTime;
    }

    /**
     * 返回已运行时间
     * @return 已运行时间
     */
    public long getRunningTime() { return runningTime; }

    /**
     * 设置进程运行结束的标志
     */
    public void setFlag() { this.flag = true;}

    /**
     * 重新设置标记
     */
    public void resetFlag() { this.flag = false; }

    /**
     * 设置进程已运行的时间
     */
    public void setRunningTime(long time) {
        runningTime += time;
    }

    /**
     * 返回是否执行结束的标志
     * @return 是否执行结束的标志，若已结束，则返回true,否则返回false
     */
    public boolean getFlag() { return flag; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Process process = (Process) o;
        return minTime == process.minTime && maxTime == process.maxTime && runningTime == process.runningTime && flag == process.flag && Objects.equals(processID, process.processID) && Objects.equals(processName, process.processName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(processID, processName, minTime, maxTime, runningTime, flag);
    }
}
