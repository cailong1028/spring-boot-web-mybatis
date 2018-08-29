package com.cl.springbootwebmybatis;

import com.cl.springbootwebmybatis.primary.BeanInterface;
import com.cl.springbootwebmybatis.primary.ComponentInterface;
import com.cl.springbootwebmybatis.bean.UserBean;
import com.cl.springbootwebmybatis.mapper.TTestMapper;
import com.cl.springbootwebmybatis.mapper.UserMapper;
import com.cl.springbootwebmybatis.primary.pattern.Pattern1;
import com.cl.springbootwebmybatis.primary.pattern.Pattern2;
import com.cl.springbootwebmybatis.primary.pattern.PatternInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootWebMybatisApplication implements CommandLineRunner{

    private static final Logger log = LoggerFactory.getLogger(SpringBootWebMybatisApplication.class);

	final private UserMapper userMapper;
	final private TTestMapper tTestMapper;

	@Autowired
	private UserBean userBean;

	@Autowired
	private ComponentInterface component1;
    @Autowired
    private ComponentInterface component2;

    @Autowired
    private BeanInterface bean1;

    @Autowired
    private BeanInterface getBean;

    @Autowired
    PatternInterface<?> pattern1;

    @Autowired
    PatternInterface<?> pattern2;

	public SpringBootWebMybatisApplication(UserMapper userMapper, TTestMapper tTestMapper) {
		this.userMapper = userMapper;
		this.tTestMapper = tTestMapper;
	}

	public static void main(String[] args) {
	    SpringApplication.run(SpringBootWebMybatisApplication.class, args);
	    log.info("app start!");
	}

	@Override
	public void run(String... args){
//		log.info(this.userMapper.findByName("admin").getEmail());
		log.info(this.userMapper.findByName("test2").getEmail());
		log.info(this.tTestMapper.findByName("test1").getEmail());
		this.userBean.sayName();
        this.component1.sayName();
        this.component2.sayName();
        this.bean1.setBeanName("bean1");
        this.bean1.sayBean();
        this.getBean.setBeanName("bean_a");
        this.getBean.sayBean();

        ((Pattern1)pattern1).set(1);
        ((Pattern2)pattern2).set("s");
        System.out.println(pattern1.get());
        System.out.println(pattern2.get());
	}
}
