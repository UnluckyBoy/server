package com.server.controller;

import com.server.backTool.IPUtils;
import com.server.backTool.Pwd3DESUtil;
import com.server.model.pojo.UserInfo;
import com.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
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
    private static String system_Path=System.getProperty("user.dir")+"/BackResource/backserver/";
    private static String user_info_Path="userImage/";

    @Autowired
    private UserService userService;

    //在RedisConfig文件中配置了redisTemplate的序列化之后， 客户端也能正确显示键值对
    @Autowired
    private RedisTemplate redisTemplate;


    private UserInfo mUser;
    //private List<UserInfo> mListUser;
    private static String PASSWORD_EncryKEY = "EncryptionKey_By-WuMing";//自定义密钥:EncryptionKey_By-WuMing
    private static String mHand="userImage/default.png";//默认头像地址

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
        requestMap.put("status",1);

        /**显示ip**/
        String currentIp=IPUtils.getIpAddress(request);
        System.out.println("IP:"+currentIp);
        requestMap.put("addressIp",currentIp);

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
            mUser= userService.login(requestMap);
            if(mUser.getmStatus()!=0){
                /*
                if(mUser.getmAddressIp()!=currentIp){
                    System.out.println("两次登录ip不一致"+mUser.getmAddressIp()+currentIp);
                }
                */
                resultMap.put("result","login_lock");
            }else{
                boolean login_status= userService.fresh_status_login(requestMap);
                if(login_status){
                    resultMap=CommonClass2Map("success",mUser);
                }else{
                    resultMap.put("result","error");
                }
            }
        }catch (Exception e){
            System.out.println("查询异常:"+e.getMessage());
            resultMap.put("result","error");
        }

        System.out.println("Server_running_login_Map:\n"+resultMap.toString());
        return resultMap;
    }

    /**
     * 登出接口
     * @param account
     * @param password
     * @param request
     * @return
     */
    @RequestMapping( "/logout")
    public Map Logout(@RequestParam("account") String account, @RequestParam("password") String password,
                      HttpServletRequest request){
        Map<String,Object> resultMap=new HashMap<>();
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("account",account);
        String mEncryPwd = Pwd3DESUtil.encode3Des(PASSWORD_EncryKEY, password);
        requestMap.put("password",mEncryPwd);
        requestMap.put("status",0);
        try{
            if((userService.infoQuery(requestMap).getmStatus()==1)){
                boolean logout= userService.fresh_status_logout(requestMap);
                if(logout){
                    resultMap.put("result","success");
                }else{
                    System.out.println("登出异常:"+logout);
                    resultMap.put("result","error");
                }
            }else{
                System.out.println("登出异常:尚未登录");
                resultMap.put("result","unlogin");
            }
        }catch (Exception e){
            System.out.println("登出异常:"+e.getMessage());
            resultMap.put("result","error");
        }

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
        Map<String,Object> requestMap=new HashMap<>();

        requestMap.put("head",mHand);
        requestMap.put("name",name);
        requestMap.put("account",account);
        requestMap.put("sex","男");
        //对密码加密
        String mEncryPwd = Pwd3DESUtil.encode3Des(PASSWORD_EncryKEY, password);
        requestMap.put("password",mEncryPwd);

        //若未存在，写入数据库返回Json
        if((userService.infoQuery(requestMap)==null)){
            boolean regKey= userService.register(requestMap);
            if(regKey){
                System.out.println("注册成功:"+regKey);
                try{
                    mUser= userService.infoQuery(requestMap);
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
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("account",account);
        try{
            mUser= userService.infoQuery(requestMap);
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
        List<UserInfo> mList= userService.fuzzyQuery(name);
        return mList;
    }

    @RequestMapping("/doGptTrans")
    public Map UpGptNum(@RequestParam("account") String account){
        Map<String,Object> resultMap=new HashMap();
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("account",account);
        UserInfo temp= userService.infoQuery(requestMap);
        if(temp!=null){
            Map<String,Object> upMap=new HashMap<>();
            upMap.put("account",temp.getmAccount());
            upMap.put("password",temp.getmPassword());
            upMap.put("gptnum",temp.getmGptNum()-1);
            if(temp.getmGptNum()>0){
                try{
                    boolean upConfirm= userService.upgptnumber(upMap);
                    if(upConfirm){
                        UserInfo resultClass= userService.infoQuery(requestMap);
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

    @RequestMapping("/update_gpt_num")
    public Map Update_Gpt_Num(@RequestParam("account") String account){

        Map<String,Object> resultMap=new HashMap();
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("account",account);
        UserInfo temp= userService.infoQuery(requestMap);
        if(temp!=null&&temp.getmStatus()==1){
            Map<String,Object> upMap=new HashMap<>();
            upMap.put("account",temp.getmAccount());
            upMap.put("password",temp.getmPassword());
            upMap.put("gptnum",1);
            try{
                boolean upConfirm= userService.upgptnumber(upMap);
                if(upConfirm){
                    UserInfo resultClass= userService.infoQuery(requestMap);
                    resultMap=CommonClass2Map("success",resultClass);
                    System.out.println("update成功");
                }else{
                    System.out.println("update异常_更新失败");
                    resultMap=CommonClass2Map("error",temp);
                }
            }catch (Exception e){
                System.out.println("update异常:"+e.getMessage());
                resultMap=CommonClass2Map("error",temp);
            }
        }else{
            System.out.println("update异常_用户未登录");
            resultMap=CommonClass2Map("error",temp);
        }
        return resultMap;
    }

    @RequestMapping("/upHead")
    public Map UpHead(@RequestParam("account") String account,@RequestParam("file") MultipartFile file){
        Map<String,Object> resultMap=new HashMap();
        Map<String,Object> requestMap=new HashMap<>();
        //String filename = file.getOriginalFilename();//获取上传文件名
        requestMap.put("account",account);
        UserInfo temp= userService.infoQuery(requestMap);
        if(temp!=null){
            String filename=temp.getmName()+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            try {
                //写入本地文件
                file.transferTo(new File(system_Path+user_info_Path+filename));
                System.out.println("上传成功"+filename);
                requestMap.put("head",user_info_Path+filename);
                boolean upHead= userService.fresh_head(requestMap);
                if(upHead){
                    System.out.println("更新成功");
                    UserInfo resultClass= userService.infoQuery(requestMap);
                    resultMap=CommonClass2Map("success",resultClass);
                    //resultMap.put("result","success");
                }else{
                    System.out.println("更新异常");
                    resultMap=CommonClass2Map("error",temp);
                    //resultMap.put("result","error");
                }
            } catch (IOException e) {
                System.out.println("上传异常:"+e.getMessage());
                resultMap=CommonClass2Map("error",temp);
            }
        }else{
            resultMap=CommonClass2Map("error",temp);
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
