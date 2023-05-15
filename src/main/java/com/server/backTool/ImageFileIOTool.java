package com.server.backTool;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName ImageFileIOTool
 * @Author Create By matrix
 * @Date 2023/5/15 0015 14:30
 */
public class ImageFileIOTool {
    public static boolean writeImage(String system_Path,String user_info_Path,String filename,MultipartFile file){
        try {
            file.transferTo(new File(system_Path+user_info_Path+filename));
            System.out.println(TimeTool.GetTime(true)+"\t上传文件成功:"+filename);
            return true;
        } catch (IOException e) {
            System.out.println(TimeTool.GetTime(true)+"\t上传异常:"+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
