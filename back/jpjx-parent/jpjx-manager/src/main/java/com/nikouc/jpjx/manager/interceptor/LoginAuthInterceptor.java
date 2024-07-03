package com.nikouc.jpjx.manager.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.nikouc.common.AuthContextUtil;
import com.nikouc.jpjx.model.entity.system.SysUser;
import com.nikouc.jpjx.model.vo.common.Result;
import com.nikouc.jpjx.model.vo.common.ResultCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@Component
public class LoginAuthInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求方式 预检查
        String method = request.getMethod();
        if ("OPTIONS".equals(method)){
            return true;
        }
        //从请求头获取token
        String token = request.getHeader("token");
        //如果无token不放行，响应结果
        if (StrUtil.isEmpty(token)){
            responseNoLoginInfo(response);
            return  false;
        }
        //如果有token 拿着token查询redis
        String userInfoStrig = redisTemplate.opsForValue().get("user:login" + token);
        //如果redis查不到数据，返回错误信息
        if (StrUtil.isEmpty(userInfoStrig)){
            responseNoLoginInfo(response);
            return false;
        }
        //如果redis查询到数据，把用户信息放到ThreadLocal中
        SysUser sysUser = JSON.parseObject(userInfoStrig, SysUser.class);
        AuthContextUtil.set(sysUser);
        //把redis用户信息数据更新过期时间
        redisTemplate.expire("user:login"+token,1, TimeUnit.HOURS);
        //放行
        return true;
    }

    //响应208给前端
    private void responseNoLoginInfo(HttpServletResponse response){
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try{
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (writer != null){
                writer.close();
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //移除ThreadLocal中的数据
        AuthContextUtil.remove();
    }
}
