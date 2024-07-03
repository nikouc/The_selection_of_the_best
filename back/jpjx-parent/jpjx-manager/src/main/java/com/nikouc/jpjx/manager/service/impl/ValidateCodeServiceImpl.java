package com.nikouc.jpjx.manager.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.nikouc.jpjx.manager.service.ValidateCodeService;
import com.nikouc.jpjx.model.vo.system.ValidateCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public ValidateCodeVo generateValidateCode() {
        //1 通过工具生成图片验证码
        //hutool
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 4);
        String code = circleCaptcha.getCode();//验证码值
        String imageBase64 = circleCaptcha.getImageBase64();//返回验证码图片的编码值
        //2 把验证码存储到redis里面，设置redis的key：uuid  redis的value：验证码值
        //设置过期时间
        String key = UUID.randomUUID().toString().replace("-","");
        redisTemplate.opsForValue().set("user:validate"+key,
                                        code,
                                        5,
                                        TimeUnit.MINUTES);
        //3 返回ValidateCodeVo对象
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(key);//redis存储数据的key
        validateCodeVo.setCodeValue("data:image/png;base64,"+imageBase64);
        return validateCodeVo;
    }
}
