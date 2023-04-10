package com.server.GptNetWork;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.server.backTool.JsonUtils;
import com.server.model.pojo.GptRequestBody.ChatBody;
import com.server.model.pojo.GptRequestBody.CreateImageBody;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName GptTool
 * @Author Create By Administrator
 * @Date 2023/4/10 0010 20:10
 */
public class GptTool {
    private static Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 1080));
    private static String apiKey = "Bearer 你的API-KEY";

    public static String ChatApi(String content,String url){

        /**
         * 整理请求body
         */
        ChatBody mChatBody=new ChatBody();
        mChatBody.getMessages();
        List<Map<String, String>> dataList = new ArrayList<>();
        Map<String,String> temp=new HashMap<>();
        temp.put("role","user");
        temp.put("content",content);
        dataList.add(temp);

        mChatBody.setModel("gpt-3.5-turbo");
        mChatBody.setMessages(dataList);

        JSONObject message = null;
        String body = HttpRequest.post(url)
                .setProxy(proxy)
                .header("Authorization", apiKey)
                .header("Content-Type", "application/json")
                .body(JsonUtils.toJson(mChatBody.GetChatMap()))//mChatBody.GetChatMap()返回值为map类型
                .execute()
                .body();
        JSONObject jsonObject = JSONUtil.parseObj(body);
        JSONArray choices = jsonObject.getJSONArray("choices");
        JSONObject result = choices.get(0, JSONObject.class, Boolean.TRUE);
        message = result.getJSONObject("message");

        System.out.println(message.getStr("content"));
        return message.getStr("content");
    }

    public static String CreateImageApi(String content,String url){
        CreateImageBody mCreateImageBody=new CreateImageBody();
        mCreateImageBody.setPrompt(content);
        mCreateImageBody.setSize("1024x1024");

        String message = null;
        String body = HttpRequest.post(url)
                .setProxy(proxy)
                .header("Authorization", apiKey)
                .header("Content-Type", "application/json")
                .body(JsonUtils.toJson(mCreateImageBody.GetImageMap()))//mCreateImageBody.GetImageMap()返回值为map类型
                .execute()
                .body();
        JSONObject jsonObject = JSONUtil.parseObj(body);
        JSONArray data = jsonObject.getJSONArray("data");
        JSONObject result = data.get(0, JSONObject.class, Boolean.TRUE);
        message = result.getStr("url");
        System.out.println(message);
        return message;
    }
}
