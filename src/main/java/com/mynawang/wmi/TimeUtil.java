package com.mynawang.wmi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @auther mynawang
 * @create 2017-07-16 16:19
 */
public class TimeUtil {

    /**一秒**/
    public static final int SECOND = 1000;
    /**一分钟**/
    public static final int MINUTE = 60 * SECOND;
    /**一小时**/
    public static final int HOUR  = 60 * MINUTE;
    /**一天**/
    public static final int DAY = 24 *  HOUR;
    /**一个星期**/
    public static final int WEEK= 7 * DAY;

    public static Long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * 获得yyyyMMddHHmmss格式的当前时间
     * @return
     */
    public static String getTimeSimple() {
        return getTimeByFormat("yyyyMMddHHmmss");
    }
    /**
     * 获得yyyyMM格式的当前时间
     * @return
     */
    public static String getTimeShort() {
        return getTimeByFormat("yyyyMM");
    }

    /**
     * 获得yyyyMMdd格式的当前时间
     * @return
     */
    public static String getTimeDay() {
        return getTimeByFormat("yyyyMMdd");
    }

    /**
     * 获得yyyy-MM-dd HH:mm:ss格式的当前时间
     * @return
     */
    public static String getTimeAll(){
        return getTimeByFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获得yyyy-MM-dd格式的当前时间
     * @return
     */
    public static String getTimeDaySimple(){
        return getTimeByFormat("yyyy-MM-dd");
    }

    public static Date getTimeDate(){
        return StringToDate3(getTimeSimple());
    }

    /**
     * 获得yyyy-MM-dd格式的时间，传入参数格式为yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getTimeDateByAll(String time){
        return changeFormat(time, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd");
    }

    /**
     * 获得yyyyMMdd格式的时间，传入参数格式为yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getTimeDateByAll2(String time){
        return changeFormat(time, "yyyy-MM-dd HH:mm:ss", "yyyyMMdd");
    }

    public static String getTimeDateByAll3(String time){
        return changeFormat(time, "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss");
    }

    public static String getTimeDateByAll4(String time){
        return changeFormat(time, "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss");
    }


    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /*
    * 将时间戳转换为时间
    */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 根据时间格式获得当前时间
     * @param format 格式，如yyyyMMddHHmmss
     * @return
     */
    public static String getTimeByFormat(String format) {
        Date nowDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String  nowtime=sdf.format(nowDate);
        return nowtime;
    }

    /**
     *
     * @Title: getDateAfterDay
     * @Description: 获取几天后的时间
     * @param @param d
     * @param @param day
     * @param @return
     * @param @throws ParseException
     * @return String
     */
    public static String getDateAfterDay(String d, int day) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = sdf.parse(d);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DATE, day);
        return sdf.format(calendar.getTime());
    }

    /**将字符串时间改为Date类型的时间
     * @param s yyyy/MM/dd HH:mm:ss
     * @return
     */
    public static Date StringToDate1(String s) {
        return StringToDate(s, "yyyy/MM/dd HH:mm:ss");
    }
    /**将字符串时间改为Date类型的时间
     * @param s yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date StringToDate2(String s) {
        return StringToDate(s, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date StringToDate3(String s)
    {
        return StringToDate(s, "yyyyMMddHHmmss");
    }

    /**字符串时间转化为Date类型
     * @param s 时间字符串
     * @param format s的时间格式
     * @return
     */
    public static Date StringToDate(String s,String format){
        try {
            Date time = new Date();
            SimpleDateFormat sd = new SimpleDateFormat(format);
            time = sd.parse(s);
            return time;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**增减天数
     * @param date 指定的时间
     * @param num 要变化的天数，可以为负数
     * @return
     */
    public static Date addDay(Date date, int num) {
        try {
            Calendar startDT = Calendar.getInstance();
            startDT.setTime(date);
            startDT.add(Calendar.DAY_OF_MONTH, num);
            return startDT.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 增加分钟
     * @param date
     * @param num
     * @return
     */
    public static Date addMinute(Date date,int num){
        try {
            Calendar startDT = Calendar.getInstance();
            startDT.setTime(date);
            startDT.add(Calendar.MINUTE, num);
            return startDT.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串时间更改时间
     * @param date
     * @param num
     * @return
     */
    public static String addDay(String date, int num) {
        try {
            String newDate = dateToString(addDay(StringToDate(date, "yyyy-MM-dd HH:mm:ss"), num), "yyyy-MM-dd HH:mm:ss");
            return newDate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串时间更改时间
     * @param date
     * @param num
     * @return
     */
    public static String addDaySimple(String date, int num) {
        try {
            String newDate = dateToString(addDay(StringToDate(date, "yyyy-MM-dd"), num), "yyyy-MM-dd");
            return newDate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     * 字符串增加分钟
     * @param date
     * @param num
     * @return
     */
    public static String addMinute(String date,int num){
        try {
            String newDate = dateToString(addMinute(StringToDate(date, "yyyy-MM-dd HH:mm:ss"), num), "yyyy-MM-dd HH:mm:ss");
            return newDate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * data 转string
     * @param date
     * @param format
     * @return
     */
    public static String dateToString(Date date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String  nowtime=sdf.format(date);
        return nowtime;
    }


    /**
     * 比较两个时间的大小
     * @param time1
     * @param time2
     * @return time1 比time 2 大，或者相等，则返回true
     */
    public static boolean compareTime(String time1,String time2){
        try {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1 = df.parse(time1);
            Date d2 = df.parse(time2);
            long diff = d1.getTime() - d2.getTime();
            return diff>=0?true:false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 比较两个时间的大小
     * @param time1
     * @param time2
     * @return time1 比time 2 大，则返回true
     */
    public static boolean compareTime2(String time1,String time2){
        try {
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            Date d1 = df.parse(time1);
            Date d2 = df.parse(time2);
            long diff = d1.getTime() - d2.getTime();
            return diff>=0?true:false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 比较两个时间的大小
     * @param time1
     * @param time2
     * @return time1 比time 2 大，则返回true
     */
    public static boolean compareDate(String time1,String time2) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = df.parse(time1);
            Date d2 = df.parse(time2);
            long diff = d1.getTime() - d2.getTime();
            return diff>=0?true:false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 比较两个时间的日期的大小
     * @param time1
     * @param time2
     * @return time1 比time 2 大，则返回true
     */
    public static boolean compareDate2(String time1, String time2) {
        try {
            String date1 = changeFormat(time1, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd");
            String date2 = changeFormat(time2, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = df.parse(date1);
            Date d2 = df.parse(date2);
            long diff = d1.getTime() - d2.getTime();
            return diff>=0?true:false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 比较两个时间的大小
     * @param time1
     * @param time2
     * @return time1 比time 2 大，则返回true
     */
    public static boolean compareHour(String time1, String time2){
        try {
            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            Date d1 = df.parse(time1);
            Date d2 = df.parse(time2);
            long diff = d1.getTime() - d2.getTime();
            return diff>=0?true:false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 比较时间的时间点是否在该区间（闭区间）
     * @param compareTime：yyyy-MM-dd HH:mm:ss
     * @param range1：HH:mm:ss
     * @param range2：HH:mm:ss
     * @return compareTime在区间 [range1, range2] 内，则返回true
     */
    public static boolean compareHourRange(String compareTime, String range1, String range2)
    {
        String rangeHour = changeFormat(compareTime, "yyyy-MM-dd HH:mm:ss", "HH:mm:ss");
        if (compareHour(rangeHour, range1)
                && compareHour(range2, rangeHour))
        {
            return true;
        }
        return false;
    }


    /**
     * 比较时间的时间点是否在该区间（闭区间）
     * @param compareTime：yyyy-MM-dd HH:mm:ss
     * @param range1：yyyy-MM-dd HH:mm:ss
     * @param range2：yyyy-MM-dd HH:mm:ss
     * @return compareTime在区间 [range1, range2] 内，则返回true
     */
    public static boolean compareAllRange(String compareTime, String range1, String range2)
    {
        if (compareTime(compareTime, range1)
                && compareTime(range2, compareTime))
        {
            return true;
        }
        return false;
    }


    /**
     * 获得当天日期
     * @return
     */
    public static String getDate(){
        return getTimeByFormat("yyyy-MM-dd");
    }

    /**
     * 转化格式化
     * @param time
     * @param format1
     * @param format2
     * @return
     */
    public static String changeFormat(String time,String format1,String format2){
        try {
            return dateToString(StringToDate(time, format1), format2);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * @Title: totalSeconds
     * @Description: 计算两个时间之差，返回秒数
     * @param @param startTime
     * @param @param endTime
     * @param @return
     * @param @throws ParseException
     * @return long
     * @throws ParseException
     */
    public static long totalSeconds(String startTime, String endTime) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间
        Date outdate = sdf.parse(endTime);
        Date indate =  sdf.parse(startTime);
        long totalseconds = (outdate.getTime()-indate.getTime())/1000;		//秒
        return totalseconds;
    }

    /**
     * 取得两个时间段的时间间隔
     *
     * @author color
     * @param t1
     *            时间1
     * @param t2
     *            时间2
     * @return t2 与t1的间隔天数
     * @throws ParseException
     *             如果输入的日期格式不是0000-00-00 格式抛出异常
     */
    public static int getBetweenDays(String t1, String t2)
            throws ParseException {
        return getBetweenDaysByForMat(t1, t2, "yyyy-MM-dd");
    }

    /**
     * 根据格式取得两个时间段的时间间隔
     *
     * @author color
     * @param t1
     *            时间1
     * @param t2
     *            时间2
     * @return t2 与t1的间隔天数
     * @throws ParseException
     *             如果输入的日期格式不是forMat 格式抛出异常
     */
    public static int getBetweenDaysByForMat(String t1, String t2, String forMat)
            throws ParseException {
        DateFormat format = new SimpleDateFormat(forMat);
        int betweenDays = 0;
        Date d1 = format.parse(t1);
        Date d2 = format.parse(t2);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        // 保证第二个时间一定大于第一个时间
        if (c1.after(c2)) {
            c1 = c2;
            c2.setTime(d1);
        }
        int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        betweenDays = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
        /*System.err.println(betweenDays);
        System.out.println(c2.get(Calendar.DAY_OF_YEAR));
        System.err.println(c1.get(Calendar.DAY_OF_YEAR));*/
        for (int i = 0; i < betweenYears; i++) {
            betweenDays += countDays(c1.get(Calendar.YEAR));
            c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
        }
        return betweenDays;
    }

    public static int countDays(int year){
        int n=0;
        for (int i = 1; i <= 12; i++) {
            n += countDays(i,year);
        }
        return n;
    }


    public static int countDays(int month, int year){
        int count = -1;
        switch(month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                count = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                count = 30;
                break;
            case 2:
                if(year % 4 == 0)
                    count = 29;
                else
                    count = 28;
                if((year % 100 ==0) & (year % 400 != 0))
                    count = 28;
        }
        return count;
    }

    /**
     * 返回年份
     * @return
     */
    public static String getCurrentYear(){

        return getFormatDate("yyyy");
    }
    /**
     * 返回月份
     */
    public static String getCurrentMonth(){
        return getFormatDate("MM");
    }
    /**
     * 返回特定格式的日期
     * 格式如下:
     * yyyy-mm-dd
     * @param formatString
     * @return
     */
    public static String getFormatDate(String formatString){
        String currentDate="";
        SimpleDateFormat format1 = new SimpleDateFormat(formatString);
        currentDate = format1.format(new Date());
        return currentDate;
    }

    /**
     * 当月第一天
     * @return
     */
    public static String getFirstDay(Date theDate)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        return str.toString();
    }

    /**
     * 当月最后一天
     * @return
     */
    public static String getLastDay(Date theDate)
    {
        /*SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String s = df.format(theDate);
        StringBuffer str = new StringBuffer().append(s).append(" 23:59:59");
        return str.toString();*/

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.add(Calendar.MONTH, 1);		// 加一个月
        gcLast.set(Calendar.DATE, 1);		// 设置为下个月的第一天
        gcLast.add(Calendar.DATE, -1);    	// 再减一天即为这个月最后一天
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 23:59:59");
        return str.toString();
    }

    /**
     * 上个月第一天
     * @return
     */
    public static String getLastMonthFirstDay(Date theDate)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.add(Calendar.MONTH, -1);
        gcLast.set(Calendar.DAY_OF_MONTH,1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        return str.toString();
    }

    /**
     * 上个月最后一天
     * @return
     */
    public static String getLastMonthLastDay(Date theDate)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DATE, 1);     //设置为该月第一天
        gcLast.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        return str.toString();
    }

    /**
     * 该月最后一天
     * @return
     */
    public static String getMonthLastDay(Date theDate)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DATE, 1);     //设置为该月第一天
        gcLast.add(Calendar.MONTH, 1);    //再加一个月即为下个月第一天
        gcLast.add(Calendar.DATE, -1);    //再减一天即为该月最后一天
        return df.format(gcLast.getTime());
    }

    /**
     * 下月第一天
     * @return
     */
    public static String getNextMonthLastDay(Date theDate)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DATE, 1);     //设置为该月第一天
        gcLast.add(Calendar.MONTH, 1);    //再加一个月即为下个月第一天
        return df.format(gcLast.getTime());
    }

    /**
     *
     * @Title: getLastMonthStr
     * @Description: 以当前时间为准，获取上个月的时间，格式yyyyMM
     * @param @return
     * @return String
     */
    public static String getLastMonthStr()
    {
        Date nowDate = new Date();
        GregorianCalendar 	gcLast	= (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(nowDate);
        gcLast.add(Calendar.MONTH, -1);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
        String lastMonth = df.format(gcLast.getTime());
        return lastMonth;
    }

    /**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到秒的字符串
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds, String format)
    {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null"))
        {
            return "";
        }
        if(format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds+"000")));
    }


    /**
     * 日期格式字符串转换成时间戳
     * @param date_str 字符串日期
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str,String format)
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime()/1000);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取得当前时间戳（精确到秒）
     * @return
     */
    public static String getTimeStamp()
    {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time/1000);
        return t;
    }

    /**
     *
     * @Title: getTimeStamp2
     * @Description: 取得当前时间戳
     * @param @return
     * @return String
     */
    public static String getTimeStamp2()
    {
        long time = System.currentTimeMillis();
        return String.valueOf(time);
    }

    /**
     * 获取明天日期
     * @return
     */
    @SuppressWarnings("static-access")
    public static String getNextDay()
    {
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        date=calendar.getTime(); //这个时间就是日期往后推一天的结果
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 返回输入时间是星期几
     * @param time 输入时间
     * @param format 输入时间格式
     * @return 1-7 依次是星期天到星期六
     */
    public static int getDayOfWeekByParam(String time,String format)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(StringToDate(time, format));
        return  c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获得下一个星期几的日期
     * @param num 星期几
     * @return
     */
    public static String getEndDate(int num)
    {

        String time = addDaySimple(getTimeDaySimple(),7);
        int currentDate = getDayOfWeekByParam(time,"yyyy-MM-dd");
        int dirrer = num - currentDate;
        time = addDaySimple(time, dirrer);
        return time;

    }


    /**
     * 获得后几天的天数
     * @param days  后几天
     * @return
     */
    @SuppressWarnings("static-access")
    public static String getAfterDay(int days,String dateFormat)
    {
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,days);
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        date=calendar.getTime(); //这个时间就是日期往后推一天的结果
        String dateString = formatter.format(date);
        return dateString;
    }


    /**
     *
     * @Title: getNextWeekWed
     * @Description: 获取下一周的周三
     * @param @param time
     * @param @param format
     * @param @return
     * @return String
     */
	/*@SuppressWarnings("static-access")
	public static String getNextWeekWed(String time, String format)
	{
    	Date date = StringToDate(time, format);//取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, 7);
		calendar.set(Calendar.DAY_OF_WEEK, 4);
		return dateToString(calendar.getTime(), format);
	}*/

    /**
     *
     * @Title: getThisWeekTues
     * @Description: 获取本周周二的时间
     * @param @param time
     * @param @param format
     * @param @return
     * @return String
     */
    public static String getThisWeekTues(String time, String format)
    {
        Date date = StringToDate(time, format);//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, 3);
        return dateToString(calendar.getTime(), format);
    }

    /**
     * 时间（秒）截止到天
     * @param date
     * @return
     */
    public static String allTime2SimpleDay(String date) {
        try {
            String newDate = dateToString(StringToDate(date, "yyyy-MM-dd"), "yyyyMMdd");
            return newDate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 计算两个月份的相差值
     * @param startDate 小日期
     * @param endDate 大日期
     * @return
     * @throws ParseException
     */
    public static int getMonthBet(String startDate, String endDate)
            throws ParseException {
        int monthNum = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        startCalendar.setTime(sdf.parse(startDate));
        endCalendar.setTime(sdf.parse(endDate));
        int startYear = startCalendar.get(Calendar.YEAR);
        int endYear = endCalendar.get(Calendar.YEAR);
        int startMonth = startCalendar.get(Calendar.MONTH);
        int endMonth = endCalendar.get(Calendar.MONTH);
        monthNum = ((endYear - startYear) * 12 + (endMonth - startMonth));
        return monthNum;
    }

    /**
     * 计算两个日期的相差天数
     * @param startDate 小日期
     * @param endDate 大日期
     * @return
     * @throws ParseException
     */
    public static int getDaysBet(String startDate, String endDate) throws ParseException {
        int betDays = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        startCalendar.setTime(sdf.parse(startDate));
        endCalendar.setTime(sdf.parse(endDate));
        long timeStart = startCalendar.getTimeInMillis();
        long timeEnd = endCalendar.getTimeInMillis();
        long betweenDays=(timeEnd-timeStart)/(1000*3600*24);
        betDays = Integer.parseInt(String.valueOf(betweenDays));
        return betDays;
    }

    /**
     * 获取当月15号
     * @param giveDate 给出时间，如：2016-08-22
     * @param monthWhere 前后加月份 -1为减一月 0为本月 1为加1月
     * @return
     * @throws ParseException
     */
    public static String getMonthThe15Day(String giveDate, int monthWhere) throws ParseException {
        String day15OfMonth = getMonthTheDay(giveDate, monthWhere, 15);
        return day15OfMonth;
    }

    /**
     * 获取当月16号
     * @param giveDate
     * @param monthWhere
     * @return
     * @throws ParseException
     */
    public static String getMonthThe16Day(String giveDate, int monthWhere) throws ParseException {
        String day16OfMonth = getMonthTheDay(giveDate, monthWhere, 16);
        return day16OfMonth;
    }

    /**
     * 获取当月20号
     * @param giveDate
     * @param monthWhere
     * @return
     * @throws ParseException
     */
    public static String getMonthThe20Day(String giveDate, int monthWhere) throws ParseException {
        String day20OfMonth = getMonthTheDay(giveDate, monthWhere, 20);
        return day20OfMonth;
    }

    public static String getMonthTheDay(String giveDate, int monthNum, int monthDay) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calOld = Calendar.getInstance();
        calOld.setTime(sdf.parse(giveDate));
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, calOld.get(Calendar.YEAR));
        //设置月份
        cal.set(Calendar.MONTH, calOld.get(Calendar.MONTH) + monthNum);
        //设置日历中月份的第15天
        cal.set(Calendar.DAY_OF_MONTH, monthDay);
        String day15OfMonth = sdf.format(cal.getTime());
        return day15OfMonth;
    }


    public static void main(String[] args) throws ParseException {

        try {
            System.out.println(dateToStamp("2017-8-20 18:31:27"));

            System.out.println(stampToDate("1504088587228"));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Long timeNow = TimeUtil.getCurrentTime();
        System.out.println(timeNow);
        System.err.println(stampToDate(timeNow.toString()));
        Long timeBegin = timeNow + -7 * 86400000;
        System.out.println(timeBegin);
        System.err.println(stampToDate(timeBegin.toString()));
        System.out.println(TimeUtil.getTimeDateByAll3(TimeUtil.getDateAfterDay(TimeUtil.getTimeDateByAll4(timeNow.toString()), -7)));

       /* System.out.println(System.currentTimeMillis());

        Long timeNow = TimeUtil.getCurrentTime();

        Long timeBegin = Long.valueOf(TimeUtil.getTimeDateByAll3(TimeUtil.getDateAfterDay(TimeUtil.getTimeDateByAll4(timeNow.toString()), -7)));


        System.out.println(timeBegin);*/
    }

}
