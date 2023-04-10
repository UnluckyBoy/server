package com.server.controller;

import com.server.GptNetWork.GptTool;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * GPT控制层
 */
@Controller
@ResponseBody
@RequestMapping("/MatrixGPT")
public class MatrixGPTController {

    @RequestMapping( "/chat")
    public Map<String,String> Chat(@RequestParam String content) throws IOException {
        String mChatUrl = "https://api.openai.com/v1/chat/completions";
        Map<String,String> resultMap=new HashMap<>();
        resultMap.put("result",GptTool.ChatApi(content,mChatUrl));
        return resultMap;
    }

    @RequestMapping( "/createImage")
    public Map<String,String> CreateImage(@RequestParam String content) throws IOException {
        String mImageUrl = "https://api.openai.com/v1/images/generations";
        Map<String,String> resultMap=new HashMap<>();
        resultMap.put("result",GptTool.CreateImageApi(content,mImageUrl));
        return resultMap;
    }
}
