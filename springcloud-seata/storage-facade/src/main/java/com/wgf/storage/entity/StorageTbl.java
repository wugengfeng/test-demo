package com.wgf.storage.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;

@ApiModel(value = "StorageTbl", description = "")
@Table(name = "storage_tbl")
public class StorageTbl {
    @Id
    @ApiModelProperty(value = "")
    private Integer id;

    @Column(name = "commodity_code")
    @ApiModelProperty(value = "")
    private String commodityCode;

    @ApiModelProperty(value = "")
    private Integer count;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return commodity_code
     */
    public String getCommodityCode() {
        return commodityCode;
    }

    /**
     * @param commodityCode
     */
    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    /**
     * @return count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count
     */
    public void setCount(Integer count) {
        this.count = count;
    }
}