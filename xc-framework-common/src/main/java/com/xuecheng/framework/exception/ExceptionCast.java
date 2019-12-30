package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * 创建时间:2019/12/28
 * Name:ZHANGFENG
 */

//抛出异常类
public class ExceptionCast {

    //使用此静态方法抛出自定义异常
    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
