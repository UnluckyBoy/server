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
import java.util.List;
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

    //在RedisConfig文件中配置了redisTemplate的序列化之后， 客户端也能正确显示键值对
    @Autowired
    private RedisTemplate redisTemplate;


    private UserInfo mUser;
    private List<UserInfo> mListUser;
    private static String PASSWORD_EncryKEY = "EncryptionKey_By-WuMing";//自定义密钥:EncryptionKey_By-WuMing
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

        /**
         * try {
         * 			String StringtoKey=new String(Key.getBytes("iso-8859-1"), "utf-8");//中文字符转换
         * 			searchList=backService.searchBook(StringtoKey);
         *                } catch (UnsupportedEncodingException e) {
         * 			e.printStackTrace();
         *        }
         */

        requestMap.put("account",account);
        //对密码解密
        String mEncryPwd = Pwd3DESUtil.encode3Des(PASSWORD_EncryKEY, password);
        requestMap.put("password",mEncryPwd);

        /**
         * 查询缓存是否存在
         * 否则查询数据库，写入Redis缓存
         */
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
                resultMap.put("account",mUser.getmAccount());
                resultMap.put("password",mUser.getmPassword());
                resultMap.put("sex",mUser.getmSex());
                resultMap.put("phone",mUser.getmPhone());
                resultMap.put("email",mUser.getmEmail());
                resultMap.put("mGptNum",String.valueOf(mUser.getmGptNum()));

                //System.out.println("__写入Redis缓存Key："+acount);
                redisTemplate.opsForHash().putAll(account,resultMap);//写入Redis
                redisTemplate.expire(account,5, TimeUnit.MINUTES);
            }catch (Exception e){
                System.out.println("查询异常——mUser is:"+e.getMessage());
                resultMap.put("result","error");
            }
        }

        System.out.println("Server_running_login_Map:\n"+resultMap.toString());
        return resultMap;
    }

    /**
     * 注册接口
     * @param name
     * @param account   账户
     * @param password  密码
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
                    resultMap.put("account",mUser.getmAccount());
                    resultMap.put("password",mUser.getmPassword());
                    resultMap.put("sex",mUser.getmSex());
                    resultMap.put("phone",mUser.getmPhone());
                    resultMap.put("email",mUser.getmEmail());
                    resultMap.put("mGptNum",String.valueOf(mUser.getmGptNum()));

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
     * @param account 账户信息
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
                resultMap.put("account",mUser.getmAccount());
                resultMap.put("password",mUser.getmPassword());
                resultMap.put("sex",mUser.getmSex());
                resultMap.put("phone",mUser.getmPhone());
                resultMap.put("email",mUser.getmEmail());
                resultMap.put("mGptNum",String.valueOf(mUser.getmGptNum()));
            }else
                resultMap.put("result","UnRegister");
        }catch (Exception e){
            System.out.println("查询异常_尚未注册:"+e.getMessage());
            resultMap.put("result","UnRegister");
        }
        return resultMap;
    }

    @RequestMapping("/fuzzyQuery")
    public List<UserInfo> FuzzyQuery(@RequestParam String name){
//        Map<String,String> resultMap=new HashMap<>();
//        try{
//            mListUser=userService.fuzzyQuery(name);
//            if(!(mListUser.equals(null))){
//                //表示账户已注册
//                resultMap.put("id",String.valueOf(mUser.getmId()));
//                resultMap.put("image",mUser.getmImage());
//                resultMap.put("name",mUser.getmName());
//                resultMap.put("account",mUser.getmAcount());
//                resultMap.put("password",mUser.getmPassword());
//                resultMap.put("sex",mUser.getmSex());
//                resultMap.put("phone",mUser.getmPhone());
//                resultMap.put("email",mUser.getmEmail());
//            }else
//                resultMap.put("result","Query_error");
//        }catch (Exception e){
//            System.out.println("查询异常——mUser is:"+e.getMessage());
//            resultMap.put("result","error");
//        }

        List<UserInfo> mList=userService.fuzzyQuery(name);
        //System.out.println(mList.get(0));

//        Map<String,String> resultMap=new HashMap<>();
//        try{
//            if(mList.size()>0){
//                for(UserInfo mUsers:mList){
//                    //System.out.println(s);
//                    resultMap.put("id",String.valueOf(mUsers.getmId()));
//                    resultMap.put("image",mUsers.getmImage());
//                    resultMap.put("name",mUsers.getmName());
//                    resultMap.put("account",mUsers.getmAcount());
//                    resultMap.put("password",mUsers.getmPassword());
//                    resultMap.put("sex",mUsers.getmSex());
//                    resultMap.put("phone",mUsers.getmPhone());
//                    resultMap.put("email",mUsers.getmEmail());
//                }
//            }else{
//                resultMap.put("result","Query_error");
//            }
//        }catch (Exception e){
//            System.out.println("查询异常——mUser is:"+e.getMessage());
//            resultMap.put("result","error");
//        }
        return mList;
    }

    /**
     * 测试加密
     * @param password
     * @return
     */
    @RequestMapping("/test")
    public Map Test(@RequestParam String password){

        Map<String,String> resultMap=new HashMap<>();
        //对密码加密
        String mEncryPwd = Pwd3DESUtil.encode3Des(PASSWORD_EncryKEY, password);
        System.out.println("加密前：\n"+password+"\n加密后：\n"+mEncryPwd);
        System.out.println("\nServer_running___"+resultMap.toString());
        return resultMap;
    }
}
