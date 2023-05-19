package com.server.backTool;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

    public static boolean writeImages(String system_Path, String user_info_Path,List<MultipartFile> files){
        try {
            for(int i=0;i<files.size();i++){
                files.get(i).transferTo(new File(system_Path+user_info_Path+files.get(i).getOriginalFilename()));
            }
            System.out.println(TimeTool.GetTime(true)+"\t上传文件成功");
            return true;
        } catch (IOException e) {
            System.out.println(TimeTool.GetTime(true)+"\t上传异常:"+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static void deleteImage(String system_Path,String user_info_Path,String filename){
        File delete_cache=new File(system_Path+user_info_Path+filename);
        if(delete_cache.exists()){
            delete_cache.delete();
            System.out.println(TimeTool.GetTime(true)+"\t删除成功");
        }else{
            System.out.println(TimeTool.GetTime(true)+"\t删除异常");
        }
    }
}
