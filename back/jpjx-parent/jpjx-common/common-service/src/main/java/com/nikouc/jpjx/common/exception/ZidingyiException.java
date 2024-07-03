package com.nikouc.jpjx.common.exception;

import com.nikouc.jpjx.model.vo.common.ResultCodeEnum;
import lombok.Data;

@Data
public class ZidingyiException extends RuntimeException{
    private Integer code;
    private String message;
    private ResultCodeEnum resultCodeEnum;

    public ZidingyiException(ResultCodeEnum resultCodeEnum){
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }
}
