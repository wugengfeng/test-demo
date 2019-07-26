package com.guarantee.controller;

import com.guarantee.exception.CrawlException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class Advice {

    private Logger logger = LogManager.getLogger(Advice.class);

    @ExceptionHandler(CrawlException.class)
    public String crawl(Exception e) {
        logger.error("获取保修信息失败", e);
        return e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    public Map err(Exception e) {
        logger.error("系统繁忙，请稍后再试", e);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 3);
        map.put("result", "系统繁忙，请稍后再试");
        map.put("success", false);
        return map;
    }

}
