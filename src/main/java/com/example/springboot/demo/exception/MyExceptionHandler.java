package com.example.springboot.demo.exception;

import com.example.springboot.demo.common.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public R handle(ServiceException se){
        return R.failure(se.getCode(),se.getMessage());
    }
}
