package com.modules.feignclient.biz.hystrix;

import com.modules.feignclient.biz.FeignClient1Biz;
import org.springframework.stereotype.Component;

@Component
public class FeignClient1BizHystrix implements FeignClient1Biz {
    @Override
    public String getHi(String name) {
        return "hystrix: " + name;
    }
}
