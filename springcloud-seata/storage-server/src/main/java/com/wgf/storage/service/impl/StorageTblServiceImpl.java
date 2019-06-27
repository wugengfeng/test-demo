package com.wgf.storage.service.impl;

import com.wgf.storage.entity.StorageTbl;
import com.wgf.storage.mapper.StorageTblMapper;
import com.wgf.storage.service.StorageTblService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: wgf
 * @create: 2019-06-26 11:36
 * @description:
 **/
@Service
public class StorageTblServiceImpl implements StorageTblService {

    @Autowired
    private StorageTblMapper storageTblMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deduct(String commodityCode, Integer count) {
        StorageTbl query = new StorageTbl();
        query.setCommodityCode(commodityCode);
        StorageTbl storageTbl = this.storageTblMapper.selectOne(query);

        storageTbl.setCount(storageTbl.getCount() - count);
        this.storageTblMapper.updateByPrimaryKeySelective(storageTbl);
    }
}
