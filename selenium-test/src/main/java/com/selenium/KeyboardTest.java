package com.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
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
        new WebDriverWait(webDriver, 10).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("TANGRAM__PSP_10__footerULoginBtn")));

        WebElement change = webDriver.findElement(By.id("TANGRAM__PSP_10__footerULoginBtn"));

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
}
