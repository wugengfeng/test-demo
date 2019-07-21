/**
 * Copyright 2019 bejson.com
 */
package com.guarantee.entity.json;
import java.util.List;

/**
 * Auto-generated: 2019-07-21 19:2:32
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class ResponseJson {

    private String DISPUTE_MESSAGE;
    private List<Results> results;
    private String COVERAGE_STATUS;
    private String IS_REGISTERED;
    private String IS_LOANER;
    private ProductInfo productInfo;
    public void setDISPUTE_MESSAGE(String DISPUTE_MESSAGE) {
        this.DISPUTE_MESSAGE = DISPUTE_MESSAGE;
    }
    public String getDISPUTE_MESSAGE() {
        return DISPUTE_MESSAGE;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }
    public List<Results> getResults() {
        return results;
    }

    public void setCOVERAGE_STATUS(String COVERAGE_STATUS) {
        this.COVERAGE_STATUS = COVERAGE_STATUS;
    }
    public String getCOVERAGE_STATUS() {
        return COVERAGE_STATUS;
    }

    public void setIS_REGISTERED(String IS_REGISTERED) {
        this.IS_REGISTERED = IS_REGISTERED;
    }
    public String getIS_REGISTERED() {
        return IS_REGISTERED;
    }

    public void setIS_LOANER(String IS_LOANER) {
        this.IS_LOANER = IS_LOANER;
    }
    public String getIS_LOANER() {
        return IS_LOANER;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }
    public ProductInfo getProductInfo() {
        return productInfo;
    }

}