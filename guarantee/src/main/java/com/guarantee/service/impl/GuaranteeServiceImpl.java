package com.guarantee.service.impl;

import com.guarantee.agent.UserAgent;
import com.guarantee.entity.Proxy;
import com.guarantee.service.GuaranteeService;
import com.guarantee.service.ProxyService;
import com.guarantee.util.DistinguishUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @create: 2019-07-15 18:28
 * @description:
 **/
@Service
public class GuaranteeServiceImpl implements GuaranteeService {

    @Autowired
    private UserAgent defaultUserAgent;

    @Autowired
    private ProxyService proxyService;

    @Value("${chromedriverPath}")
    private String chromedriverPath;

    @Override
    public Map<String, Object> crawl(String sno) {
        String userAgent = defaultUserAgent.getUserAgent();

        System.setProperty("webdriver.chrome.driver", chromedriverPath);
        ChromeOptions options = new ChromeOptions();
        //设置为 headless 模式 （必须）
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments(String.format("--user-agent=%s", userAgent));

        Proxy proxy = this.proxyService.getProxy();
        if (Objects.nonNull(proxy)) {
            options.addArguments(String.format("--proxy-server=http://%s", proxy.getProxyip()));
        }

        WebDriver webDriver = new ChromeDriver(options);
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(chromedriverPath))
                .usingAnyFreePort()
                .build();

        Map<String, Object> resultMap = new HashMap<>();
        try {
            service.start();
            webDriver.get("https://checkcoverage.apple.com/tw/zh/?sn=");

            WebElement productNameElement;
            int num = 0;
            do {
                this.submit(webDriver, sno, num);
                num++;

                // 查找保修元素
                productNameElement = new WebDriverWait(webDriver, 10).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#product > div > div > div > p.product-info-name")));
            } while (Objects.nonNull(productNameElement) && num < 3);


            if (Objects.isNull(productNameElement)) {

            } else {
                // 产品名称
                String productName = productNameElement.getText();
                resultMap.put("productName", productName);

                // 有效购买日期
                String info = webDriver.findElement(By.cssSelector("#registration > div.result-info > h3")).getText();
                resultMap.put("info", info);

                // 電話技術支援
                String info2 = webDriver.findElement(By.cssSelector("#iphone > div.result-info > h3")).getText();
                resultMap.put("info2", info2);

                // 維修和服務範圍
                String info3 = webDriver.findElement(By.cssSelector("#hardware > div.result-info > h3")).getText();
                resultMap.put("info3", info3);
            }


        } catch (Exception e) {
            this.proxyService.deprecated(proxy.getId());
            e.printStackTrace();
        } finally {
            this.proxyService.freedProxy(proxy.getId());
            webDriver.quit();
            service.stop();
        }

        return resultMap;
    }

    /**
     * 提交表单
     */
    public void submit(WebDriver webDriver, String sno, int num) {

        // 提交按钮
        WebElement submitElement = new WebDriverWait(webDriver, 10).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#view-selector-3 > button")));
        // 序列号
        WebElement snoElement = webDriver.findElement(By.id("serial-number"));
        // 验证码
        WebElement ansElement = webDriver.findElement(By.id("captcha-input"));
        // 图片
        WebElement base64Element = webDriver.findElement(By.cssSelector("#view-selector-4 > section > div.desktop-captcha-section.hide-small > div.captcha-image-section > div > img"));

        String ansStr = base64Element.getAttribute("src").replace("data:image/jpeg;base64,", "");
        if (num == 0) {
            // 调用自己验证
            ansStr = DistinguishUtil.getAuthCode(ansStr);
        } else {
            // todo 调用收费验证
        }

        snoElement.sendKeys(sno);
        ansElement.sendKeys(ansStr);
        submitElement.submit();
    }
}
