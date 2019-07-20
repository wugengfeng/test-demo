package com.guarantee.entity;

import java.util.Date;
import javax.persistence.*;

public class Guarantee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 序列号
     */
    private String sno;

    /**
     * 设备型号
     */
    @Column(name = "iphone_info")
    private String iphoneInfo;

    /**
     * 激活状态
     */
    @Column(name = "activation_state")
    private String activationState;

    /**
     * 激活时间
     */
    @Column(name = "activation_date")
    private Date activationDate;

    /**
     * 电话技术支持
     */
    @Column(name = "support_info")
    private String supportInfo;

    /**
     * 电话剩余
     */
    @Column(name = "support_date")
    private Date supportDate;

    /**
     * 保修支持
     */
    @Column(name = "is_guarantee")
    private String isGuarantee;

    /**
     * 保修剩余
     */
    @Column(name = "guarantee_date")
    private Date guaranteeDate;

    /**
     * 是否延保
     */
    private String delay;

    /**
     * 是否官换
     */
    @Column(name = "change_phone")
    private String changePhone;

    /**
     * 借出设备
     */
    private String lend;

    /**
     * 创建日期
     */
    @Column(name = "create_date")
    private Date createDate;

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
     * 获取序列号
     *
     * @return sno - 序列号
     */
    public String getSno() {
        return sno;
    }

    /**
     * 设置序列号
     *
     * @param sno 序列号
     */
    public void setSno(String sno) {
        this.sno = sno;
    }

    /**
     * 获取设备型号
     *
     * @return iphone_info - 设备型号
     */
    public String getIphoneInfo() {
        return iphoneInfo;
    }

    /**
     * 设置设备型号
     *
     * @param iphoneInfo 设备型号
     */
    public void setIphoneInfo(String iphoneInfo) {
        this.iphoneInfo = iphoneInfo;
    }

    /**
     * 获取激活状态
     *
     * @return activation_state - 激活状态
     */
    public String getActivationState() {
        return activationState;
    }

    /**
     * 设置激活状态
     *
     * @param activationState 激活状态
     */
    public void setActivationState(String activationState) {
        this.activationState = activationState;
    }

    /**
     * 获取激活时间
     *
     * @return activation_date - 激活时间
     */
    public Date getActivationDate() {
        return activationDate;
    }

    /**
     * 设置激活时间
     *
     * @param activationDate 激活时间
     */
    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    /**
     * 获取电话技术支持
     *
     * @return support_info - 电话技术支持
     */
    public String getSupportInfo() {
        return supportInfo;
    }

    /**
     * 设置电话技术支持
     *
     * @param supportInfo 电话技术支持
     */
    public void setSupportInfo(String supportInfo) {
        this.supportInfo = supportInfo;
    }

    /**
     * 获取电话剩余
     *
     * @return support_date - 电话剩余
     */
    public Date getSupportDate() {
        return supportDate;
    }

    /**
     * 设置电话剩余
     *
     * @param supportDate 电话剩余
     */
    public void setSupportDate(Date supportDate) {
        this.supportDate = supportDate;
    }

    /**
     * 获取保修支持
     *
     * @return is_guarantee - 保修支持
     */
    public String getIsGuarantee() {
        return isGuarantee;
    }

    /**
     * 设置保修支持
     *
     * @param isGuarantee 保修支持
     */
    public void setIsGuarantee(String isGuarantee) {
        this.isGuarantee = isGuarantee;
    }

    /**
     * 获取保修剩余
     *
     * @return guarantee_date - 保修剩余
     */
    public Date getGuaranteeDate() {
        return guaranteeDate;
    }

    /**
     * 设置保修剩余
     *
     * @param guaranteeDate 保修剩余
     */
    public void setGuaranteeDate(Date guaranteeDate) {
        this.guaranteeDate = guaranteeDate;
    }

    /**
     * 获取是否延保
     *
     * @return delay - 是否延保
     */
    public String getDelay() {
        return delay;
    }

    /**
     * 设置是否延保
     *
     * @param delay 是否延保
     */
    public void setDelay(String delay) {
        this.delay = delay;
    }

    /**
     * 获取借出设备
     *
     * @return lend - 借出设备
     */
    public String getLend() {
        return lend;
    }

    /**
     * 设置借出设备
     *
     * @param lend 借出设备
     */
    public void setLend(String lend) {
        this.lend = lend;
    }

    /**
     * 获取创建日期
     *
     * @return create_date - 创建日期
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建日期
     *
     * @param createDate 创建日期
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getChangePhone() {
        return changePhone;
    }

    public void setChangePhone(String changePhone) {
        this.changePhone = changePhone;
    }
}