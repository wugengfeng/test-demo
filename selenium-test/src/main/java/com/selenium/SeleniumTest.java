package com.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SeleniumTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("http://www.baidu.com");

        WebElement searchInput = webDriver.findElement(By.id("kw"));
        searchInput.sendKeys("JAVA 在线手册");

        WebElement button = webDriver.findElement(By.id("su"));
        button.submit();

        javaScript(webDriver);

        webDriver.quit();
    }


    /**
     * 基础选择
     *
     * @param webDriver
     */
    public static void basicSelector(WebDriver webDriver) {
        // 显式定时等待多少时间，超过时间条件不成立则抛出异常
        new WebDriverWait(webDriver, 3).until(driver -> driver.getTitle().equals("JAVA 在线手册_百度搜索"));
        System.out.println(webDriver.getTitle());

        // 根据id定位元素
        WebElement webElement = webDriver.findElement(By.id("1"));
        System.out.println(webElement.getText());

        // 根据css定位元素
        List<WebElement> cssElement = webDriver.findElements(By.className("t"));
        cssElement.forEach(e -> System.out.println(e.getText()));

        // 根据dom元素查找
        List<WebElement> domElement = webDriver.findElements(By.tagName("h3"));
        domElement.forEach(e -> System.out.println(e.getText()));

        // 根据name属性查找元素
        WebElement nameElement = webDriver.findElement(By.name("ie"));
        System.out.println(nameElement.getAttribute("value"));  // 获取val

        // 按链接文字匹配
        WebElement linkTextElement = webDriver.findElement(By.linkText("web开发技术"));
        System.out.println(linkTextElement.getAttribute("href"));

        // 按部分链接文字匹配
        WebElement partialLinkTextElement = webDriver.findElement(By.partialLinkText("API文档"));
        System.out.println(partialLinkTextElement.getAttribute("href"));

        // css选择器
        List<WebElement> cssSelectorElement = webDriver.findElements(By.cssSelector(".c-span4.opr-recommends-merge-item.opr-recommends-merge-item-vertical"));
        cssSelectorElement.forEach(e -> System.out.println(e.getText()));

        WebElement cssSelector = webDriver.findElement(By.cssSelector(".ftCon-Wrapper #setf"));
        System.out.println(cssSelector.getAttribute("href"));

        // xpath 选择器
        List<WebElement> xpathElement = webDriver.findElements(By.xpath("//div[@id='page']/a"));
        xpathElement.forEach(e -> System.out.println(e.getAttribute("href")));
    }


    /**
     * 执行js代码
     *
     * @param webDriver
     */
    public static void javaScript(WebDriver webDriver) {
        // 执行页面自带的js函数
        String result = ((JavascriptExecutor) webDriver).executeScript("return bds.util.domain.get('5.su.bdimg.com')").toString();
        System.out.println(result);

        // 执行自定义函数 arguments[] 在js的函数中可以根据下标获取参数
        Object sum = ((JavascriptExecutor) webDriver).executeScript("let let_1 = arguments[0]; let let_2 = arguments[1]; return let_1 + let_2;", 10, 20);
        System.out.println(sum);
    }


    /**
     * select帮助类，可以处理一些表单元素
     */
    @Test
    public void select() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("http://www.w3school.com.cn//tiy/loadtext.asp?f=html_input_checked");


    }
}
