package gui.DutyRoster;

public class MyDate {
    private final long year;
    private final long month;
    private final long day;

    public MyDate(String line) throws MyTimeException {
        String[] date = line.split("-");
        if (date.length != 3) {
            throw new MyTimeException("格式错误");
        }
        for (String d : date) {
            if (!d.matches("[0-9]+")) {
                throw new MyTimeException("输入非自然数");
            }
        }
        int[] dateInt = new int[3];
        for (int i = 0; i < 3; i++) {
            dateInt[i] = Integer.parseInt(date[i]);
        }
        if (dateInt[0] < 1000 || dateInt[0] > 9999) {
            throw new MyTimeException("输入年份不正确（" + dateInt[0] + "）");
        }
        if (dateInt[1] <= 0 || dateInt[1] > 12) {
            throw new MyTimeException("输入月份不正确（" + dateInt[1] + "）");
        }
        if (dateInt[1] == 1 || dateInt[1] == 3 || dateInt[1] == 5 || dateInt[1] == 7 || dateInt[1] == 8 ||
                dateInt[1] == 10 || dateInt[1] == 12) {
            if (dateInt[2] <= 0 || dateInt[2] > 31)
                throw new MyTimeException("输入日期不正确 (" + dateInt[2] + ")");
        }
        if (dateInt[1] == 4 || dateInt[1] == 6 || dateInt[1] == 9 || dateInt[1] == 11) {
            if (dateInt[2] <= 0 || dateInt[2] > 30)
                throw new MyTimeException("输入日期不正确 (" + dateInt[2] + ")");
        }
        if ((dateInt[0] % 4 == 0 && dateInt[0] % 100 != 0) || dateInt[0] % 400 == 0) {
            if (dateInt[1] == 2 && (dateInt[2] <= 0 || dateInt[2] > 29))
                throw new MyTimeException("输入日期不正确 (" + dateInt[2] + ")");
        } else {
            if (dateInt[1] == 2 && (dateInt[2] <= 0 || dateInt[2] > 28))
                throw new MyTimeException("输入日期不正确 (" + dateInt[2] + ")");
        }
        this.year = (long) dateInt[0];
        this.month = (long) dateInt[1];
        this.day = (long) dateInt[2];
    }

    /**
     * 将yyyy-mm-dd格式转换为long数据类型
     */
    public long toLong() {
        long res = 0;
        for(long i=1; i<this.month; i++) {
            if (((this.year % 4 == 0 && this.year % 100 != 0)) || this.year % 400 == 0) {
                if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12)
                    res += 31;
                else if (i == 4 || i == 6 || i == 9 || i == 11)
                    res += 30;
                else if (i == 2)
                    res += 29;
            } else {
                if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12)
                    res += 31;
                else if (i == 4 || i == 6 || i == 9 || i == 11)
                    res += 30;
                else if (i == 2)
                    res += 28;
            }
        }
        res += this.day;
        return res;
    }

    /**
     * 给定当前员工的时间段长度，由此计算出其yyyy-mm-dd
     */
    public String add(long time) {
        String s = "";
        long temp = 0;
        if (((this.year % 4 == 0 && this.year % 100 != 0)) || this.year % 400 == 0) {
            if (this.month == 3 || this.month == 5 || this.month == 8 ||
                    this.month == 10 || this.month == 12) {
                temp = time + this.day;
                if (temp > 31 && temp <= 61 )
                    s += this.year + "-" + (this.month + 1) + "-" + (temp - 31);
                else if(temp >= 0 && temp <= 31)
                    s += this.year + "-" + this.month + "-" + temp;
                else if(temp > 61)
                    s += this.year + "-" + (this.month + 2) + "-" + (temp - 61);
            } else if (this.month == 4 || this.month == 6 || this.month == 9 || this.month == 11) {
                temp = time + this.day;
                if (temp > 30 && temp <= 61 )
                    s += this.year + "-" + (this.month + 1) + "-" + (temp - 30);
                else if(temp >= 0 && temp <= 30)
                    s += this.year + "-" + this.month + "-" + temp;
                else if(temp > 61)
                    s += this.year + "-" + (this.month + 2) + "-" + (temp - 61);
            } else if (this.month == 2) {
                temp = time + this.day;
                if (temp > 29 && temp <= 60 )
                    s += this.year + "-" + (this.month + 1) + "-" + (temp - 29);
                else if(temp >= 0 && temp <= 29)
                    s += this.year + "-" + this.month + "-" + temp;
                else if(temp > 60)
                    s += this.year + "-" + (this.month + 2) + "-" + (temp - 60);
            } else if (this.month == 1) {
                temp = time + this.day;
                if (temp > 31 && temp <= 60 )
                    s += this.year + "-" + (this.month + 1) + "-" + (temp - 31);
                else if(temp >= 0 && temp <= 31)
                    s += this.year + "-" + this.month + "-" + temp;
                else if(temp > 60)
                    s += this.year + "-" + (this.month + 2) + "-" + (temp - 60);
            } else if (this.month == 7) {
                temp = time + this.day;
                if (temp > 31 && temp <= 62 )
                    s += this.year + "-" + (this.month + 1) + "-" + (temp - 31);
                else if(temp >= 0 && temp <= 31)
                    s += this.year + "-" + this.month + "-" + temp;
                else if(temp > 62)
                    s += this.year + "-" + (this.month + 2) + "-" + (temp - 62);
            }
        } else {
            if (this.month == 3 || this.month == 5 || this.month == 8 ||
                    this.month == 10 || this.month == 12) {
                temp = time + this.day;
                if (temp > 31 && temp <= 61 )
                    s += this.year + "-" + (this.month + 1) + "-" + (temp - 31);
                else if(temp >= 0 && temp <= 31)
                    s += this.year + "-" + this.month + "-" + temp;
                else if(temp > 61)
                    s += this.year + "-" + (this.month + 2) + "-" + (temp - 61);
            } else if (this.month == 4 || this.month == 6 || this.month == 9 || this.month == 11) {
                temp = time + this.day;
                if (temp > 30 && temp <= 61 )
                    s += this.year + "-" + (this.month + 1) + "-" + (temp - 30);
                else if(temp >= 0 && temp <= 30)
                    s += this.year + "-" + this.month + "-" + temp;
                else if(temp > 61)
                    s += this.year + "-" + (this.month + 2) + "-" + (temp - 61);
            } else if (this.month == 2) {
                temp = time + this.day;
                if (temp > 28 && temp <= 59 )
                    s += this.year + "-" + (this.month + 1) + "-" + (temp - 28);
                else if(temp >= 0 && temp <= 28)
                    s += this.year + "-" + this.month + "-" + temp;
                else if(temp > 59)
                    s += this.year + "-" + (this.month + 2) + "-" + (temp - 59);
            } else if (this.month == 1) {
                temp = time + this.day;
                if (temp > 31 && temp <= 59 )
                    s += this.year + "-" + (this.month + 1) + "-" + (temp - 31);
                else if(temp >= 0 && temp <= 31)
                    s += this.year + "-" + this.month + "-" + temp;
                else if(temp > 59)
                    s += this.year + "-" + (this.month + 2) + "-" + (temp - 59);
            } else if (this.month == 7) {
                temp = time + this.day;
                if (temp > 31 && temp <= 62 )
                    s += this.year + "-" + (this.month + 1) + "-" + (temp - 31);
                else if(temp >= 0 && temp <= 31)
                    s += this.year + "-" + this.month + "-" + temp;
                else if(temp > 62)
                    s += this.year + "-" + (this.month + 2) + "-" + (temp - 62);
            }
        }
        return s;
    }
}



