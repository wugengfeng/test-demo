package com.wgf;

import com.wgf.storage.StorageServerApplication;
import com.wgf.storage.entity.StorageTbl;
import com.wgf.storage.mapper.StorageTblMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author: wgf
 * @create: 2019-06-25 16:46
 * @description:
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StorageServerApplication.class)
public class MapperTest {

    @Autowired
    private StorageTblMapper storageTblMapper;

    @Test
    public void selectAll() {
        List<StorageTbl> orderTblList = this.storageTblMapper.selectAll();
        System.out.println(orderTblList);
    }
}
