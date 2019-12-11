package com.wgf.test.common;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author: wgf
 * @create: 2019-12-06 20:24
 * @description:
 **/
public interface CommonMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
