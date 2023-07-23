package com.server.controller;

import com.server.backTool.ImageFileIOTool;
import com.server.backTool.TimeTool;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ImageController
 * @Author Create By matrix
 * @Date 2023/7/23 0023 16:19
 */
@Controller
@ResponseBody
@RequestMapping("/image")
public class ImageController {
    private final String system_temp_path=System.getProperty("user.dir")+"/BackResource/backserver/";
    private final String temp_file_path="compressTemp/";


    @PostMapping("/compress")
    public Map compressImage(@RequestParam("file") MultipartFile file,
                                @RequestParam("targetSize") long targetSize) throws IOException {
        System.out.println("获取的文件:" + file.getName() + "  大小:" + file.getSize() / 1024 + "(Kb)" + "  目标大小:" + targetSize + "(Kb)");

        //return null;
        Map<String, Object> resultMap = new HashMap<>();

        String originalFileName = file.getOriginalFilename();// 获取上传的文件名
        //String compressedImagePath = "path/to/your/destination/compressed_image.jpg";//指定压缩后的图片存放路径

        //String temp_name=TimeTool.formatTime(TimeTool.GetTime(true));
        //final String temp_file=system_temp_path+temp_file_path+temp_name+".jpg";//指定压缩后的图片存放路径
        //final String result_path=temp_file_path+temp_name+".jpg";
        final String temp_file = system_temp_path + temp_file_path + originalFileName;//指定压缩后的图片存放路径
        final String result_path = temp_file_path + originalFileName;

        // 初始压缩质量
        double outputQuality = 1.0;

        // 压缩图片直到达到目标文件大小
        while (outputQuality > 0.0) {
            File tempCompressedImageFile = new File(temp_file);
            Thumbnails.of(file.getInputStream())
                    .size(800, 600) // 设置压缩后的尺寸
                    .outputFormat("jpg") // 设置输出格式为 jpg
                    .outputQuality(outputQuality) // 设置压缩质量
                    .toFile(tempCompressedImageFile);

            // 检查压缩后图片的文件大小
            long compressedFileSize = tempCompressedImageFile.length();

            // 判断是否达到目标文件大小
            if (compressedFileSize <= targetSize) {
                // 将压缩后的图片移动到最终的存放路径
                String finalCompressedImagePath = temp_file;
                tempCompressedImageFile.renameTo(new File(finalCompressedImagePath));
                resultMap.put("result", finalCompressedImagePath);
                //return "Image compressed successfully: " + finalCompressedImagePath;
            }

            // 逐步调整压缩质量，减小文件大小
            outputQuality -= 0.1;
        }
        return resultMap;
    }
}
