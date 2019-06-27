package com.wgf.storage.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: wgf
 * @create: 2019-06-26 10:24
 * @description:
 **/
@FeignClient("storage-server")
@RequestMapping("/storage/storage_api")
public interface StorageApi {

    /**
     * 扣减库存
     * @param commodityCode
     * @param count
     */
    @GetMapping("/deduct")
    void deduct(@RequestParam("commodityCode") String commodityCode, @RequestParam("count") Integer count);

}
