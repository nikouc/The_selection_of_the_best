package com.nikouc.jpjx.manager.controler;

import com.nikouc.jpjx.manager.mapper.SysUserMapper;
import com.nikouc.jpjx.manager.service.SysUserService;
import com.nikouc.jpjx.manager.service.ValidateCodeService;
import com.nikouc.jpjx.model.dto.system.LoginDto;
import com.nikouc.jpjx.model.entity.system.SysUser;
import com.nikouc.jpjx.model.vo.common.Result;
import com.nikouc.jpjx.model.vo.common.ResultCodeEnum;
import com.nikouc.jpjx.model.vo.system.LoginVo;
import com.nikouc.jpjx.model.vo.system.ValidateCodeVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ValidateCodeService validateCodeService;

    //获取当前登录用户信息
    @GetMapping(value = "/getUserInfo")
    public Result getUserInfo(@RequestHeader(name = "token") String token){
        //1 从请求头获取token
        //2 根据token查询redis获取用户信息
        SysUser sysUser = sysUserService.getUserInfo(token);
        //3 用户信息返回
        return Result.build(sysUser,ResultCodeEnum.SUCCESS);
    }

    //生成图片验证码
    @GetMapping(value = "/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode(){
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo,ResultCodeEnum.SUCCESS);
    }

    //用户登录
    @Operation(summary = "登录方法")
    @PostMapping("login")
    public Result login(@RequestBody LoginDto loginDto){
        LoginVo loginVo = sysUserService.login(loginDto);
        return  Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }
}
