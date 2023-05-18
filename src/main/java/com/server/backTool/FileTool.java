package com.server.backTool;

import java.io.*;

/**
 * @ClassName FileTool
 * @Author Create By matrix
 * @Date 2023/5/12 0012 19:54
 */
public class FileTool {
    /**
     * 读取文件
     * @param filePath
     * @return
     */
    public static String ReadFile(String filePath){
        //Map<String ,Object> map=new HashMap<String, Object>();
        StringBuffer stringBuffer=new StringBuffer();
        try {
            String encoding = "UTF-8";
            File bookfile = new File(filePath);
            if (bookfile.isFile() && bookfile.exists())//是文件而且存在
            {
                InputStreamReader read = new InputStreamReader(new FileInputStream(
                        bookfile), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                //System.out.print("文件内容：");
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    //System.out.println(lineTxt);//原样输出读到的内容
                    //System.out.println(lineTxt);
                    if(!(lineTxt.equals(" "))){
                        stringBuffer.append(lineTxt+"\n");
                    }

                }
                read.close();
                //return new String(stringBuffer);
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return new String(stringBuffer);
    }


    public static boolean WriteContent(String filePath,String content){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
            System.out.println(TimeTool.GetTime(true)+"\t字符串写入成功。");
            return true;
        } catch (IOException e) {
            System.out.println(TimeTool.GetTime(true)+"\t字符串写入失败。");
            e.printStackTrace();
            return false;
        }
    }
}
