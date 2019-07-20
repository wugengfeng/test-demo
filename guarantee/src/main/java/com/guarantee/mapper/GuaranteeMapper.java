package com.guarantee.mapper;

import com.guarantee.constant.mapper.Mapper;
import com.guarantee.entity.Guarantee;
import org.apache.ibatis.annotations.Param;

public interface GuaranteeMapper extends Mapper<Guarantee> {

    Guarantee selectBySno(@Param("sno") String sno);
}