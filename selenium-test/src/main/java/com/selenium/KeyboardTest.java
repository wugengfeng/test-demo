package com.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class KeyboardTest {

    @Test
    public void key() throws InterruptedException {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://www.oschina.net/home/login");


        // 账号
        WebElement account = webDriver.findElement(By.id("userMail"));

        // 密码
        WebElement passwd = webDriver.findElement(By.id("userPassword"));

        // 登陆按钮
        WebElement login = webDriver.findElement(By.cssSelector("#account_login > form > div > div.form-item.form-button > button"));

        new Actions(webDriver)
                .sendKeys(account, "")
                .sendKeys(passwd, "")
                .click(login)
                .perform();

    }

    /**
     * 百度有防机器人机制，登陆每个步骤都需要停顿几秒
     * @throws InterruptedException
     */
    @Test
    public void login() throws InterruptedException {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://www.baidu.com/");

        WebElement loginBtn = webDriver.findElement(By.cssSelector("#u1 > a.lb"));
        loginBtn.click();

        // 等待元素加载完毕
        WebElement change = new WebDriverWait(webDriver, 10).until(ExpectedConditions.presenceOfElementLocated(By.id("TANGRAM__PSP_10__footerULoginBtn")));

        WebElement account = webDriver.findElement(By.id("TANGRAM__PSP_10__userName"));

        WebElement passwd = webDriver.findElement(By.id("TANGRAM__PSP_10__password"));

        WebElement login = webDriver.findElement(By.id("TANGRAM__PSP_10__submit"));

        change.click();
        Thread.sleep(3 * 1000l);

        account.sendKeys("");
        Thread.sleep(3 * 1000l);

        passwd.sendKeys("");
        Thread.sleep(3 * 1000l);

        login.submit();
    }

    /**
     * 组合键实现复制，粘贴 CTRL+A, CTRL+C, CTRL+V
     */
    @Test
    public void combination() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("http://www.w3school.com.cn//tiy/loadtext.asp?f=html_inputfields");

        WebElement name = webDriver.findElement(By.cssSelector("body > form > input[type=\"text\"]:nth-child(1)"));

        WebElement firstName = webDriver.findElement(By.cssSelector("body > form > input[type=\"text\"]:nth-child(3)"));

        // 动作链
        new Actions(webDriver)
                .sendKeys(name, "123456")
                .keyDown(Keys.LEFT_CONTROL)
                .sendKeys("a")
                .keyUp(Keys.LEFT_CONTROL)
                .keyDown(Keys.LEFT_CONTROL)
                .sendKeys("c")
                .keyUp(Keys.LEFT_CONTROL)
                .keyDown(firstName, Keys.LEFT_CONTROL)
                .sendKeys("v")
                .keyUp(Keys.LEFT_CONTROL)
                .perform();

    }

    /**
     * 实现文件上传
     */
    @Test
    public void upload() throws InterruptedException {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://pan.baidu.com/");

        WebElement change = new WebDriverWait(webDriver, 10).until(ExpectedConditions.presenceOfElementLocated(By.id("TANGRAM__PSP_4__footerULoginBtn")));

        WebElement account = webDriver.findElement(By.id("TANGRAM__PSP_4__userName"));
        WebElement passwd = webDriver.findElement(By.id("TANGRAM__PSP_4__password"));
        WebElement login = webDriver.findElement(By.id("TANGRAM__PSP_4__submit"));

        change.click();
        Thread.sleep(3 * 1000l);

        account.sendKeys("13229942600");
        Thread.sleep(3 * 1000l);

        passwd.sendKeys("");
        Thread.sleep(3 * 1000l);

        login.submit();

        // 关闭弹窗
        new WebDriverWait(webDriver, 10).until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("body > div.module-cardtip-box > div.module-content > div.words > p.tip-button"))).click();

        // 上传操作
        // 上传按钮
        WebElement uploadBtn = webDriver.findElement(By.id("h5Input0"));
        new Actions(webDriver).click(uploadBtn).sendKeys(uploadBtn, "D:\\timg.jpg").perform();
    }
}
