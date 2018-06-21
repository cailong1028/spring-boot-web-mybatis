package com.cl.springbootwebmybatis;

import com.cl.springbootwebmybatis.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootWebMybatisApplication implements CommandLineRunner{

    private static final Logger log = LoggerFactory.getLogger(SpringBootWebMybatisApplication.class);

	final private UserMapper userMapper;

	public SpringBootWebMybatisApplication(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public static void main(String[] args) {
	    SpringApplication.run(SpringBootWebMybatisApplication.class, args);
	    log.info("app start!");
	}

	@Override
	public void run(String... args) throws Exception {
		log.info(this.userMapper.findByName("admin").getEmail());
	}
}
