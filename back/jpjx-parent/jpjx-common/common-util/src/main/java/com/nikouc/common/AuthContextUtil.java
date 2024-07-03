package com.nikouc.common;

import com.nikouc.jpjx.model.entity.system.SysUser;

//工具类 封装ThreadLocal
public class AuthContextUtil {
    //创建ThreadLocal对象
    private static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<>();
    //存储数据
    public static void set(SysUser sysUser){
        threadLocal.set(sysUser);
    }
    //获取数据
    public static SysUser get(){
        return threadLocal.get();
    }
    //删除数据
    public static void remove(){
        threadLocal.remove();
    }
}
