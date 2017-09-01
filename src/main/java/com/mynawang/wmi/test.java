package com.mynawang.wmi;

/**
 * Created by mynawang on 2017/8/31 0031.
 */
public class test {


    public static void main(String[] args) {

        String detectTime = "20170831230903.246000-000";
        String[] times = detectTime.split("\\.");
        System.out.println(times.length);
        System.out.println(times[0]);
        Long timeLong = new Long(times[0]);


    }
}
