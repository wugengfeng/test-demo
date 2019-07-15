package com.guarantee.controller;

import com.guarantee.service.CrawlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: wgf
 * @create: 2019-07-15 18:26
 * @description:
 **/
@RestController
@RequestMapping("/test")
@Api(description = "保修控制器")
public class TestController {

    @Autowired
    private CrawlService crawlService;

    @ApiOperation(value = "获取保修信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "no", value = "序列号", required = true)
    })
    @GetMapping("/crawl")
    public Map<String, Object> crawl(String no) {
        return this.crawlService.crawl(no);
    }

}
