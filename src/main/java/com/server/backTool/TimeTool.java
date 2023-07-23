package com.server.backTool;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 封装时间类
 */
public class TimeTool {
    /**
     *
     * @param add_hour 是否添加小时
     * @return
     */
    public static String GetTime(boolean add_hour){
        //获取当前系统时间
        long time=System.currentTimeMillis();
        //new日期对象
        Date date =new Date(time);
        //转换提日期输出格式
        if(add_hour){
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String st = dateFormat.format(date);
            return st;
        }else{
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd");
            String st = dateFormat.format(date);
            return st;
        }
    }

    public static String formatTime(String time){
        return time.replaceAll("[- :]","");//通过replaceAll将"-"、" "、":"替换为""
    }
}
