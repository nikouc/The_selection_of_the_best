package com.nikouc.jpjx.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.nikouc.jpjx.common.exception.ZidingyiException;
import com.nikouc.jpjx.manager.mapper.SysUserMapper;
import com.nikouc.jpjx.manager.service.SysUserService;
import com.nikouc.jpjx.model.dto.system.LoginDto;
import com.nikouc.jpjx.model.entity.system.SysUser;
import com.nikouc.jpjx.model.vo.common.ResultCodeEnum;
import com.nikouc.jpjx.model.vo.system.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //用户登录
    @Override
    public LoginVo login(LoginDto loginDto) {
        //1 获取输入的验证码和存储到redis的key的名称 loginDto获取
        String captcha = loginDto.getCaptcha();
        String codeKey = loginDto.getCodeKey();
        //2 根据获取的redis里面的key，查询redis里面存储的验证码
        String redisCode = redisTemplate.opsForValue().get("user:validate" + codeKey);
        //3 比较输入的验证码和redis存储验证码是否一致
        if(StrUtil.isEmpty(redisCode) || !StrUtil.equalsIgnoreCase(redisCode,captcha)){
            throw new ZidingyiException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        //4 如果不一致，提示用户，校验失败
        //5 如果一致，删除redis里面验证码
        redisTemplate.delete("user:validate"+codeKey);

        //1.获取提交用户名，loginDto获取
        String username = loginDto.getUserName();
        //2.根据用户名查询数据库表sys_user表
        SysUser sysUser = sysUserMapper.selectUserInfoByUserName(username);
        //3.如果根据用户名查不到对应的信息，用户不存在，返回错误信息
        if (sysUser == null){
            throw new ZidingyiException(ResultCodeEnum.LOGIN_ERROR);
        }
        //4.如果根据用户名查询到用户信息，用户存在
        //5.获取输入的密码，比较输入的密码和数据库密码是否一致
        String database_password = sysUser.getPassword();
        String input_password = DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());
        if (!input_password.equals(database_password)){
            throw new ZidingyiException(ResultCodeEnum.LOGIN_ERROR);
        }
        //6.如果密码一致登录成功，如果密码不一致登录失败
        //7.登录成功，生成用户唯一表示token
        String token = UUID.randomUUID().toString().replaceAll("-","");
        //8.把登录成功用户信息放到redis里面
        redisTemplate.opsForValue()
                .set("user:login"+token,
                        JSON.toJSONString(sysUser),
                        7,
                        TimeUnit.DAYS);
        //9.返回loginVo对象
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        return loginVo;
    }

    @Override
    public SysUser getUserInfo(String token) {
        String userJson = redisTemplate.opsForValue().get("user:login" + token);
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login"+token);
    }
}