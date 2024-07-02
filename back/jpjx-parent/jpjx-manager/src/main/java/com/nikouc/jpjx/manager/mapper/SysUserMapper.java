package com.nikouc.jpjx.manager.mapper;

import com.nikouc.jpjx.manager.service.SysUserService;
import com.nikouc.jpjx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public interface SysUserMapper {

    SysUser selectUserInfoByUserName(String username);
}
