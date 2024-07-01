package com.nikouc.jpjx.manager.service.impl;

import com.nikouc.jpjx.manager.mapper.SysUserMapper;
import com.nikouc.jpjx.manager.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
}