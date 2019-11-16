package com.guarantee.controller;

import com.guarantee.constant.Constant;
import com.guarantee.entity.Guarantee;
import com.guarantee.service.GuaranteeService;
import com.guarantee.service.impl.HkGuaranteeServiceImpl;
import com.guarantee.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @author: wgf
 * @create: 2019-07-15 18:26
 * @description:
 **/
@RestController
@RequestMapping("guarantee")
@Api(description = "保修控制器")
public class GuaranteetController {

    @Resource(name = "guaranteeService")
    private GuaranteeService guaranteeService;

    @Resource(name = "hkGuaranteeService")
    private HkGuaranteeServiceImpl hkGuaranteeService;

    @ApiOperation(value = "获取保修信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "sno", value = "序列号", required = true)
    })
    @GetMapping("info")
    public String info(String sno) throws IOException, URISyntaxException, InterruptedException {
        String result = this.hkGuaranteeService.selectBySno(sno);
        return result;
    }

    @GetMapping("proxy")
    public Boolean proxy(String proxy) throws IOException, URISyntaxException, InterruptedException {
        if ("1".equals(proxy)) {
            Constant.enableProxy = true;
        } else {
            Constant.enableProxy = false;
        }

        return Constant.enableProxy;
    }
}
