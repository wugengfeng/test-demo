package com.wgf.test.entity.base;

import javax.persistence.*;
import java.util.Date;

@Table(name = "sys_user")
public class SysUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户昵称
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像地址
     */
    @Column(name = "portrait_url")
    private String portraitUrl;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 是否管理员
     */
    @Column(name = "is_admin")
    private String isAdmin;

    /**
     * 密码强度
     */
    @Column(name = "password_strength")
    private String passwordStrength;

    /**
     * 个人描述
     */
    @Column(name = "pesonal_description")
    private String pesonalDescription;

    /**
     * 用户状态： 1,启用，0.禁用
     */
    private Byte enable;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 最后登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;


    @Column(name = "vx_user_id")
    private String vxUserId;

    public String getVxUserId() {
        return vxUserId;
    }

    public void setVxUserId(String vxUserId) {
        this.vxUserId = vxUserId;
    }

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
     * 获取用户昵称
     *
     * @return name - 用户昵称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置用户昵称
     *
     * @param name 用户昵称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取手机号
     *
     * @return phone - 手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号
     *
     * @param phone 手机号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取头像地址
     *
     * @return portrait_url - 头像地址
     */
    public String getPortraitUrl() {
        return portraitUrl;
    }

    /**
     * 设置头像地址
     *
     * @param portraitUrl 头像地址
     */
    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取是否管理员
     *
     * @return is_admin - 是否管理员
     */
    public String getIsAdmin() {
        return isAdmin;
    }

    /**
     * 设置是否管理员
     *
     * @param isAdmin 是否管理员
     */
    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * 获取密码强度
     *
     * @return password_strength - 密码强度
     */
    public String getPasswordStrength() {
        return passwordStrength;
    }

    /**
     * 设置密码强度
     *
     * @param passwordStrength 密码强度
     */
    public void setPasswordStrength(String passwordStrength) {
        this.passwordStrength = passwordStrength;
    }

    /**
     * 获取个人描述
     *
     * @return pesonal_description - 个人描述
     */
    public String getPesonalDescription() {
        return pesonalDescription;
    }

    /**
     * 设置个人描述
     *
     * @param pesonalDescription 个人描述
     */
    public void setPesonalDescription(String pesonalDescription) {
        this.pesonalDescription = pesonalDescription;
    }

    /**
     * 获取用户状态： 1,启用，0.禁用
     *
     * @return enable - 用户状态： 1,启用，0.禁用
     */
    public Byte getEnable() {
        return enable;
    }

    /**
     * 设置用户状态： 1,启用，0.禁用
     *
     * @param enable 用户状态： 1,启用，0.禁用
     */
    public void setEnable(Byte enable) {
        this.enable = enable;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取最后登录时间
     *
     * @return last_login_time - 最后登录时间
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 设置最后登录时间
     *
     * @param lastLoginTime 最后登录时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}