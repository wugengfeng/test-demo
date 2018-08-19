package com.selenium;

import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WebDriverTest {

    /**
     * 基础选择
     *
     * @param webDriver
     */
    @Test
    public void basicSelector() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("http://www.baidu.com");

        WebElement searchInput = webDriver.findElement(By.id("kw"));
        searchInput.sendKeys("JAVA 在线手册");

        WebElement button = webDriver.findElement(By.id("su"));
        button.click();

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

        webDriver.quit();
    }


    /**
     * 执行js代码
     *
     * @param webDriver
     */
    @Test
    public void javaScript() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("http://www.baidu.com");

        WebElement searchInput = webDriver.findElement(By.id("kw"));
        searchInput.sendKeys("JAVA 在线手册");

        WebElement button = webDriver.findElement(By.id("su"));
        button.submit();

        // 执行页面自带的js函数
        String result = ((JavascriptExecutor) webDriver).executeScript("return bds.util.domain.get('5.su.bdimg.com')").toString();
        System.out.println(result);

        // 执行自定义函数 arguments[] 在js的函数中可以根据下标获取参数
        Object sum = ((JavascriptExecutor) webDriver).executeScript("let let_1 = arguments[0]; let let_2 = arguments[1]; return let_1 + let_2;", 10, 20);
        System.out.println(sum);

        webDriver.quit();
    }


    /**
     * select帮助类，可以处理一些表单元素
     */
    @Test
    public void select() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("http://www.w3school.com.cn//tiy/loadtext.asp?f=html_select");

        // 指定选中select的指定值
        Select select = new Select(webDriver.findElement(By.cssSelector("select")));
        select.selectByValue("opel");

        webDriver.quit();
    }

    /**
     * swatchTo可以切换frame或窗口，因为有些页面是内嵌frame或点按钮会弹出新的页面
     * 这时就需要进行切换去操作新页面/内嵌页面的Dom元素
     */
    @Test
    public void swatchTo(){
        /*WebDriver webDriver = new ChromeDriver();
        webDriver.get("http://www.w3school.com.cn/tiy/t.asp?f=html_select");

        // 切换到内嵌的iframe
        webDriver.switchTo().frame("i");
        // 指定选中select的指定值
        Select select = new Select(webDriver.findElement(By.cssSelector("select")));
        select.selectByValue("opel");

        webDriver.quit();*/

        WebDriver webDriver = new ChromeDriver();
        webDriver.get("http://www.w3school.com.cn//tiy/loadtext.asp?f=html_a_target_framename");
        WebElement button = webDriver.findElement(By.cssSelector("a"));
        button.click();

        // 获取新窗口的handle
        List<String> handles = new ArrayList<>(webDriver.getWindowHandles());
        webDriver.switchTo().window(handles.get(handles.size() - 1));

        // 获取新窗口内容
        System.out.println(webDriver.findElement(By.cssSelector("h3")).getText());
        webDriver.quit();
    }

    /**
     * alter操作，可以点击确认，输入值，获取值，关闭操作
     */
    @Test
    public void alter() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://www.w3schools.com/jsref/tryit.asp?filename=tryjsref_alert");
        webDriver.switchTo().frame("iframeResult");

        WebElement botton = webDriver.findElement(By.cssSelector("body > button"));
        botton.click();

        Alert alert = webDriver.switchTo().alert();
        alert.dismiss();
        webDriver.quit();
    }

    /**
     * 操作浏览器
     */
    @Test
    public void navigate() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("http://cas.qa.aukeyit.com");
        String url = webDriver.getCurrentUrl();

        // 是否需要登陆
        if (url.contains("login")) {
            WebElement changeLoginType = webDriver.findElement(By.xpath("//*[@id=\"btn_account_login\"]"));
            changeLoginType.click();

            WebElement account = webDriver.findElement(By.cssSelector("#account"));
            WebElement passwd = webDriver.findElement(By.cssSelector("#password"));
            WebElement loginBtn = webDriver.findElement(By.cssSelector("#button"));
            account.sendKeys("xieben@Aukeys.com");
            passwd.sendKeys("Aukey123");
            loginBtn.click();

            // 点击 tips
            webDriver.findElement(By.cssSelector("body > div.content_warp > div.content_tips > div > button")).click();
        }

        // to在当前页面导航到某个页面
        webDriver.navigate().to("http://cas.qa.aukeyit.com/companyorg/index");

        // 返回
        webDriver.navigate().back();

        // 前进
        webDriver.navigate().forward();

        // 刷新当前页
        webDriver.navigate().refresh();
    }

    @Test
    public void cookie() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("http://www.example.com");
        Cookie cookie = new Cookie("key","value");
        webDriver.manage().addCookie(cookie);

        Set<Cookie> cookies = webDriver.manage().getCookies();

        for (Cookie ck : cookies) {
            System.out.println(ck.getName());
            System.out.println(ck.getValue());

            webDriver.manage().deleteCookie(ck);
        }

        webDriver.manage().deleteCookieNamed("key");
        webDriver.manage().deleteAllCookies();
    }
}
