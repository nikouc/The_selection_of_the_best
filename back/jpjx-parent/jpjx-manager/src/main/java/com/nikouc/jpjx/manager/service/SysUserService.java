package com.nikouc.jpjx.manager.service;


import com.nikouc.jpjx.model.dto.system.LoginDto;
import com.nikouc.jpjx.model.entity.system.SysUser;
import com.nikouc.jpjx.model.vo.system.LoginVo;

public interface SysUserService{

    //用户登录
    LoginVo login(LoginDto loginDto);

    //获取用户信息
    SysUser getUserInfo(String token);
}