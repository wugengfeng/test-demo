package com.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

/**
 * 模拟用户鼠标操作
 * perform() 执行所有Actions中存储的行为
 * release() 释放鼠标
 */
public class MouseTest {

    /**
     * 鼠标左击
     */
    @Test
    public void click() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("http://www.w3school.com.cn/tiy/loadtext.asp?f=jseg_alert");
        new Actions(webDriver).click(webDriver.findElement(By.cssSelector("body > input[type='button']")))
                .perform();
    }

    /**
     * 鼠标右击
     */
    @Test
    public void contextClick() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("http://www.baidu.com");
        new Actions(webDriver).contextClick().perform();
    }

    /**
     * 鼠标双击
     */
    @Test
    public void doubleClick() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("http://www.w3school.com.cn/tiy/loadtext.asp?f=html5_ev_ondblclick");
        new Actions(webDriver).doubleClick(webDriver.findElement(By.cssSelector("body > button")))
                .perform();
    }

    /**
     * 鼠标点击并控制
     */
    @Test
    public void clickAndHold() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("http://www.w3school.com.cn//tiy/loadtext.asp?f=html5_ev_onmousedown");
        new Actions(webDriver).clickAndHold(webDriver.findElement(By.id("p1"))).perform();
    }


    /**
     * 使用Action 执行拖放操作
     */
    @Test
    public void Actions() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("http://www.bootcss.com/p/layoutit/");
        WebElement element = webDriver.findElement(By.cssSelector("#estRows > div:nth-child(4) > span"));
        WebElement target = webDriver.findElement(By.cssSelector(".demo.ui-sortable"));
        new Actions(webDriver).dragAndDrop(element, target).perform();
        new Actions(webDriver).keyDown(Keys.LEFT_CONTROL).sendKeys("t").keyUp(Keys.LEFT_CONTROL).perform();
    }

    /**
     * 鼠标移动到某个坐标
     */
    @Test
    public void moveToElement() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://www.baidu.com/");
        new Actions(webDriver).moveToElement(webDriver.findElement(By.cssSelector("#u1 > a.pf"))).perform();
    }

    /**
     * 鼠标移动到某个元素悬停后选择元素
     * 如百度首页的设置
     */
    @Test
    public void moveAndSelect() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://www.baidu.com/");
        new Actions(webDriver).moveToElement(webDriver.findElement(By.cssSelector("#u1 > a.pf"))).perform();
        // 先悬停，再点击
        webDriver.findElement(By.cssSelector("#wrapper > div.bdpfmenu > a:nth-child(3)")).click();
    }
}
