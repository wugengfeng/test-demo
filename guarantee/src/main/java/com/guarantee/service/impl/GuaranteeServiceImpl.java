package com.guarantee.service.impl;

import com.guarantee.agent.UserAgent;
import com.guarantee.constant.Constant;
import com.guarantee.entity.Guarantee;
import com.guarantee.entity.Proxy;
import com.guarantee.mapper.GuaranteeMapper;
import com.guarantee.service.GuaranteeService;
import com.guarantee.service.ProxyService;
import com.guarantee.util.CaptchaUtil;
import com.guarantee.util.DistinguishUtil;
import com.guarantee.util.ElementUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import java.util.Date;
import java.util.Objects;

/**
 * @create: 2019-07-15 18:28
 * @description:
 **/
@Service
public class GuaranteeServiceImpl implements GuaranteeService {

    private Logger logger = LogManager.getLogger(GuaranteeServiceImpl.class);

    @Autowired
    private GuaranteeMapper guaranteeMapper;

    @Autowired
    private UserAgent defaultUserAgent;

    @Autowired
    private ProxyService proxyService;

    @Override
    public Guarantee selectBySno(String sno) throws InterruptedException, IOException, URISyntaxException {
        if (StringUtils.isBlank(sno)) {
            throw new RuntimeException("序列号错误");
        }

        Guarantee guarantee = this.guaranteeMapper.selectBySno(sno);

        if (Objects.isNull(guarantee)) {
            guarantee = this.crawl(sno);
        }

        return guarantee;
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

        Proxy proxy = null;
        if (Constant.enableProxy) {
            proxy = this.proxyService.getProxy();
            if (Objects.nonNull(proxy)) {
                options.addArguments(String.format("--proxy-server=http://%s", proxy.getProxyip()));
            }
        }

        WebDriver webDriver = new ChromeDriver(options);
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(Constant.chromedriverPath))
                .usingAnyFreePort()
                .build();

        Guarantee guarantee = new Guarantee();
        try {
            service.start();
            webDriver.get("https://checkcoverage.apple.com/cn/zh/");

            WebElement productNameElement = null;
            int num = 0;
            do {
                this.submit(webDriver, sno, num);
                num++;

                // 查找保修元素
                try {
                    productNameElement = new WebDriverWait(webDriver, 2).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#product > div > div > div > p.product-info-name")));
                } catch (Exception e) {
                    e.printStackTrace();
                    // 异常继续执行
                }

            } while (Objects.isNull(productNameElement) && num < 4);


            if (Objects.isNull(productNameElement)) {
                throw new RuntimeException("获取保修信息失败");
            }

            logger.info(String.format("获取保修数据使用时间 ----- %s", (System.currentTimeMillis() - begin) / 1000.0));
            // 设备型号
            String productName = productNameElement.getText();
            guarantee.setIphoneInfo(productName);

            // 序列号
            guarantee.setSno(sno);

            guarantee.setActivationState("已激活");

            // 电话技术支持
            String supportInfo = ElementUtil.getVal(webDriver.findElement(By.cssSelector("#iphone > div.result-info > h3")), WebElement::getText);
            if (StringUtils.isNotBlank(supportInfo)) {
                guarantee.setSupportInfo(supportInfo.replace("电话技术支持：", ""));
            }

            // 电话技术支持日期
            String supportDate = ElementUtil.getVal(webDriver.findElement(By.cssSelector("#iphone > div.result-info > p")), WebElement::getText);
            if (StringUtils.isNotBlank(supportDate)) {
                String match = "预计到期日：";
                if (supportDate.contains(match)) {
                    int start = supportDate.lastIndexOf(match) + match.length();
                    int end = start + 9;

                    if (start > 0) {
                        supportDate = supportDate.substring(start, end);
                        guarantee.setSupportDate(this.date(supportDate));
                    }
                    guarantee.setActivationDate(this.date(supportDate));
                }
            }

            // 保修支持
            String guaranteeInfo = ElementUtil.getVal(webDriver.findElement(By.cssSelector("#hardware > div.result-info > h3")), WebElement::getText);
            if (StringUtils.isNotBlank(guaranteeInfo)) {
                guarantee.setIsGuarantee(guaranteeInfo.replace("维修和服务保障情况：", ""));

                // 延保
                if ("有效".equals(guarantee.getIsGuarantee())) {
                    guarantee.setDelay("延保");
                } else {
                    guarantee.setDelay("无");
                }
            }

            // 保修剩余
            String guaranteeDate = ElementUtil.getVal(webDriver.findElement(By.cssSelector("#hardware > div.result-info > p")), WebElement::getText);
            if (StringUtils.isNotBlank(guaranteeDate)) {

                String match = "预计到期日：";

                if (guaranteeDate.contains(match)) {
                    int start = guaranteeDate.lastIndexOf(match) + match.length();
                    int end = start + 9;

                    if (start > 0) {
                        guaranteeDate = guaranteeDate.substring(start, end);
                        guarantee.setSupportDate(this.date(guaranteeDate));
                    }
                }
            }

            // 是否官换机
            String html = webDriver.getPageSource();
            if (html.contains("我们的记录显示，您的产品存在相关的服务历史记录")) {
                guarantee.setChangePhone("是");
            } else {
                guarantee.setChangePhone("否");
            }

            // 是否借出设备
            String jsonMatch = "IS_LOANER";
            guarantee.setLend("否");
            if (html.contains(jsonMatch)) {
                int start = html.indexOf(jsonMatch) + jsonMatch.length() + 3;
                int end = start + 1;

                if (start > 0) {
                    String flag = html.substring(start, end);
                    if (!"N".equals(flag)) {
                        guarantee.setLend("是");
                    }
                }
            }
            guarantee.setCreateDate(new Date());
            this.guaranteeMapper.insertSelective(guarantee);

        } catch (Exception e) {
            throw e;
        } finally {
            this.proxyService.freedProxy(proxy);
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
}
