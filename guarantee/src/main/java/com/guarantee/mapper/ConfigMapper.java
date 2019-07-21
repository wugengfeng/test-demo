package com.guarantee.mapper;

import com.guarantee.constant.mapper.Mapper;
import com.guarantee.entity.Config;
import org.apache.ibatis.annotations.Param;

/**
 * Created by acer on 2019/7/21.
 */
public interface ConfigMapper extends Mapper<Config> {

    Config seleteByConfig(@Param("config") String config);

}
