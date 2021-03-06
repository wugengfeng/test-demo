package com.guarantee.service.impl;

import com.alibaba.fastjson.JSON;
import com.guarantee.agent.UserAgent;
import com.guarantee.constant.Constant;
import com.guarantee.entity.Config;
import com.guarantee.entity.Guarantee;
import com.guarantee.entity.json.ResponseJson;
import com.guarantee.exception.CrawlException;
import com.guarantee.mapper.ConfigMapper;
import com.guarantee.mapper.GuaranteeMapper;
import com.guarantee.proxy.ProxyUtil;
import com.guarantee.service.GuaranteeService;
import com.guarantee.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @create: 2019-07-15 18:28
 * @description:
 **/
@Service("guaranteeService")
public class GuaranteeServiceImpl implements GuaranteeService {

    private static String TEMPALTE = "%s: %s<br>";
    private static String TIME_TEMPALTE = "%s: %s到期<br>";

    private Logger logger = LogManager.getLogger(GuaranteeServiceImpl.class);

    @Autowired
    private GuaranteeMapper guaranteeMapper;

    @Autowired
    private UserAgent defaultUserAgent;

    @Autowired
    private ConfigMapper configMapper;

    @Override
    public String selectBySno(String sno) throws InterruptedException, IOException, URISyntaxException {
        List<Integer> lenList = Arrays.asList(11, 12, 15);
        if (StringUtils.isBlank(sno) || !lenList.contains(sno.length())) {
            Map<String, Object> map = new HashMap<>();
            map.put("status", 3);
            map.put("result", String.format(TEMPALTE, "序 列 号", sno) + "错误序列号(Wrong imei)");
            map.put("success", false);
            throw new CrawlException(JSON.toJSONString(map));
        }

        Guarantee guarantee = this.guaranteeMapper.selectBySno(sno);

        if (Objects.nonNull(guarantee)) {
            return this.getResult(guarantee);
        }

        int reTry = 0;
        Boolean isRetry = null;
        do {
            try {
                isRetry = false;
                guarantee = this.crawl(sno);
            } catch (TimeoutException | NoSuchElementException e) {
                reTry++;
                isRetry = true;
                logger.error(String.format("对序列号 %s 进行第%s次重试", sno, reTry));

                if (reTry >= 3) {
                    throw e;
                }
            }
        } while (reTry > 0 && reTry < 3 && isRetry);

        return this.getResult(guarantee);
    }


    public Guarantee crawl(String sno) throws InterruptedException, IOException, URISyntaxException {
        long begin = System.currentTimeMillis();
        String userAgent = defaultUserAgent.getUserAgent();

        System.setProperty("webdriver.chrome.driver", Constant.chromedriverPath);
        ChromeOptions options = new ChromeOptions();

        //设置为 headless 模式 （必须）
        options.addArguments(String.format("--disk-cache-dir=%s", Constant.cacheDir));
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments(String.format("--user-agent=%s", userAgent));

        /*String proxyAddress = ProxyUtil.getProxy();
        Proxy ipProxy = new Proxy();
        ipProxy.setHttpProxy(proxyAddress);
        options.setProxy(ipProxy);
        options.addArguments(String.format("--proxy-server=http://%s", proxyAddress));*/

        WebDriver webDriver = new ChromeDriver(options);
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(Constant.chromedriverPath))
                .usingAnyFreePort()
                .build();

        Guarantee guarantee = new Guarantee();
        try {
            service.start();
            webDriver.get("https://checkcoverage.apple.com/cn/zh/");

            logger.info(String.format("---------------------------------- %s -----------------------------------", "开始"));

            WebElement productNameElement = null;
            int num = 0;
            do {
                this.submit(webDriver, sno, num);
                num++;

                // 查找保修元素
                try {
                    productNameElement = new WebDriverWait(webDriver, 2).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#product > div > div > div > p.product-info-name")));
                } catch (Exception e) {
                    logger.error(e);
                    // 异常继续执行

                    String errMsg = ElementUtil.getValByCss(webDriver, "#error-wrapper-5 > div > span", WebElement::getText);
                    if (StringUtils.isNotBlank(errMsg)) {
                        if (errMsg.contains("更换产品")) {
                            StringBuilder sb = new StringBuilder()
                                    .append(String.format(TEMPALTE, "序 列 号", sno))
                                    .append(String.format(TEMPALTE, "保修状态", "已更换的设备"));
                            throw new CrawlException(sb.toString());
                        }

                        if (errMsg.contains("序列号无效")) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("status", 3);
                            map.put("result", String.format(TEMPALTE, "序 列 号", sno) + "错误序列号(Wrong imei)");
                            map.put("success", false);
                            throw new CrawlException(JSON.toJSONString(map));
                        }

                        if (errMsg.contains("很抱歉，我们现在无法完成您的请求")) {
                            throw new CrawlException(String.format("序 列 号: %s<br>激活状态: 暂时无法查询", sno));
                        }

                        if (num == 3 && errMsg.contains("您输入的代码与图片不符")) {
                            logger.error("验证码识别错误");
                        }
                    }
                }

            } while (Objects.isNull(productNameElement) && num < 4);


            if (Objects.isNull(productNameElement)) {
                throw new RuntimeException("获取保修信息失败");
            }

            logger.info(String.format("获取保修数据使用时间 ----- %s", (System.currentTimeMillis() - begin) / 1000.0));
            String html = webDriver.getPageSource();

            // 返回的json
            ResponseJson responseJson = null;
            String startTemp = "responseJson";
            String endTemp = "Classify(\"cs.services/GlobalObject\").set(\"responseJson.productInfo.LINK_";
            int jsonStart = html.indexOf(startTemp) + startTemp.length() + 2;
            int jsonEnd = html.indexOf(endTemp);
            String responseJsonStr = html.substring(jsonStart, jsonEnd).replace(");", "");
            responseJson = JSON.parseObject(responseJsonStr, ResponseJson.class);
            List<String> coverageList = Arrays.asList(responseJson.getCOVERAGE_STATUS().split(","));
            Map<String, String> coverageMap = new HashMap<>();
            for (String str : coverageList) {
                String[] arr = str.split("=");
                coverageMap.put(arr[0], arr[1]);
            }

            // 判断有效购买时间 IS_REGISTERED
            /*String shoppingTimeStr = ElementUtil.getValByCss(webDriver, "#registration > div.result-info > h3", WebElement::getText);
            if (StringUtils.isNotBlank(shoppingTimeStr)) {
                guarantee.setActivationState("已激活");
            } else if (html.contains("请激活您的")) {
                guarantee.setActivationState("未激活");
            }
            else {
                guarantee.setActivationState("未激活");
            }*/
            if ("N".equals(responseJson.getIS_REGISTERED())) {
                guarantee.setActivationState("未激活");
            } else {
                guarantee.setActivationState("已激活");
            }

            // 设备型号
            String productName = productNameElement.getText();
            // 配置编码
            WebElement configCodeElement = webDriver.findElement(By.cssSelector("#product > div > div > div > img"));
            String configCode = null;
            if (Objects.nonNull(configCodeElement)) {
                configCode = configCodeElement.getAttribute("src");
                String match = "configcode=";
                int start = configCode.indexOf(match) + match.length();
                int end = start + 4;
                configCode = configCode.substring(start, end);
                Config config = this.configMapper.seleteByConfig(configCode);
                if (Objects.nonNull(config))
                    productName = config.getMode();
            }
            guarantee.setIphoneInfo(productName);

            // 是否验证
            String verify = ElementUtil.getValByCss(webDriver, "#notregistered > div.result-info > h3", WebElement::getText);
            if (StringUtils.isNotBlank(verify) && verify.contains("日期未验证")) {
                StringBuilder sb = new StringBuilder()
                        .append(String.format(TEMPALTE, "序 列 号", sno))
                        .append(String.format(TEMPALTE, "设备型号", productName))
                        .append(String.format(TEMPALTE, "激活状态", "未验证购买日期"));
                throw new CrawlException(sb.toString());
            }

            String registered = ElementUtil.getValByCss(webDriver, "#iphonenotactivated > div.result-info > h3", WebElement::getText);
            if ("N".equals(responseJson.getIS_REGISTERED()) || registered.contains("请激活")) {
                StringBuilder sb = new StringBuilder()
                        .append(String.format(TEMPALTE, "序 列 号", sno))
                        .append(String.format(TEMPALTE, "设备型号", productName))
                        .append(String.format(TEMPALTE, "激活状态", "未激活"));
                throw new CrawlException(sb.toString());
            }

            // 序列号
            guarantee.setSno(sno);

            // 电话技术支持
            if (coverageMap.get("phone").contains("y")) {
                guarantee.setSupportInfo("有效");
            } else {
                guarantee.setSupportInfo("已过期");
            }

            // 电话技术支持日期
            String supportDate = ElementUtil.getValByCss(webDriver, "#iphone > div.result-info > p", WebElement::getText);
            if (StringUtils.isNotBlank(supportDate)) {
                String match = "预计到期日：";
                if (supportDate.contains(match)) {
                    int start = supportDate.lastIndexOf(match) + match.length();
                    if (start > 0) {
                        supportDate = supportDate.substring(start);
                    }


                    if (supportDate.contains("日")) {
                        int end = supportDate.indexOf("日");
                        supportDate = supportDate.substring(0, end);
                        guarantee.setSupportDate(this.date(supportDate));
                    }

                }
            }

            // 保修支持
            String hwcov = coverageMap.get("hwcov");
            // 格式 hwcov=y-LI
            if (hwcov.contains("y")) {
                guarantee.setIsGuarantee("在保");
            } else {
                guarantee.setIsGuarantee("过保");
            }

            // 是否延保
            if (hwcov.contains("PE") || hwcov.contains("PD") || hwcov.contains("PP") || html.contains("根据 AppleCare 产品的规定")) {
                guarantee.setDelay("延保");
            } else {
                guarantee.setDelay("无");
            }

            // 保修剩余
            String guaranteeDate = ElementUtil.getValByCss(webDriver, "#hardware > div.result-info > p", WebElement::getText);

            if (StringUtils.isNotBlank(guaranteeDate)) {

                String match = "预计到期日：";

                if (guaranteeDate.contains(match)) {
                    int start = guaranteeDate.lastIndexOf(match) + match.length();
                    int end = guaranteeDate.indexOf("注") - 2;

                    if (start > 0) {
                        guaranteeDate = guaranteeDate.substring(start, end);
                        guarantee.setGuaranteeDate(this.date(guaranteeDate));
                    }
                }
            }

            if (Objects.isNull(guarantee.getGuaranteeDate())) {
                guaranteeDate = ElementUtil.getValByCss(webDriver, "#app > div.result-info > p", WebElement::getText);
                String flag = "服务最长可至：";
                if (guaranteeDate.contains(flag)) {
                    int start = guaranteeDate.lastIndexOf(flag);
                    guaranteeDate = guaranteeDate.substring(start + flag.length());
                    guarantee.setGuaranteeDate(this.date(guaranteeDate));

                    guarantee.setActivationDate(DateUtil.calculateDate(guarantee.getGuaranteeDate(), -2, Calendar.YEAR));
                }
            }

            // 是否官换机
            if (html.contains("我们的记录显示，您的产品存在相关的服务历史记录")) {
                guarantee.setChangePhone("是");
            } else {
                guarantee.setChangePhone("否");
            }

            // 是否借出设备
            if ("N".equals(responseJson.getIS_LOANER())) {
                guarantee.setLend("否");
            } else {
                guarantee.setLend("是");
            }


            if ("在保".equals(guarantee.getIsGuarantee()) && Objects.isNull(guarantee.getGuaranteeDate())) {
                return guarantee;
            }
            guarantee.setCreateDate(new Date());
            this.guaranteeMapper.insertSelective(guarantee);
        } catch (Exception e) {
            throw e;
        } finally {
            webDriver.quit();
            service.stop();
        }

        return guarantee;
    }

    /**
     * 提交表单
     */
    public void submit(WebDriver webDriver, String sno, int num) throws URISyntaxException, InterruptedException {

        // 提交按钮
        WebElement submitElement = new WebDriverWait(webDriver, 10).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#view-selector-3 > button")));
        // 序列号
        WebElement snoElement = webDriver.findElement(By.id("serial-number"));
        snoElement.clear();
        snoElement.sendKeys(sno);
        // 验证码
        WebElement ansElement = webDriver.findElement(By.id("captcha-input"));
        // 图片
        WebElement base64Element = webDriver.findElement(By.cssSelector("#view-selector-4 > section > div.desktop-captcha-section.hide-small > div.captcha-image-section > div > img"));

        String ansStr = base64Element.getAttribute("src").replace("data:image/jpeg;base64,", "");
        if (num < 3) {
            // 调用自己验证
            this.logger.info("调用dll验证");
            ansStr = DistinguishUtil.getAuthCode(ansStr);
        } else {
            this.logger.info("调用接口验证");
            ansStr = CaptchaUtil.getAuthCode(ansStr);
        }

        ansElement.clear();
        ansElement.sendKeys(ansStr);
        submitElement.click();
    }

    private Date date(String dateStr) {
        dateStr = dateStr.replace("年", "-")
                .replace("月", "-")
                .replace("日", "");


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            logger.error("日期转换失败", e);
        }
        return null;
    }

    private String getResult(Guarantee guarantee) {
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
                    // 剩余天数
                    int num = DateUtil.getDatePoor(guarantee.getGuaranteeDate(), date, TimeUnit.DAYS).intValue();
                    result.append(String.format("%s: %s到期，剩余%s天<br>", "保修支持", DateUtil.formatYMD(guarantee.getGuaranteeDate()), num));

                    if (Objects.nonNull(guarantee.getActivationDate())) {
                        result.append(String.format(TEMPALTE, "激活时间", DateUtil.formatYMD(guarantee.getActivationDate())));
                    } else {
                        Date activeDate = DateUtil.calculateDate(guarantee.getGuaranteeDate(), -1, Calendar.YEAR);
                        activeDate = DateUtil.calculateDate(activeDate, 1, Calendar.DAY_OF_YEAR);
                        result.append(String.format(TEMPALTE, "激活时间", DateUtil.formatYMD(activeDate)));
                    }
                } else {
                    result.append(String.format(TEMPALTE, "保修支持", guarantee.getIsGuarantee()));
                    result.append(String.format(TEMPALTE, "激活时间", ""));
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
