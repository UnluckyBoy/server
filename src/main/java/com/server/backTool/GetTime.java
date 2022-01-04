package com.server.backTool;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 封装时间类
 */
public class GetTime {
    public String GetTime(){
        //获取当前系统时间
        long time=System.currentTimeMillis();
        //new日期对象
        Date date =new Date(time);
        //转换提日期输出格式
        SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd");
        String st = dateFormat.format(date);
        return st;
    }
}
