package adt;

import java.util.Objects;

public class Employee {
    private final String name;
    private final String job;
    private final String phoneNumber;

    /**
     * 构造器
     * @param name 姓名
     * @param job 职务
     * @param phoneNumber 手机号码
     */
    public Employee(String name, String job, String phoneNumber) {
        this.name = name;
        this.job = job;
        this.phoneNumber = phoneNumber;
    }

    /**
     * 返回姓名
     * @return 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 返回职务
     * @return 职务
     */
    public String getJob() {
        return job;
    }

    /**
     * 返回手机号码
     * @return 手机号码
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(name, employee.name) && Objects.equals(job, employee.job) && Objects.equals(phoneNumber, employee.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, job, phoneNumber);
    }
}
