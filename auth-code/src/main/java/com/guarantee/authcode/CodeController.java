package com.guarantee.authcode;

import com.guarantee.authcode.entity.CodeParam;
import com.guarantee.authcode.util.DistinguishUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("code")
public class CodeController {

    @PostMapping("get_code")
    public String getCode(@RequestBody CodeParam codeParam) {
        return DistinguishUtil.getAuthCode(codeParam.getBase64());
    }

}
