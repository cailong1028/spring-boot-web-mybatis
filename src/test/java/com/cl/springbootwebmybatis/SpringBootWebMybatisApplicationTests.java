package com.cl.springbootwebmybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootWebMybatisApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(SpringBootWebMybatisApplicationTests.class);

	@Test
	public void contextLoads() {
        log.info("test done!");
	}

}
