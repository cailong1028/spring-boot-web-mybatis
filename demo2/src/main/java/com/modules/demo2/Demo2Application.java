package com.modules.demo2;

import com.modules.demo2.bean.UserBean1;
import com.modules.demo2.bean.UserBean2;
import com.modules.demo2.mapper.TestMapper;
import com.modules.demo2.mapper.domain.Test;
import com.modules.demo2.util.ApplicationContextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class Demo2Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Demo2Application.class);

    @Autowired
    private ApplicationContext appCtx2;

    @Autowired
    private UserBean1 userBean1;

    @Autowired
    private UserBean2 userBean2;

    @Autowired
    private ApplicationContextProvider applicationContextProvider;

    public static void main(String[] args) {
        SpringApplication.run(Demo2Application.class, args);
        LOGGER.info("app started");
    }

    @Bean
    public CommandLineRunner runner(ApplicationContext appCtx){
        return args -> {

            LOGGER.info("inspect beans provided by spring boot:");
            String[] beanNames = appCtx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for(String oneBeanName:beanNames){
                LOGGER.info("one bean --> {}", oneBeanName);
            }
            mainRun();
        };
    }

    private void mainRun(){
        userBean1.setName("AAAAAAA");
        LOGGER.info("bean userBean1 getName {}", userBean1.getName());

        userBean2.setAge(1);
        LOGGER.info("bean userBean2 getAge {}", userBean2.getAge());

        UserBean1 userBean1_1 = appCtx2.getBean("userBean1", UserBean1.class);
        userBean1_1.setName("userBean1_1_name");
        LOGGER.info("appCtx getBean name is {}", userBean1_1.getName());

        UserBean1 userBean1_1_2 = appCtx2.getBean(UserBean1.class);
        userBean1_1_2.setName("userBean1_1_name_2");
        LOGGER.info("appCtx getBean name is {}", userBean1_1_2.getName());

        UserBean2 userBean2_2 = applicationContextProvider.getApplicationContext().getBean(UserBean2.class);
        userBean2_2.setAge(22);
        LOGGER.info("userBean2_2 age is {}", userBean2_2.getAge());

    }
}
