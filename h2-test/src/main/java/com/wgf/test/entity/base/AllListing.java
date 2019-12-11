package com.wgf.test.entity.base;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "all_listing")
public class AllListing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 大区id
     */
    @Column(name = "area_id")
    private Integer areaId;

    /**
     * 站点id
     */
    @Column(name = "site_id")
    private Integer siteId;

    /**
     * 账号id
     */
    @Column(name = "account_id")
    private Integer accountId;

    @Transient
    private String accountIds;

    /**
     * 亚马逊sku
     */
    @Column(name = "seller_sku")
    private String sellerSku;

    /**
     * 销售编码
     */
    private String asin;

    @Column(name = "parent_asin")
    private String parentAsin;

    @Column(name = "listing_id")
    private String listingId;

    @Column(name = "open_date")
    private Date openDate;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 亚马逊产品ID
     */
    @Column(name = "product_id")
    private String productId;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 亚马逊状态
     */
    private String status;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 更新时间
     */
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 大区名称
     */
    private String area;

    /**
     * 账号名称
     */
    @Column(name = "account_name")
    private String accountName;

    /**
     * 站点名称
     */
    @Column(name = "site_name")
    private String siteName;

    @Column(name = "parent_id")
    private Integer parentId;

    private String type;

    @Column(name = "data_digest")
    private String dataDigest;

    /**
     * 权限ID
     */
    @Column(name = "auth_id")
    private String authId;

    @Column(name = "item_name")
    private String itemName;

    /**
     * 图片链接
     */
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "company_sku")
    private String companySku;

    @Column(name = "fnsku")
    private String fnsku;

    @Column(name = "sku_type")
    private String skuType;

    @Column(name = "product_name")
    private String productName;

    public String getAccountIds() {
        return accountIds;
    }

    public void setAccountIds(String accountIds) {
        this.accountIds = accountIds;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCompanySku() {
        return companySku;
    }

    public void setCompanySku(String companySku) {
        this.companySku = companySku;
    }

    public String getFnsku() {
        return fnsku;
    }

    public void setFnsku(String fnsku) {
        this.fnsku = fnsku;
    }

    public String getSkuType() {
        return skuType;
    }

    public void setSkuType(String skuType) {
        this.skuType = skuType;
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
     * 获取大区id
     *
     * @return area_id - 大区id
     */
    public Integer getAreaId() {
        return areaId;
    }

    /**
     * 设置大区id
     *
     * @param areaId 大区id
     */
    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    /**
     * 获取站点id
     *
     * @return site_id - 站点id
     */
    public Integer getSiteId() {
        return siteId;
    }

    /**
     * 设置站点id
     *
     * @param siteId 站点id
     */
    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    /**
     * 获取账号id
     *
     * @return account_id - 账号id
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * 设置账号id
     *
     * @param accountId 账号id
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * 获取亚马逊sku
     *
     * @return seller_sku - 亚马逊sku
     */
    public String getSellerSku() {
        return sellerSku;
    }

    /**
     * 设置亚马逊sku
     *
     * @param sellerSku 亚马逊sku
     */
    public void setSellerSku(String sellerSku) {
        this.sellerSku = sellerSku;
    }

    /**
     * 获取销售编码
     *
     * @return asin - 销售编码
     */
    public String getAsin() {
        return asin;
    }

    /**
     * 设置销售编码
     *
     * @param asin 销售编码
     */
    public void setAsin(String asin) {
        this.asin = asin;
    }

    /**
     * @return parent_asin
     */
    public String getParentAsin() {
        return parentAsin;
    }

    /**
     * @param parentAsin
     */
    public void setParentAsin(String parentAsin) {
        this.parentAsin = parentAsin;
    }

    /**
     * @return listing_id
     */
    public String getListingId() {
        return listingId;
    }

    /**
     * @param listingId
     */
    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    /**
     * @return open_date
     */
    public Date getOpenDate() {
        return openDate;
    }

    /**
     * @param openDate
     */
    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    /**
     * 获取价格
     *
     * @return price - 价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置价格
     *
     * @param price 价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取亚马逊产品ID
     *
     * @return product_id - 亚马逊产品ID
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 设置亚马逊产品ID
     *
     * @param productId 亚马逊产品ID
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * 获取数量
     *
     * @return quantity - 数量
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * 设置数量
     *
     * @param quantity 数量
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * 获取亚马逊状态
     *
     * @return status - 亚马逊状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置亚马逊状态
     *
     * @param status 亚马逊状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取创建时间
     *
     * @return create_date - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取更新时间
     *
     * @return update_date - 更新时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置更新时间
     *
     * @param updateDate 更新时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取大区名称
     *
     * @return area - 大区名称
     */
    public String getArea() {
        return area;
    }

    /**
     * 设置大区名称
     *
     * @param area 大区名称
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * 获取账号名称
     *
     * @return account_name - 账号名称
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 设置账号名称
     *
     * @param accountName 账号名称
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 获取站点名称
     *
     * @return site_name - 站点名称
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * 设置站点名称
     *
     * @param siteName 站点名称
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    /**
     * @return parent_id
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return data_digest
     */
    public String getDataDigest() {
        return dataDigest;
    }

    /**
     * @param dataDigest
     */
    public void setDataDigest(String dataDigest) {
        this.dataDigest = dataDigest;
    }

    /**
     * 获取权限ID
     *
     * @return auth_id - 权限ID
     */
    public String getAuthId() {
        return authId;
    }

    /**
     * 设置权限ID
     *
     * @param authId 权限ID
     */
    public void setAuthId(String authId) {
        this.authId = authId;
    }


    /**
     * @return item_name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 获取图片链接
     *
     * @return image_url - 图片链接
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置图片链接
     *
     * @param imageUrl 图片链接
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}