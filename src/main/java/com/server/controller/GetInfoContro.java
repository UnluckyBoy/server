package com.server.controller;

import com.server.model.pojo.UserInfo;
import com.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 控制层
 *
 * @Controller
 * @ResponseBody
 * 与
 * @RestController
 * 一样
 */
@Controller
@ResponseBody
@RequestMapping("/UserInfo")
public class GetInfoContro {

    @Autowired
    private UserService userService;

    //在RedisConfig文件中配置了redisTemplate的序列化之后， 客户端也能正确显示键值对了
    @Autowired
    private RedisTemplate redisTemplate;


    private UserInfo mUser;

    /**
     * 登录接口
     * @param account
     * @param password
     * @return
     */
    @RequestMapping( "/login")
    public Map Login(@RequestParam String account,@RequestParam String password){

        Map<String,String> resultMap=new HashMap<>();
        Map<String,String> mUserMap=new HashMap<String, String>();

        mUserMap.put("account",account);
        mUserMap.put("password",password);

        BoundHashOperations hashOps = redisTemplate.boundHashOps(account);
        //System.out.println("Redis_test"+hashOps);

        if(hashOps.entries().size()>0){
            //System.out.println("Redis not null");
            resultMap=hashOps.entries();
        } else{
            //System.out.println("Redis is null");
            try{
                mUser=userService.login(mUserMap);
                //System.out.println(mUser.getmAcount());

                resultMap.put("id",String.valueOf(mUser.getmId()));
                resultMap.put("image",mUser.getmImage());
                resultMap.put("name",mUser.getmName());
                resultMap.put("account",mUser.getmAcount());
                resultMap.put("password",mUser.getmPassword());
                resultMap.put("sex",mUser.getmSex());
                resultMap.put("phone",mUser.getmPhone());
                resultMap.put("email",mUser.getmEmail());

                //System.out.println("__写入Key："+acount);
                redisTemplate.opsForHash().putAll(account,resultMap);//写入Redis
                redisTemplate.expire(account,5, TimeUnit.MINUTES);
            }catch (Exception e){
                System.out.println("mUser is null");
            }
        }

        System.out.println("Server_running___"+resultMap.toString());
        return resultMap;
    }

    @RequestMapping("/upUserInfo")
    public Map Register(){

        return null;
    }

    @RequestMapping("/test")
    public Map Test(@RequestParam String account,@RequestParam String password){
        Map<String,String> resultMap=new HashMap<>();
        Map<String,String> mUserMap=new HashMap<String, String>();
        mUserMap.put("account",account);
        mUserMap.put("password",password);

        try {
            mUser=userService.login(mUserMap);

            resultMap.put("id",String.valueOf(mUser.getmId()));
            resultMap.put("image",mUser.getmImage());
            resultMap.put("name",mUser.getmName());
            resultMap.put("account",mUser.getmAcount());
            resultMap.put("password",mUser.getmPassword());
            resultMap.put("sex",mUser.getmSex());
            resultMap.put("phone",mUser.getmPhone());
            resultMap.put("email",mUser.getmEmail());

        }catch (Exception e){
            System.out.println("mUser is null");
        }

        System.out.println("Server_running___"+resultMap.toString());
        return resultMap;
    }
}
