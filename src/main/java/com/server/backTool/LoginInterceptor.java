package com.server.backTool;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName LoginInterceptor
 * @Author Create By matrix
 * @Date 2023/5/2 0002 19:27
 */
public class LoginInterceptor implements HandlerInterceptor {
    private StringRedisTemplate redisTemplate;

    public LoginInterceptor(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String token = request.getParameter("token");
        String account = request.getParameter("account");
        if(token == null){
            response.setStatus(401);
            return false;
        }
        String tokenValue = redisTemplate.opsForValue().get(account);
        if(tokenValue!=null&&tokenValue.equals(token)){
            System.out.println("token正确,放行");
            return true;
        }
        response.setStatus(520);
        return false;
    }
}
