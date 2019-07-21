package com.guarantee.exception;

/**
 * Created by acer on 2019/7/21.
 */
public class CrawlException extends RuntimeException {

    public CrawlException() {
    }

    public CrawlException(String message) {
        super(message);
    }

    public CrawlException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrawlException(Throwable cause) {
        super(cause);
    }

    public CrawlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
