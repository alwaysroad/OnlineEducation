package com.cornelius.servicebase.exceptionhandler;

import com.cornelius.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
/*
* 统一异常处理类
* */

@ControllerAdvice
@Slf4j
public class GloableExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }


    @ExceptionHandler(CorneliusException.class)
    @ResponseBody
    public R diyError(CorneliusException e){
        e.printStackTrace();
        log.error(e.getMessage());
        //return R.error().message("执行了自定义的异常处理");
        return R.error().code(e.getCode()).message(e.getMsg());//传什么输出什么
    }
}
