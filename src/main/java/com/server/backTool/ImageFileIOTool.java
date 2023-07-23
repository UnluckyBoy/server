package com.server.backTool;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName ImageFileIOTool
 * @Author Create By matrix
 * @Date 2023/5/15 0015 14:30
 */
public class ImageFileIOTool {
    /***
     * 写入图片文件到后台物理位置
     * @param system_Path
     * @param user_info_Path
     * @param filename
     * @param file
     * @return
     */
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

    /**
     * 写入多个图片到后台物理位置
     * @param system_Path
     * @param user_info_Path
     * @param files
     * @return
     */
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

    /**
     * 从后台物理位置中将图片删除
     * @param system_Path
     * @param user_info_Path
     * @param filename
     */
    public static void deleteImage(String system_Path,String user_info_Path,String filename){
        File delete_cache=new File(system_Path+user_info_Path+filename);
        if(delete_cache.exists()){
            delete_cache.delete();
            System.out.println(TimeTool.GetTime(true)+"\t删除成功");
        }else{
            System.out.println(TimeTool.GetTime(true)+"\t删除异常");
        }
    }

    /**
     * 将图片的byte数组写入后台位置
     * @param bytes
     * @param file_Path_Name
     * @throws IOException
     */
    public static boolean writeByteArrayToFile(byte[] bytes,String file_Path_Name) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file_Path_Name);
            fos.write(bytes);
            System.out.println("图片写入成功!"+file_Path_Name);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }
}
