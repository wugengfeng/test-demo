package com.guarantee.service.impl;

import com.alibaba.fastjson.JSON;
import com.guarantee.entity.Result;
import com.guarantee.service.CrawlService;
import com.guarantee.util.Base64Util;
import com.guarantee.util.ShowApiRequest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @create: 2019-07-15 18:28
 * @description:
 **/
@Service
public class CrawlServiceImpl implements CrawlService {

    @Value("${chromedriverPath}")
    private String chromedriverPath;

    @Value("${savePath}")
    private String savePath;

    @Override
    public Map<String, Object> crawl(String no) {
        // "/opt/google/chrome/chromedriver"
        System.setProperty("webdriver.chrome.driver", chromedriverPath);
        ChromeOptions options = new ChromeOptions();
        //设置为 headless 模式 （必须）
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");

        WebDriver webDriver = new ChromeDriver(options);
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(chromedriverPath))
                .usingAnyFreePort()
                .build();

        Map<String, Object> resultMap = new HashMap<>();
        try {
            service.start();
            webDriver.get("https://checkcoverage.apple.com/tw/zh/?sn=");

            String title = webDriver.getTitle();
            resultMap.put("title", title);

            WebElement submit = new WebDriverWait(webDriver, 10).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#view-selector-3 > button")));
            WebElement sno = webDriver.findElement(By.id("serial-number"));
            WebElement ans = webDriver.findElement(By.id("captcha-input"));
            WebElement base64 = webDriver.findElement(By.cssSelector("#view-selector-4 > section > div.desktop-captcha-section.hide-small > div.captcha-image-section > div > img"));
            String base64Str = base64.getAttribute("src").replace("data:image/jpeg;base64,", "");
            sno.sendKeys(no);

            String filePath = Base64Util.base64ChangeImage(base64Str, savePath);

            if (filePath == null) {
                throw new RuntimeException("获取不到验证码图片");
            }

            String ansStr = getAnsStr(base64Str);
            if (StringUtils.isEmpty(ansStr)) {
                webDriver.quit();
                return null;
            }

            ans.sendKeys(ansStr);
            submit.submit();

            // 查找保修元素
            WebElement guarantee = new WebDriverWait(webDriver, 10).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#hardware > div.result-info > h3")));

            if (Objects.nonNull(guarantee)) {

                // 产品名称
                String productName = webDriver.findElement(By.cssSelector("#product > div > div > div > p.product-info-name")).getText();
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
            e.printStackTrace();
        } finally {
            webDriver.quit();
            service.stop();
        }

        return resultMap;
    }


    public static String getAnsStr(String base64) {
        String res = new ShowApiRequest("http://route.showapi.com/184-5", "99778", "59b8da084ac9423eae82f206568d6d19")
                .addTextPara("img_base64", base64)
                .addTextPara("typeId", "30")
                .addTextPara("convert_to_jpg", "0")
                .addTextPara("needMorePrecise", "0")
                .post();

        Result result = JSON.parseObject(res, Result.class);
        return result.getShowapi_res_body().getResult();
    }

}
