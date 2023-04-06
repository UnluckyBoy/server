package com.server.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.server.backTool.JsonUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import retrofit2.HttpException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * GPT控制层
 */
@Controller
@ResponseBody
@RequestMapping("/MatrixGPT")
public class MatrixGPTClass {

    private static final String mAPI_KEY= "Bearer API-Key";//API-Key
    /**
     *url
     * 对话:https://api.openai.com/v1/chat/completions
     * 绘画:https://api.openai.com/v1/images/generations
     */
    private static final String mOpenAI_URL = "https://api.openai.com/v1/chat/completions";
    /** 配置代理服务的ip 根据自己实际情况配置 */
    private static final String HOST = "127.0.0.1";
    /** 配置代理服务的端口 根据自己实际情况配置 */
    private static final int PORT = 7890;

    @RequestMapping( "/createImage")
    public String CreateImage(@RequestParam String imginfo){

        Map<String, Object> paramMap = new HashMap<>();
        List<Map<String, String>> dataList = new ArrayList<>();
        dataList.add(new HashMap<String, String>(){{
            put("role", "user");
            put("content", imginfo);
        }});
        paramMap.put("messages", dataList);
        paramMap.put("model", "gpt-3.5-turbo");
        paramMap.put("max_tokens",2048);
        paramMap.put("temperature",0.5);
        paramMap.put("top_p",1);
        paramMap.put("frequency_penalty",0);
        paramMap.put("presence_penalty",0);
        JSONObject message = null;

        try {
            String body = HttpRequest.post(mOpenAI_URL)
                    .header("Authorization", mAPI_KEY)
                    .header("Content-Type", "application/json")
                    .body(JsonUtils.toJson(paramMap))
                    .execute()
                    .body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            JSONArray choices = jsonObject.getJSONArray("choices");
            JSONObject result = choices.get(0,JSONObject.class,Boolean.TRUE);
            message = result.getJSONObject("message");
        } catch (HttpException e) {
            return "出现了异常";
        }
        return message.getStr("content");
    }
}
