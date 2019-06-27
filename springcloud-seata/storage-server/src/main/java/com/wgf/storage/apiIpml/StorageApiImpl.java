package com.wgf.storage.apiIpml;

import com.wgf.storage.api.StorageApi;
import com.wgf.storage.service.StorageTblService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: wgf
 * @create: 2019-06-26 11:32
 * @description:
 **/
@RestController
public class StorageApiImpl implements StorageApi {

    @Autowired
    private StorageTblService storageTblService;

    @Override
    public void deduct(String commodityCode, Integer count) {
        this.storageTblService.deduct(commodityCode, count);
    }
}
