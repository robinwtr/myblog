package com.robin.blog.controller;

import com.robin.blog.exception.ServeException;
import com.robin.blog.vo.Result;
import com.robin.blog.constant.StatusConst;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理
 *
 * @author robin
 */
@RestControllerAdvice
public class ControllerAdvice {

    /**
     * 处理服务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = ServeException.class)
    public Result errorHandler(ServeException e) {
        return new Result(false, StatusConst.ERROR, e.getMessage());
    }

    /**
     * 处理参数异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result errorHandler(MethodArgumentNotValidException e) {
        return new Result(false, StatusConst.ERROR, e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 处理系统异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Result errorHandler(Exception e) {
        e.printStackTrace();
        return new Result(false, StatusConst.SYSTEM_ERROR, "系统异常");
    }

}
