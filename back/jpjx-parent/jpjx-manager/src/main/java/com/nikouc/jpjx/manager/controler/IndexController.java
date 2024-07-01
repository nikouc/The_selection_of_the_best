package com.nikouc.jpjx.manager.controler;

import com.nikouc.jpjx.manager.mapper.SysUserMapper;
import com.nikouc.jpjx.manager.service.SysUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;
}
