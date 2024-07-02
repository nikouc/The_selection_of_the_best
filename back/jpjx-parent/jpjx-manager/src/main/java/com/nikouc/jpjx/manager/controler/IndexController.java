package com.nikouc.jpjx.manager.controler;

import com.nikouc.jpjx.manager.mapper.SysUserMapper;
import com.nikouc.jpjx.manager.service.SysUserService;
import com.nikouc.jpjx.model.dto.system.LoginDto;
import com.nikouc.jpjx.model.vo.common.Result;
import com.nikouc.jpjx.model.vo.common.ResultCodeEnum;
import com.nikouc.jpjx.model.vo.system.LoginVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    //用户登录
    @PostMapping("login")
    public Result login(@RequestBody LoginDto loginDto){
        LoginVo loginVo = sysUserService.login(loginDto);
        return  Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }
}
