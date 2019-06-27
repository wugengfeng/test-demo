package com.wgf.storage.service;

/**
 * @author: wgf
 * @create: 2019-06-26 11:35
 * @description:
 **/
public interface StorageTblService {

    /**
     * 扣减库存
     *
     * @param commodityCode
     * @param count
     */
    void deduct(String commodityCode, Integer count);

}
