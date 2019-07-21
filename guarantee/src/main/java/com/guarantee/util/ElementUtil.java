package com.guarantee.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Objects;
import java.util.function.Function;

public class ElementUtil {

    public static String getVal(WebDriver webDriver,
                                     Function<String, By> byFunction,
                                     String select,
                                     Function<WebElement, String> function) {
        String value = "";
        WebElement webElement = null;

        try {
            webElement = webDriver.findElement(byFunction.apply(select));
        } catch (Exception e) {

        }

        if (Objects.isNull(webElement)) {
            return value;
        }

        value = function.apply(webElement);

        return value;
    }

    public static String getValByCss(WebDriver webDriver,
                                String select,
                                Function<WebElement, String> function) {
        return getVal(webDriver, By::cssSelector, select, function);
    }

    public static String getValById(WebDriver webDriver,
                                   String select,
                                   Function<WebElement, String> function) {
        return getVal(webDriver, By::id, select, function);
    }
}
