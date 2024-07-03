package com.nikouc.jpjx.common.exception;

import com.nikouc.jpjx.model.vo.common.Result;
import com.nikouc.jpjx.model.vo.common.ResultCodeEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    //统一处理异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(){
        return Result.build(null, ResultCodeEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(ZidingyiException.class)
    @ResponseBody
    public Result error(ZidingyiException e){
        return Result.build(null,e.getResultCodeEnum());
    }
}
