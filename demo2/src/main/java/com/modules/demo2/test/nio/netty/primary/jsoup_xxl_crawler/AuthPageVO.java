package com.modules.demo2.test.nio.netty.primary.jsoup_xxl_crawler;

import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.annotation.PageSelect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PageSelect(cssQuery = "body")
public class AuthPageVO {

    @PageFieldSelect(cssQuery = "#loginname")
    private String loginName;
}
