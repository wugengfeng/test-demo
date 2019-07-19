package com.guarantee.entity;

import javax.persistence.*;

public class Proxy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String proxyip;

    private Integer status;

    private Integer lasttime;

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
     * @return proxyip
     */
    public String getProxyip() {
        return proxyip;
    }

    /**
     * @param proxyip
     */
    public void setProxyip(String proxyip) {
        this.proxyip = proxyip;
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return lasttime
     */
    public Integer getLasttime() {
        return lasttime;
    }

    /**
     * @param lasttime
     */
    public void setLasttime(Integer lasttime) {
        this.lasttime = lasttime;
    }
}