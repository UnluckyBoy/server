package com.server.controller;

import com.server.backTool.CacheQuery;
import com.server.backTool.Pwd3DESUtil;
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
import java.util.logging.Logger;

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
    private static String PASSWORD_EncryKEY = "EncryptionKey_By-WuMing";//自定义密钥
    private static String mHandPath="/backserver/userImage/default.png";//默认头像地址

    /**
     * 登录接口
     * @param account
     * @param password
     * @return
     */
    @RequestMapping( "/login")
    public Map Login(@RequestParam String account,@RequestParam String password){

        Map<String,String> resultMap=new HashMap<>();
        Map<String,String> requestMap=new HashMap<String, String>();

        requestMap.put("account",account);
        //对密码解密
        String mEncryPwd = Pwd3DESUtil.encode3Des(PASSWORD_EncryKEY, password);
        requestMap.put("password",mEncryPwd);

        BoundHashOperations hashOps = redisTemplate.boundHashOps(account);
        if(hashOps.entries().size()>0){
            //System.out.println("Redis not null");
            resultMap=hashOps.entries();
        } else{
            //System.out.println("Redis is null");
            try{
                mUser=userService.login(requestMap);
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
                System.out.println("查询异常——mUser is:"+e.getMessage());
            }
        }

        System.out.println("Server_running_login_Map:\n"+resultMap.toString());
        return resultMap;
    }

    /**
     * 注册接口
     * @param name
     * @param account
     * @param password
     * @return
     */
    @RequestMapping("/register")
    public Map Register(@RequestParam String name,@RequestParam String account,@RequestParam String password){
        System.out.println("Request_name="+name+"___account="+account+"___password="+password);
        Map<String,String> resultMap=new HashMap<>();
        Map<String,String> requestMap=new HashMap<String, String>();

        requestMap.put("image",mHandPath);
        requestMap.put("name",name);
        requestMap.put("account",account);
        requestMap.put("sex","男");
        //对密码加密
        String mEncryPwd = Pwd3DESUtil.encode3Des(PASSWORD_EncryKEY, password);
        requestMap.put("password",mEncryPwd);

        //若未存在，写入数据库返回Json
        if((userService.regiQuery(account)==null)){
            boolean regKey=userService.register(requestMap);
            if(regKey){
                System.out.println("注册成功:"+regKey);
                try{
                    mUser=userService.regiQuery(account);
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
                    System.out.println("查询异常——mUser is:"+e.getMessage());
                }
            }
            return resultMap;
        }else{
            //若已存在，返回false
            resultMap.put("result", "false");
            return resultMap;
        }
    }

    /**
     * 注册校验、账户获取信息
     * @param account
     * @return
     */
    @RequestMapping("/queryinfo")
    public Map RegiQuery(@RequestParam String account){
        Map<String,String> resultMap=new HashMap<>();
        try{
            mUser=userService.regiQuery(account);
            if(!(mUser.equals(null))){
                //表示账户已注册
                resultMap.put("id",String.valueOf(mUser.getmId()));
                resultMap.put("image",mUser.getmImage());
                resultMap.put("name",mUser.getmName());
                resultMap.put("account",mUser.getmAcount());
                resultMap.put("password",mUser.getmPassword());
                resultMap.put("sex",mUser.getmSex());
                resultMap.put("phone",mUser.getmPhone());
                resultMap.put("email",mUser.getmEmail());
            }else
                resultMap.put("result","UnRegister");
        }catch (Exception e){
            System.out.println("查询异常_尚未注册:"+e.getMessage());
            resultMap.put("result","UnRegister");
        }
        return resultMap;
    }

    @RequestMapping("/test")
    public Map Test(@RequestParam String password){

        Map<String,String> resultMap=new HashMap<>();
        /*
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
         */
        //对密码加密
        String mEncryPwd = Pwd3DESUtil.encode3Des(PASSWORD_EncryKEY, password);
        System.out.println("加密前：\n"+password+"\n加密后：\n"+mEncryPwd);
        System.out.println("\nServer_running___"+resultMap.toString());
        return resultMap;
    }
}
