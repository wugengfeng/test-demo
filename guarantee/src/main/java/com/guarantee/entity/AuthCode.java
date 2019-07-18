package com.guarantee.entity;

/**
 * Created by acer on 2019/7/18.
 */
public class AuthCode {
    private String binaryValue;
    private String error;
    private String type;

    public AuthCode(String binaryValue, String error, String type) {
        this.binaryValue = binaryValue;
        this.error = error;
        this.type = type;
    }

    public String getBinaryValue() {
        return binaryValue;
    }

    public void setBinaryValue(String binaryValue) {
        this.binaryValue = binaryValue;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
