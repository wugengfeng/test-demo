package com.guarantee.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class Advice {

    private Logger logger = LogManager.getLogger(Advice.class);

    @ExceptionHandler(Exception.class)
    public String err(Exception e) {
        logger.error("获取保修信息失败", e);
        return "获取保修信息失败，请稍后再试";
    }

}
