package com.server.controller;

import com.server.backTool.CacheQuery;
import com.server.backTool.IPUtils;
import com.server.backTool.Pwd3DESUtil;
import com.server.model.pojo.UserInfo;
import com.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
    //private List<UserInfo> mListUser;
    private static String PASSWORD_EncryKEY = "EncryptionKey_By-WuMing";//自定义密钥:EncryptionKey_By-WuMing
    private static String mHandPath="/backserver/userImage/default.png";//默认头像地址

    /**
     * 登录接口
     * @param account
     * @param password
     * @return
     */
    @RequestMapping( "/login")
    public Map Login(@RequestParam("account") String account, @RequestParam("password") String password,
                     HttpServletRequest request){

        Map<String,Object> resultMap=new HashMap<>();
        Map<String,Object> requestMap=new HashMap<>();

        requestMap.put("account",account);
        //对密码解密
        String mEncryPwd = Pwd3DESUtil.encode3Des(PASSWORD_EncryKEY, password);
        requestMap.put("password",mEncryPwd);

        /**显示ip**/
        String currentIp=IPUtils.getIpAddress(request);
        System.out.println("IP:"+currentIp);
        //requestMap.put()

        /** 查询缓存是否存在
         * 否则查询数据库，写入Redis缓存
         * */
//        BoundHashOperations hashOps = redisTemplate.boundHashOps(account);
//        if(hashOps.entries().size()>0){
//            //System.out.println("Redis not null");
//            resultMap=hashOps.entries();
//        } else{
//            //System.out.println("Redis is null");
//            try{
//                mUser=userService.login(requestMap);
//                resultMap=CommonClass2Map("success",mUser);
//
//                //System.out.println("__写入Redis缓存Key："+acount);
//                redisTemplate.opsForHash().putAll(account,resultMap);//写入Redis
//                redisTemplate.expire(account,5, TimeUnit.MINUTES);
//            }catch (Exception e){
//                System.out.println("查询异常——mUser is:"+e.getMessage());
//                resultMap.put("result","error");
//            }
//        }
        try{
            mUser=userService.login(requestMap);
//            if(mUser.getmStatus()!=0){
//                if(mUser.getmAddressIp()!=currentIp){
//                    System.out.println("两次登录ip不一致"+mUser.getmAddressIp()+currentIp);
//                }
//            }
            //boolean login_status=userService.fresh_status(requestMap);
            resultMap=CommonClass2Map("success",mUser);
        }catch (Exception e){
            System.out.println("查询异常——mUser is:"+e.getMessage());
            resultMap.put("result","error");
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
    public Map Register(@RequestParam("name") String name,
                        @RequestParam("account") String account,@RequestParam("password") String password){
        System.out.println("Request_name="+name+"___account="+account+"___password="+password);
        Map<String,Object> resultMap=new HashMap<>();
        Map<String,String> requestMap=new HashMap<String, String>();

        requestMap.put("head",mHandPath);
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
                    resultMap=CommonClass2Map("success",mUser);

                    //System.out.println("__写入Key："+acount);
                    redisTemplate.opsForHash().putAll(account,resultMap);//写入Redis
                    redisTemplate.expire(account,5, TimeUnit.MINUTES);
                }catch (Exception e){
                    System.out.println("查询异常——mUser is:"+e.getMessage());
                    resultMap.put("result","false");
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
    public Map RegiQuery(@RequestParam("account") String account){
        Map<String,Object> resultMap=new HashMap<>();
        try{
            mUser=userService.regiQuery(account);
            if(!(mUser.equals(null))){
                //表示账户已注册
                resultMap.put("id",mUser.getmId());
                resultMap.put("head",mUser.getmHead());
                resultMap.put("name",mUser.getmName());
                resultMap.put("account",mUser.getmAccount());
                resultMap.put("password",mUser.getmPassword());
                resultMap.put("sex",mUser.getmSex());
                resultMap.put("phone",mUser.getmPhone());
                resultMap.put("email",mUser.getmEmail());
                resultMap.put("gptNum",mUser.getmGptNum());
                resultMap.put("level",mUser.getmLevel());
            }else
                resultMap.put("result","UnRegister");
        }catch (Exception e){
            System.out.println("查询异常_尚未注册:"+e.getMessage());
            resultMap.put("result","UnRegister");
        }
        return resultMap;
    }

    @RequestMapping("/fuzzyQuery/name")
    public List<UserInfo> FuzzyQuery(@RequestParam("name") String name){
        List<UserInfo> mList=userService.fuzzyQuery(name);
        return mList;
    }

    @RequestMapping("/doGptTrans")
    public Map UpGptNum(@RequestParam("account") String account){
        Map<String,Object> resultMap=new HashMap();
        UserInfo temp=userService.regiQuery(account);
        if(temp!=null){
            Map<String,Object> upMap=new HashMap<>();
            upMap.put("account",temp.getmAccount());
            upMap.put("password",temp.getmPassword());
            upMap.put("gptnum",temp.getmGptNum()-1);
            if(temp.getmGptNum()>0){
                try{
                    boolean upConfirm=userService.upgptnumber(upMap);
                    if(upConfirm){
                        UserInfo resultClass=userService.regiQuery(account);
                        System.out.println("有权限,update成功:");
                        resultMap=CommonClass2Map("Permission",resultClass);
                    }else{
                        /**更新失败**/
                        System.out.println("有权限,update失败:");
                        resultMap=CommonClass2Map("error",temp);
                    }
                }catch (Exception e){
                    System.out.println("update异常:"+e.getMessage());
                    resultMap=CommonClass2Map("error",temp);
                }
            }else{
                System.out.println("没有权限");
                resultMap=CommonClass2Map("NullPermission",temp);
            }
        }
        return resultMap;
    }


    /***
     * 返回公共类方法
     * @param result
     * @param temp
     * @return
     */
    public Map CommonClass2Map(String result,UserInfo temp){
        Map<String,Object> tempMap=new HashMap();
        tempMap.put("result",result);
        tempMap.put("id",temp.getmId());
        tempMap.put("head",temp.getmHead());
        tempMap.put("name",temp.getmName());
        tempMap.put("account",temp.getmAccount());
        tempMap.put("password",temp.getmPassword());
        tempMap.put("sex",temp.getmSex());
        tempMap.put("phone",temp.getmPhone());
        tempMap.put("email",temp.getmEmail());
        tempMap.put("gptNum",temp.getmGptNum());
        tempMap.put("level",temp.getmLevel());
        tempMap.put("status",temp.getmStatus());
        tempMap.put("ip",temp.getmAddressIp());

        return tempMap;
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
