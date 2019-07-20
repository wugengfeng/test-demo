package com.guarantee.service;

import com.guarantee.entity.Guarantee;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author: wgf
 * @create: 2019-07-15 18:27
 * @description:
 **/
public interface GuaranteeService {

    Guarantee selectBySno(String sno) throws InterruptedException, IOException, URISyntaxException;

}
