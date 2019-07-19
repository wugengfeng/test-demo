package com.guarantee;


import com.alibaba.fastjson.JSON;
import com.guarantee.entity.Result;
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
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class AutoTest {
    public static void main(String[] args) {
        new TestThread().start();
        new TestThread().start();
        new TestThread().start();
    }

    public static String getAnsStr(String base64) {
        String res = new ShowApiRequest("http://route.showapi.com/184-5", "", "")
                .addTextPara("img_base64", base64)
                .addTextPara("typeId", "30")
                .addTextPara("convert_to_jpg", "0")
                .addTextPara("needMorePrecise", "0")
                .post();

        Result result = JSON.parseObject(res, Result.class);
        return result.getShowapi_res_body().getResult();
    }

}


class TestThread extends Thread {
    @Override
    public void run() {
        while (true) {
            // "/opt/google/chrome/chromedriver"
            System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            //设置为 headless 模式 （必须）
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-gpu");
            options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");

            WebDriver webDriver = new ChromeDriver(options);
            ChromeDriverService service = new ChromeDriverService.Builder()
                    .usingDriverExecutable(new File("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe"))
                    .usingAnyFreePort()
                    .build();

            try {
                service.start();
                webDriver.get("https://checkcoverage.apple.com/tw/zh/?sn=");
                System.out.println(webDriver.getTitle());
                WebElement submit = new WebDriverWait(webDriver, 10).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#view-selector-3 > button")));
                WebElement sno = webDriver.findElement(By.id("serial-number"));
                WebElement ans = webDriver.findElement(By.id("captcha-input"));
                WebElement base64 = webDriver.findElement(By.cssSelector("#view-selector-4 > section > div.desktop-captcha-section.hide-small > div.captcha-image-section > div > img"));
                String base64Str = base64.getAttribute("src").replace("data:image/jpeg;base64,", "");
                sno.sendKeys("FCMTT2AJHFM4");

                String filePath = Base64Util.base64ChangeImage(base64Str, "");

                if (filePath == null) {
                    throw new RuntimeException("获取不到验证码图片");
                }

                //String ansStr = getAnsStr(base64Str);
                String ansStr = "123456";
                if (StringUtils.isEmpty(ansStr)) {
                    webDriver.quit();
                    return;
                }

                ans.sendKeys(ansStr);
                submit.submit();

                // 查找保修元素
                WebElement guarantee = new WebDriverWait(webDriver, 10).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#hardware > div.result-info > h3")));

                if (Objects.nonNull(guarantee)) {
                    System.out.println(webDriver.findElement(By.cssSelector("#hardware > div.result-info > h3")).getText());
                }

                TimeUnit.SECONDS.sleep(5);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                webDriver.quit();
                service.stop();
            }
        }
    }
}
