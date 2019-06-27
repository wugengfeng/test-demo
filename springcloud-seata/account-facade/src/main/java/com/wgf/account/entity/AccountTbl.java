package com.wgf.account.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import java.math.BigDecimal;

@ApiModel(value = "AccountTbl", description = "")
@Table(name = "account_tbl")
public class AccountTbl {
    @Id
    @ApiModelProperty(value = "")
    private Integer id;

    @Column(name = "user_id")
    @ApiModelProperty(value = "")
    private String userId;

    @ApiModelProperty(value = "")
    private BigDecimal money;

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
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}