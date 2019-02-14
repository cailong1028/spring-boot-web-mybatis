package com.modules.feignclient.biz;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "eureka-client")
public interface FeignClient1Biz {

    @RequestMapping("/api/hi")
    public String getHi(@RequestParam("name") String name);

}
