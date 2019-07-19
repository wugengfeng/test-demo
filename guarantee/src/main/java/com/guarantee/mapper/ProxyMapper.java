package com.guarantee.mapper;

import com.guarantee.constant.mapper.Mapper;
import com.guarantee.entity.Proxy;
import org.apache.ibatis.annotations.Param;

public interface ProxyMapper extends Mapper<Proxy> {

    Proxy getProxy(@Param("status") Integer status);

}