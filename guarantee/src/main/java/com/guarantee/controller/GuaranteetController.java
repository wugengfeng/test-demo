package com.guarantee.controller;

import com.guarantee.entity.Guarantee;
import com.guarantee.service.GuaranteeService;
import com.guarantee.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private static String TEMPALTE = "%s：%s<br>";
    private static String TIME_TEMPALTE = "%s：%s到期<br>";

    @Autowired
    private GuaranteeService guaranteeService;

    @ApiOperation(value = "获取保修信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "sno", value = "序列号", required = true)
    })
    @GetMapping("info")
    public String info(String sno) throws IOException, URISyntaxException, InterruptedException {
        Guarantee guarantee = this.guaranteeService.selectBySno(sno);

        StringBuilder result = new StringBuilder();

        Date date = new Date();
        if (Objects.isNull(guarantee)) {
            result.append("获取保修信息失败，请稍后再试");
        } else {
            result.append(String.format(TEMPALTE, "序 列 号", guarantee.getSno()));
            result.append(String.format(TEMPALTE, "设备型号", guarantee.getIphoneInfo()));
            result.append(String.format(TEMPALTE, "激活状态", guarantee.getActivationState()));

            if (Objects.nonNull(guarantee.getSupportDate())) {
                if (date.before(guarantee.getSupportDate())) {
                    result.append(String.format(TIME_TEMPALTE, "电话支持", DateUtil.formatYMD(guarantee.getSupportDate())));
                }
            } else {
                result.append(String.format(TEMPALTE, "电话支持", "已过期"));
            }

            //在保修期内的、激活时间=保修时间少一年、天数加一天
            if (Objects.nonNull(guarantee.getGuaranteeDate())) {
                if (date.before(guarantee.getGuaranteeDate())) {
                    result.append(String.format(TIME_TEMPALTE, "保修支持", DateUtil.formatYMD(guarantee.getGuaranteeDate())));
                    Date activeDate = DateUtil.calculateDate(guarantee.getSupportDate(), -1, Calendar.YEAR);
                    activeDate = DateUtil.calculateDate(activeDate, 1, Calendar.DAY_OF_YEAR);
                    result.append(String.format(TIME_TEMPALTE, "激活时间", DateUtil.formatYMD(activeDate)));
                }
            } else {
                result.append(String.format(TEMPALTE, "保修支持", guarantee.getIsGuarantee()));
                result.append(String.format(TEMPALTE, "激活时间", ""));
            }

            result.append(String.format(TEMPALTE, "是否延保", guarantee.getDelay()));
            result.append(String.format(TEMPALTE, "是否官换机", guarantee.getChangePhone()));
            result.append(String.format(TEMPALTE, "是否借出设备", guarantee.getLend()));
        }

        return result.toString();
    }


}
