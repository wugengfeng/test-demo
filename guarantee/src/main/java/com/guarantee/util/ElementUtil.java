package com.guarantee.util;

import org.openqa.selenium.WebElement;

import java.util.Objects;
import java.util.function.Function;

public class ElementUtil {

    public static String getVal(WebElement webElement, Function<WebElement, String> function) {
        String value = null;

        if (Objects.isNull(webElement)) {
            return value;
        }

        value = function.apply(webElement);

        return value;
    }

}
