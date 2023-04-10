package com.server.model.pojo.GptRequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ChatBody
 * @Author Create By Administrator
 * @Date 2023/4/10 0010 21:11
 */
public class ChatBody {
    private String model="gpt-3.5-turbo";
    private List<Map<String, String>> messages;
    private int max_tokens=2048;
    private double temperature=0.5;
    private int top_p=1;
    private int frequency_penalty=0;
    private int presence_penalty=0;

    public int getMax_tokens() {
        return max_tokens;
    }

    public void setMax_tokens(int max_tokens) {
        this.max_tokens = max_tokens;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getTop_p() {
        return top_p;
    }

    public void setTop_p(int top_p) {
        this.top_p = top_p;
    }

    public int getFrequency_penalty() {
        return frequency_penalty;
    }

    public void setFrequency_penalty(int frequency_penalty) {
        this.frequency_penalty = frequency_penalty;
    }

    public int getPresence_penalty() {
        return presence_penalty;
    }

    public void setPresence_penalty(int presence_penalty) {
        this.presence_penalty = presence_penalty;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Map<String, String>> getMessages() {
        return messages;
    }

    public void setMessages(List<Map<String, String>> messages) {
        this.messages = messages;
    }

    public Map<String,Object> GetChatMap(){
        Map<String,Object> map=new HashMap<>();
        map.put("model", this.getModel());
        map.put("messages", this.getMessages());
        map.put("max_tokens", this.getMax_tokens());
        map.put("temperature", this.getTemperature());
        map.put("top_p", this.getTop_p());
        map.put("frequency_penalty", this.getFrequency_penalty());
        map.put("presence_penalty", this.getPresence_penalty());
        return map;
    }
}
