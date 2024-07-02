package com.nikouc.jpjx.manager.service;


import com.nikouc.jpjx.model.dto.system.LoginDto;
import com.nikouc.jpjx.model.vo.system.LoginVo;

public interface SysUserService{

    LoginVo login(LoginDto loginDto);
}